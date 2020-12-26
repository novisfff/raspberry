package cn.novisfff.raspberry.views;

import cn.novisfff.raspberry.JavafxApplication;
import cn.novisfff.raspberry.domain.Weather;
import cn.novisfff.raspberry.event.StageReadyEvent;
import cn.novisfff.raspberry.utils.NetworkUtil;
import cn.novisfff.raspberry.utils.WeatherUtil;
import cn.novisfff.raspberry.views.skin.WeatherSkin;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/15
 */

@Component
public class WeatherView implements ApplicationListener<StageReadyEvent>, UpdateWeather {


    private final static Logger logger = LoggerFactory.getLogger(WakeOnView.class);

    private ConfigurableApplicationContext applicationContext;

    private WeatherSkin weatherSkin;

    private HomeView homeView;

    private Pane weatherPane;

    public WeatherView(ConfigurableApplicationContext applicationContext, HomeView homeView, WeatherSkin weatherSkin) {
        this.applicationContext = applicationContext;
        this.homeView = homeView;
        this.weatherSkin = weatherSkin;
    }

    @FXML
    private ImageView day2Icon;

    @FXML
    private Label nowTemp;

    @FXML
    private ImageView nowIcon;

    @FXML
    private Label airLable;

    @FXML
    private Label day3Temp;

    @FXML
    private Circle airDot;

    @FXML
    private ImageView day3Icon;

    @FXML
    private Label day2Temp;

    @FXML
    private Label nowText;

    @FXML
    private ImageView day1Icon;

    @FXML
    private Label day1Temp;

    @FXML
    private ImageView backgroundImage;

    private String iconPath;

    private final static String ICON_SUFFIX = ".png";

    @Override
    public void onApplicationEvent(StageReadyEvent stageReadyEvent) {
        Platform.runLater(() -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(weatherSkin.getWeatherFxml(WeatherView.class));
                fxmlLoader.setControllerFactory(applicationContext::getBean);
                weatherPane = fxmlLoader.load();
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }

            iconPath = weatherSkin.getWeatherIconUrl();

            if(weatherSkin.getBackground() != null) {
                backgroundImage.setImage(weatherSkin.getBackground());
            }

            homeView.rightPane0.getChildren().setAll(weatherPane);

            logger.info("加载 WeatherView");

        });
    }


    @Override
    public void updateNow(Weather weather) {
        WeatherUtil.AirMessage airMessage = WeatherUtil.getAirMessage(weather.getAir());
        Platform.runLater(() -> {
            nowIcon.setImage(getIcon(weather.getIcon()));
            nowTemp.setText(weather.getTemp() + "°");
            nowText.setText(weather.getText());
            airLable.setText(airMessage.getText());
            airDot.setFill(Color.valueOf(airMessage.getColor()));
        });
    }

    @Override
    public void updateThreeDay(Weather[] weathers) {
        Platform.runLater(() -> {
            day1Icon.setImage(getIcon(weathers[0].getIcon()));
            day1Temp.setText(weathers[0].getTempMin() + "°/" + weathers[0].getTempMax() + "°");
            day2Icon.setImage(getIcon(weathers[1].getIcon()));
            day2Temp.setText(weathers[1].getTempMin() + "°/" + weathers[1].getTempMax()+ "°");
            day3Icon.setImage(getIcon(weathers[2].getIcon()));
            day3Temp.setText(weathers[2].getTempMin() + "°/" + weathers[2].getTempMax()+ "°");
        });
    }


    /**
     * 图像缓存
     */
    private Map<Integer, Image> imageCache = new HashMap<>();

    /**
     * 获取天气图标
     * @param iconIndex
     * @return
     */
    private Image getIcon(Integer iconIndex) {
        if (iconIndex == null) {
            iconIndex = 999;
        }
        if (imageCache.containsKey(iconIndex)) {
            return imageCache.get(iconIndex);
        } else {
            Image image = new Image(iconPath + iconIndex + ICON_SUFFIX);
            imageCache.put(iconIndex, image);
            return image;
        }
    }

}

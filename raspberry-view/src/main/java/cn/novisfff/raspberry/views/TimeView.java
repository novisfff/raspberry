package cn.novisfff.raspberry.views;

import cn.novisfff.raspberry.event.StageReadyEvent;
import cn.novisfff.raspberry.views.skin.TimeSkin;
import eu.hansolo.medusa.Clock;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * <h1>显示时间页面</h1>
 *
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/9
 */
@Component
public class TimeView implements ApplicationListener<StageReadyEvent>, UpdateTime {

    private final static Logger logger = LoggerFactory.getLogger(WakeOnView.class);

    private ConfigurableApplicationContext applicationContext;

    private TimeSkin timeSkin;

    private HomeView homeView;

    public TimeView(ConfigurableApplicationContext applicationContext, HomeView homeView, TimeSkin timeSkin) {
        this.applicationContext = applicationContext;
        this.homeView = homeView;
        this.timeSkin = timeSkin;
    }

    @FXML
    public Pane timePane;

    @FXML
    private ImageView backgroundImage;

    public Clock timeClock;

    /**
     * 初始化页面
     *
     */
    @Override
    public void onApplicationEvent(StageReadyEvent stageReadyEvent) {
        Platform.runLater(() -> {
            Pane root;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(timeSkin.getTimeFxml(TimeView.class));
                fxmlLoader.setControllerFactory(applicationContext::getBean);
                root = fxmlLoader.load();
                homeView.timePane.getChildren().add(root);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }

            timeClock = timeSkin.buildTimeClock();
            timePane.getChildren().setAll(timeClock);

            if(timeSkin.getBackground() != null) {
                backgroundImage.setImage(timeSkin.getBackground());
            }
            homeView.timePane.getChildren().setAll(root);

            logger.info("加载 TimeView");
        });
    }


    @Override
    public void setTime(long seconds) {
        if(timeClock != null) {
            Platform.runLater(() -> timeClock.setTime(seconds));
        }
    }

}



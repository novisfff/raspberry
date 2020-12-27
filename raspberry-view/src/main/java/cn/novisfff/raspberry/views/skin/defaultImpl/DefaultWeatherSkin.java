package cn.novisfff.raspberry.views.skin.defaultImpl;

import cn.novisfff.raspberry.views.skin.WeatherSkin;
import javafx.scene.image.Image;

import java.net.URL;

/**
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/26
 */

public class DefaultWeatherSkin implements WeatherSkin {

    private static DefaultWeatherSkin instance;

    private DefaultWeatherSkin() {
    }

    public static synchronized DefaultWeatherSkin getInstance() {
        if(instance == null) {
            instance = new DefaultWeatherSkin();
        }
        return instance;
    }

    @Override
    public URL getWeatherFxml(Class<?> source) {
        return DefaultWeatherSkin.class.getResource("weather.fxml");
    }

    @Override
    public Image getBackground() {
        return null;
    }

    @Override
    public String getWeatherIconUrl() {
        return "/pic/s2-64/";
    }
}

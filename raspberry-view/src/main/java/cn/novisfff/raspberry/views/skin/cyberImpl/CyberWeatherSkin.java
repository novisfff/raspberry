package cn.novisfff.raspberry.views.skin.cyberImpl;

import cn.novisfff.raspberry.views.skin.WeatherSkin;
import javafx.scene.image.Image;

import java.net.URL;

/**
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/26
 */

public class CyberWeatherSkin implements WeatherSkin {

    private static CyberWeatherSkin instance;

    private CyberWeatherSkin() {
    }

    public static synchronized CyberWeatherSkin getInstance() {
        if(instance == null) {
            instance = new CyberWeatherSkin();
        }
        return instance;
    }

    @Override
    public URL getWeatherFxml(Class<?> source) {
        return CyberWeatherSkin.class.getResource("weather.fxml");
    }

    @Override
    public Image getBackground() {
        return null;
    }

    @Override
    public String getWeatherIconUrl() {
        return "/pic/bw-64/";
    }
}

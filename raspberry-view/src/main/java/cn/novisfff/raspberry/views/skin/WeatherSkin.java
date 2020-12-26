package cn.novisfff.raspberry.views.skin;

import javafx.scene.image.Image;

import java.net.URL;

/**
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/26
 */
public interface WeatherSkin {

    /**
     * 获取WeatherView的Fxml文件Url
     * @param source classPath
     * @return WeatherView的Fxml文件Url
     */
    URL getWeatherFxml(Class<?> source);

    /**
     * 获取WeatherView背景图
     * @return WeatherView背景图
     */
    Image getBackground();

    /**
     * 获取天气图标根目录
     * @return 天气图标根目录
     *
     */
    String getWeatherIconUrl();

}

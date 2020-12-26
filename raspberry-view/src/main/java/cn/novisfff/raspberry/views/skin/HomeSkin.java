package cn.novisfff.raspberry.views.skin;

import javafx.scene.image.Image;

import java.net.URL;
import java.util.List;

/**
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/26
 */
public interface HomeSkin {
    /**
     * 获取Css文件Url
     * @param source classPath
     * @return Css文件Url
     */
    List<String> getStylesheetsUrl(Class<?> source);

    /**
     * 获取Font文件Url
     * @param source classPath
     * @return Font文件Url
     */
    List<String> getFontsUrl(Class<?> source);

    /**
     * 获取HomeView的Fxml文件Url
     * @param source classPath
     * @return HomeView的Fxml文件Url
     */
    URL getHomeFxml(Class<?> source);

    /**
     * 获取HomeView背景图
     * @return HomeView背景图
     */
    Image getBackground();

}

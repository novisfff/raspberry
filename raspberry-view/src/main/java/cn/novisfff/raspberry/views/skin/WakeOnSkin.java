package cn.novisfff.raspberry.views.skin;

import javafx.scene.image.Image;

import java.net.URL;

/**
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/26
 */
public interface WakeOnSkin {

    /**
     * 获取WakeOnView的Fxml文件Url
     * @param source classPath
     * @return WakeOnView的Fxml文件Url
     */
    URL getWakeOnFxml(Class<?> source);

    /**
     * 获取WakeOnView背景图
     * @return WakeOnView背景图
     */
    Image getBackground();

    /**
     * 获取开机按键背景图
     * @return 开机按键背景图
     */
    Image getWakeOnButton();

}

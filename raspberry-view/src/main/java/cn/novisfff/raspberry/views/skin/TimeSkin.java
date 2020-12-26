package cn.novisfff.raspberry.views.skin;

import eu.hansolo.medusa.Clock;
import javafx.scene.image.Image;

import java.net.URL;

/**
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/26
 */
public interface TimeSkin {

    /**
     * 获取TimeView的Fxml文件Url
     * @param source classPath
     * @return TimeView的Fxml文件Url
     */
    URL getTimeFxml(Class<?> source);

    /**
     * 获取TimeView背景图
     * @return TimeView背景图
     */
    Image getBackground();

    /**
     * 创建数字时钟
     * @return 时钟
     */
    Clock buildTimeClock();

}

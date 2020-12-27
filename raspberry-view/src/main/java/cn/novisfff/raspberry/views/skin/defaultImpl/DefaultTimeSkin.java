package cn.novisfff.raspberry.views.skin.defaultImpl;

import cn.novisfff.raspberry.views.skin.TimeSkin;
import eu.hansolo.medusa.Clock;
import eu.hansolo.medusa.ClockBuilder;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.Locale;

/**
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/26
 */

public class DefaultTimeSkin implements TimeSkin {

    private static DefaultTimeSkin instance;

    private DefaultTimeSkin() {
    }

    public static synchronized DefaultTimeSkin getInstance() {
        if(instance == null) {
            instance = new DefaultTimeSkin();
        }
        return instance;
    }

    @Override
    public URL getTimeFxml(Class<?> source) {
        return DefaultTimeSkin.class.getResource("time.fxml");
    }

    @Override
    public Image getBackground() {
        return null;
    }

    @Override
    public Clock buildTimeClock() {
        return ClockBuilder
                .create()
                .prefSize(300, 115)
                .skinType(Clock.ClockSkinType.DIGITAL)
                .locale(Locale.CHINA)
                .textColor(Color.WHITE)
                .dateColor(Color.WHITE)
                .build();
    }
}

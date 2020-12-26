package cn.novisfff.raspberry.views.skin.cyberImpl;

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

public class CyberTimeSkin implements TimeSkin {

    private static CyberTimeSkin instance;

    private CyberTimeSkin() {
    }

    public static synchronized CyberTimeSkin getInstance() {
        if(instance == null) {
            instance = new CyberTimeSkin();
        }
        return instance;
    }

    @Override
    public URL getTimeFxml(Class<?> source) {
        return CyberTimeSkin.class.getResource("time.fxml");
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
                .locale(Locale.CHINA).textColor(Color.WHITE)
                .textColor(new Color(0,0,0.05,0.9))
                .dateColor(new Color(0,0,0.05,0.9))
                .build();
    }
}

package cn.novisfff.raspberry.views.skin.cyberImpl;

import cn.novisfff.raspberry.views.skin.WakeOnSkin;
import javafx.scene.image.Image;

import java.net.URL;

/**
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/26
 */

public class CyberWakeOnSkin implements WakeOnSkin {

    private static CyberWakeOnSkin instance;

    private CyberWakeOnSkin() {
    }

    public static synchronized CyberWakeOnSkin getInstance() {
        if(instance == null) {
            instance = new CyberWakeOnSkin();
        }
        return instance;
    }

    @Override
    public URL getWakeOnFxml(Class<?> source) {
        return CyberWakeOnSkin.class.getResource("wakeon.fxml");
    }

    @Override
    public Image getBackground() {
        return new Image("pic/left.png");
    }

    @Override
    public Image getWakeOnButton() {
        return new Image("/pic/button.png");
    }
}

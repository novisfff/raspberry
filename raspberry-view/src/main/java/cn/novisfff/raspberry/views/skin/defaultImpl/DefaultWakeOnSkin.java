package cn.novisfff.raspberry.views.skin.defaultImpl;

import cn.novisfff.raspberry.views.skin.WakeOnSkin;
import javafx.scene.image.Image;

import java.net.URL;

/**
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/26
 */

public class DefaultWakeOnSkin implements WakeOnSkin {

    private static DefaultWakeOnSkin instance;

    private DefaultWakeOnSkin() {
    }

    public static synchronized DefaultWakeOnSkin getInstance() {
        if(instance == null) {
            instance = new DefaultWakeOnSkin();
        }
        return instance;
    }

    @Override
    public URL getWakeOnFxml(Class<?> source) {
        return DefaultWakeOnSkin.class.getResource("wakeon.fxml");
    }

    @Override
    public Image getBackground() {
        return new Image("pic/defaultLeft.png");
    }

    @Override
    public Image getWakeOnButton() {
        return new Image("/pic/cyberButton.png");
    }
}

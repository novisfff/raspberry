package cn.novisfff.raspberry.views.skin.cyberImpl;

import cn.novisfff.raspberry.views.skin.HomeSkin;
import javafx.scene.image.Image;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/26
 */

public class CyberHomeSkin implements HomeSkin {

    private static CyberHomeSkin instance;

    private CyberHomeSkin() {
    }

    public static synchronized CyberHomeSkin getInstance() {
        if(instance == null) {
            instance = new CyberHomeSkin();
        }
        return instance;
    }

    @Override
    public List<String> getStylesheetsUrl(Class<?> source) {
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add(CyberHomeSkin.class.getResource("style.css").toExternalForm());
        return stringArrayList;
    }

    @Override
    public List<String> getFontsUrl(Class<?> source) {
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add(CyberHomeSkin.class.getResource("cyber.ttf").toExternalForm());
        stringArrayList.add(source.getResource("SourceHanSansCNBold.otf").toExternalForm());
        return stringArrayList;
    }

    @Override
    public URL getHomeFxml(Class<?> source) {
        return CyberHomeSkin.class.getResource("home.fxml");
    }

    @Override
    public Image getBackground() {
        return new Image("/pic/background.png");
    }
}

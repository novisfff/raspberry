package cn.novisfff.raspberry.views.skin.cyberImpl;

import cn.novisfff.raspberry.views.skin.ComputerInfoSkin;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.Section;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.net.URL;

/**
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/26
 */

public class CyberComputerInfoSkin implements ComputerInfoSkin {

    private static CyberComputerInfoSkin instance;

    private CyberComputerInfoSkin() {
    }

    public static synchronized ComputerInfoSkin getInstance() {
        if(instance == null) {
            instance = new CyberComputerInfoSkin();
        }
        return instance;
    }

    @Override
    public URL getComputerInfoFxml(Class<?> source) {
        return CyberComputerInfoSkin.class.getResource("computerInfo.fxml");
    }

    @Override
    public Image getBackground() {
        return new Image("pic/left.png");
    }

    @Override
    public Tile buildCpuGpuTile() {
        return TileBuilder.create().skinType(Tile.SkinType.SMOOTH_AREA_CHART)
                .prefSize(150, 150)
                .minValue(0)
                .maxValue(100)
                .backgroundColor(new Color(0, 0, 0, 0))
                .valueColor(new Color(0,0,0.05,0.9))
                .smoothing(true)
                .chartType(Tile.ChartType.AREA)
                .tooltipText("")
                .animated(true)
                .build();
    }

    @Override
    public Gauge buildTempGauge() {
        return GaugeBuilder
                .create()
                .skinType(Gauge.SkinType.KPI)
                .prefSize(150, 150)
                .maxValue(100)
                .valueColor(new Color(0, 0, 0.05, 0.9))
                .barColor(Color.LIME)
                .needleColor(new Color(0, 0.73, 0.72, 1))
                .thresholdVisible(true)
                .threshold(70)
                .thresholdColor(Color.RED)
                .checkThreshold(true)
                .build();
    }

    @Override
    public Gauge buildComputerMemoryGauge(int total) {
        return GaugeBuilder
                .create()
                .skinType(Gauge.SkinType.BULLET_CHART)
                .prefSize(340, 60)
                .maxValue(total)
                .decimals(1)
                .barColor(Color.WHITE)
                .barBackgroundColor(Color.WHITE)
                .barBorderColor(Color.WHITE)
                .tickLabelColor(Color.WHITE)
                .valueColor(new Color(0, 0, 0.05, 0.9))
                .barColor(new Color(0.1, 0.7, 1, 1))
                .sectionsVisible(true)
                .sections(new Section(0, total * 0.5, new Color(0.1, 1, 0.1, 0.3)),
                        new Section(total * 0.5, total * 0.75, new Color(1, 1, 0.3, 0.3)),
                        new Section(total * 0.75, total, new Color(1, 0.1, 0.1, 0.3)))
                .majorTickSpace(1)
                .build();
    }
}

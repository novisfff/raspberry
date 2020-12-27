package cn.novisfff.raspberry.views.skin.defaultImpl;

import cn.novisfff.raspberry.views.skin.SysInfoSkin;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.Section;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;

import java.net.URL;

/**
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/26
 */

public class DefaultSysInfoSkin implements SysInfoSkin {

    private static DefaultSysInfoSkin instance;

    private DefaultSysInfoSkin() {
    }

    public static synchronized DefaultSysInfoSkin getInstance() {
        if(instance == null) {
            instance = new DefaultSysInfoSkin();
        }
        return instance;
    }

    @Override
    public URL getSysInfoFxml(Class<?> source) {
        return DefaultSysInfoSkin.class.getResource("sysInfo.fxml");
    }

    @Override
    public Image getBackground() {
        return new Image("pic/defaultRight.png");
    }

    @Override
    public Gauge buildCpuCoreGauge() {
        return GaugeBuilder
                .create()
                .skinType(Gauge.SkinType.FLAT)
                .prefSize(50, 50)
                .maxValue(100)
                .valueColor(Color.WHITE)
                .gradientBarEnabled(true)
                .gradientBarStops(new Stop(0.0, Color.LIME),
                        new Stop(0.4, Color.YELLOW),
                        new Stop(0.75, Color.RED))
                .build();
    }

    @Override
    public Tile buildCpuLoadTile() {
        return TileBuilder.create().skinType(Tile.SkinType.SMOOTH_AREA_CHART)
                .prefSize(120, 120)
                .minValue(0)
                .maxValue(100)
                .backgroundColor(new Color(0, 0, 0, 0))
                .valueColor(Color.WHITE)
                .smoothing(true)
                .chartType(Tile.ChartType.AREA)
                .tooltipText("")
                .animated(true)
                .build();
    }

    @Override
    public Gauge buildMemoryGauge(double total) {
        return GaugeBuilder
                .create()
                .skinType(Gauge.SkinType.DIGITAL)
                .prefSize(120, 120)
                .maxValue(total)
                .decimals(0)
                .valueColor(Color.WHITE)
                .majorTickMarkColor(Color.CYAN)
                .tickLabelColor(new Color(0, 0, 0.05, 0))
                .barColor(new Color(0.7, 0.7, 0.15, 1))
                .sectionsVisible(true)
                .sections(new Section(0, total * 0.5, Color.LIME),
                        new Section(total * 0.5, total * 0.75, Color.ORANGE),
                        new Section(total * 0.75, total, Color.RED))
                .build();
    }

    @Override
    public Gauge buildTemperatureGauge() {
        return GaugeBuilder
                .create()
                .skinType(Gauge.SkinType.KPI)
                .prefSize(100, 100)
                .maxValue(100)
                .valueColor(Color.WHITE)
                .barColor(Color.LIME)
                .needleColor(new Color(0, 0.73, 0.72, 1))
                .thresholdVisible(true)
                .threshold(70)
                .thresholdColor(Color.RED)
                .checkThreshold(true)
                .build();
    }
}

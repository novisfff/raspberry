package cn.novisfff.raspberry.views;

import cn.novisfff.raspberry.JavafxApplication;
import cn.novisfff.raspberry.event.StageReadyEvent;
import cn.novisfff.raspberry.utils.SysInfoUtil;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.Section;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.chart.ChartData;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;
import org.checkerframework.checker.units.qual.C;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * <h1>树莓派系统信息页面</h1>
 *
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/10
 */
@Component
public class SysInfoView implements ApplicationListener<StageReadyEvent>, UpdateSystemInfo {

    private final static int DATA_LENGTH = 20;

    private final static Logger logger = LoggerFactory.getLogger(WakeOnView.class);

    private ConfigurableApplicationContext applicationContext;

    private HomeView homeView;

    private LinkedList<ChartData> dataList;

    @FXML
    Pane cpuPane;
    @FXML
    Pane cpu0Pane;
    @FXML
    Pane cpu1Pane;
    @FXML
    Pane cpu2Pane;
    @FXML
    Pane cpu3Pane;
    @FXML
    Pane memoryPane;
    @FXML
    Pane temperaturePane;

    public Gauge cpu0LoadGauge, cpu1LoadGauge, cpu2LoadGauge, cpu3LoadGauge, memoryGauge, temperatureGauge;
    public Tile cpuLoadTile;


    public SysInfoView(ConfigurableApplicationContext applicationContext, HomeView homeView) {
        this.applicationContext = applicationContext;
        this.homeView = homeView;
    }

    /**
     * 初始化页面
     */
    @Override
    public void onApplicationEvent(StageReadyEvent stageReadyEvent) {
        Platform.runLater(() -> {
            Pane root;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(SysInfoView.class.getResource("sysInfo.fxml"));
                fxmlLoader.setControllerFactory(applicationContext::getBean);
                root = fxmlLoader.load();
                homeView.rightPane1.getChildren().add(root);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }

            cpu0LoadGauge = buildCpuCoreGauge();
            cpu0Pane.getChildren().setAll(cpu0LoadGauge);

            cpu1LoadGauge = buildCpuCoreGauge();
            cpu1Pane.getChildren().setAll(cpu1LoadGauge);

            cpu2LoadGauge = buildCpuCoreGauge();
            cpu2Pane.getChildren().setAll(cpu2LoadGauge);

            cpu3LoadGauge = buildCpuCoreGauge();
            cpu3Pane.getChildren().setAll(cpu3LoadGauge);

            cpuLoadTile = buildCpuLoadTile();
            cpuPane.getChildren().setAll(cpuLoadTile);

            int totalMemory = 3700;
            if (applicationContext.getEnvironment().getProperty("os.name").contains("Linux")) {
                totalMemory = SysInfoUtil.getTotalMemory();
            }
            memoryGauge = buildMemoryGauge(totalMemory);
            memoryPane.getChildren().setAll(memoryGauge);

            temperatureGauge = buildTemperatureGauge();
            temperaturePane.getChildren().setAll(temperatureGauge);

            homeView.rightPane1.getChildren().setAll(root);

            logger.info("加载 SysInfoView");
        });
    }

    /**
     * 构建绘制CPU单个核心使用率的{@link Gauge}
     */
    private Gauge buildCpuCoreGauge() {
        return GaugeBuilder
                .create()
                .skinType(Gauge.SkinType.FLAT)
                .prefSize(50, 50)
                .maxValue(100)
                .valueColor(new Color(0, 0, 0.05, 0.9))
                .gradientBarEnabled(true)
                .gradientBarStops(new Stop(0.0, Color.LIME),
                        new Stop(0.4, Color.YELLOW),
                        new Stop(0.75, Color.RED))
                .build();
    }

    /**
     * 构建绘制CPU总体使用率的{@link Tile}
     */
    private Tile buildCpuLoadTile() {
        dataList = new LinkedList<>();
        for (int i = 0; i < DATA_LENGTH; i++) {
            dataList.add(new ChartData("data", 0, Tile.GREEN));
        }
        return TileBuilder.create().skinType(Tile.SkinType.SMOOTH_AREA_CHART)
                .prefSize(120, 120)
                .minValue(0)
                .maxValue(100)
                .backgroundColor(new Color(0, 0, 0, 0))
                .valueColor(new Color(0,0,0.05,0.9))
                .smoothing(true)
                .chartType(Tile.ChartType.AREA)
                .chartData(dataList)
                .tooltipText("")
                .animated(true)
                .build();
    }

    /**
     * 构建绘制内存使用率的{@link Gauge}
     */
    private Gauge buildMemoryGauge(double totalMemory) {
        return GaugeBuilder
                .create()
                .skinType(Gauge.SkinType.DIGITAL)
                .prefSize(120, 120)
                .maxValue(totalMemory)
                .decimals(0)
                .valueColor(new Color(0, 0, 0.05, 0.9))
                .majorTickMarkColor(Color.CYAN)
                .tickLabelColor(new Color(0, 0, 0.05, 0))
                .barColor(new Color(0.7, 0.7, 0.15, 1))
                .sectionsVisible(true)
                .sections(new Section(0, totalMemory * 0.5, Color.LIME),
                        new Section(totalMemory * 0.5, totalMemory * 0.75, Color.ORANGE),
                        new Section(totalMemory * 0.75, totalMemory, Color.RED))
                .build();
    }

    /**
     * 构建绘制cpu温度的{@link Gauge}
     */
    private Gauge buildTemperatureGauge() {
        return GaugeBuilder
                .create()
                .skinType(Gauge.SkinType.KPI)
                .prefSize(100, 100)
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
    public void setCpuLoad(double[] loads) {
        if (cpuLoadTile != null) {
            Platform.runLater(() -> {
                if (loads != null) {
                    cpu0LoadGauge.setValue(loads[1] * 100);
                    cpu1LoadGauge.setValue(loads[2] * 100);
                    cpu2LoadGauge.setValue(loads[3] * 100);
                    cpu3LoadGauge.setValue(loads[4] * 100);
                    ChartData firstData = dataList.removeFirst();
                    firstData.setValue(loads[0] * 100);
                    dataList.addLast(firstData);
                    cpuLoadTile.removeChartData(firstData);
                    cpuLoadTile.addChartData(firstData);
                }
            });
        }
    }

    @Override
    public void setMemoryUsed(double used) {
        if (memoryGauge != null) {
            Platform.runLater(() -> memoryGauge.setValue(used));
        }
    }

    @Override
    public void setTemperature(double temperature) {
        if (temperatureGauge != null) {
            Platform.runLater(() -> temperatureGauge.setValue(temperature));
        }
    }

}



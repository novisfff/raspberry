package cn.novisfff.raspberry.views;

import cn.novisfff.raspberry.JavafxApplication;
import cn.novisfff.raspberry.event.StageReadyEvent;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedList;

/**
 * <h1>电脑信息JavaFx页面</h1>
 *
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/11
 * @see cn.novisfff.raspberry.views.schedule.UpdateSysInfoSchedule
 */
@Component
public class ComputerInfoView implements ApplicationListener<StageReadyEvent>, UpdateComputerInfo {

    private final static int DATA_LENGTH = 20;

    private final static Logger logger = LoggerFactory.getLogger(WakeOnView.class);

    private ConfigurableApplicationContext applicationContext;

    private LinkedList<ChartData> cpuDataList, gpuDataList;

    Pane computerInfoPane;

    public ComputerInfoView(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @FXML
    private Pane cpuUsedPane;
    @FXML
    private Pane cpuTempPane;
    @FXML
    private Pane gpuUsedPane;
    @FXML
    private Pane gpuTempPane;
    @FXML
    private Pane memoryPane;

    private Gauge cpuTempGauge, gpuTempGauge, memoryGauge;
    private Tile cpuUsedTile, gpuUsedTile;

    /**
     * 初始化页面
     */
    @Override
    public void onApplicationEvent(StageReadyEvent stageReadyEvent) {
        Platform.runLater(() -> {

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(ComputerInfoView.class.getResource("computerInfo.fxml"));
                fxmlLoader.setControllerFactory(applicationContext::getBean);
                computerInfoPane = fxmlLoader.load();
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
            cpuDataList = new LinkedList<>();
            for (int i = 0; i < DATA_LENGTH; i++) {
                cpuDataList.add(new ChartData("data", 0, Tile.GREEN));
            }
            cpuUsedTile = buildCpuGpuTile(cpuDataList);
            cpuUsedPane.getChildren().setAll(cpuUsedTile);

            cpuTempGauge = buildTempGauge();
            cpuTempPane.getChildren().setAll(cpuTempGauge);

            gpuDataList = new LinkedList<>();
            for (int i = 0; i < DATA_LENGTH; i++) {
                gpuDataList.add(new ChartData("data", 0, Tile.GREEN));
            }
            gpuUsedTile = buildCpuGpuTile(gpuDataList);
            gpuUsedPane.getChildren().setAll(gpuUsedTile);

            gpuTempGauge = buildTempGauge();
            gpuTempPane.getChildren().setAll(gpuTempGauge);

            //TODO 动态获取内存总量
            memoryGauge = buildMemoryGauge(16);
            memoryPane.getChildren().setAll(memoryGauge);

            logger.info("加载 ComputerInfoView");

        });
    }

    /**
     * 构建绘制CPU和GPU使用率的{@link Tile}
     */
    private Tile buildCpuGpuTile(LinkedList<ChartData> dataList) {
        return TileBuilder.create().skinType(Tile.SkinType.SMOOTH_AREA_CHART)
                .prefSize(150, 150)
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
     * 构建绘制CPU和GPU温度的{@link Gauge}
     */
    private Gauge buildTempGauge() {
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

    /**
     * 构建绘制内存使用率的{@link Gauge}
     *
     * @param totalMemory 总内存量，单位GB
     */
    private Gauge buildMemoryGauge(double totalMemory) {
        return GaugeBuilder
                .create()
                .skinType(Gauge.SkinType.BULLET_CHART)
                .prefSize(340, 60)
                .maxValue(totalMemory)
                .decimals(1)
                .barColor(Color.WHITE)
                .barBackgroundColor(Color.WHITE)
                .barBorderColor(Color.WHITE)
                .tickLabelColor(Color.WHITE)
                .valueColor(new Color(0, 0, 0.05, 0.9))
                .barColor(new Color(0.1, 0.7, 1, 1))
                .sectionsVisible(true)
                .sections(new Section(0, totalMemory * 0.5, new Color(0.1, 1, 0.1, 0.3)),
                        new Section(totalMemory * 0.5, totalMemory * 0.75, new Color(1, 1, 0.3, 0.3)),
                        new Section(totalMemory * 0.75, totalMemory, new Color(1, 0.1, 0.1, 0.3)))
                .majorTickSpace(1)
                .build();
    }

    @Override
    public void setCpuUsed(double load) {
        if (cpuUsedTile != null) {
            Platform.runLater(() -> {
                ChartData firstData = cpuDataList.removeFirst();
                firstData.setValue(load * 100);
                cpuDataList.addLast(firstData);
                cpuUsedTile.removeChartData(firstData);
                cpuUsedTile.addChartData(firstData);
            });
        }
    }

    @Override
    public void setGpuUsed(double load) {
        if (gpuUsedTile != null) {
            Platform.runLater(() -> {
                ChartData firstData = gpuDataList.removeFirst();
                firstData.setValue(load * 100);
                gpuDataList.addLast(firstData);
                gpuUsedTile.removeChartData(firstData);
                gpuUsedTile.addChartData(firstData);
            });
        }
    }

    @Override
    public void setCpuTemperature(double temperature) {
        if (cpuTempGauge != null) {
            Platform.runLater(() -> cpuTempGauge.setValue(temperature));
        }
    }

    @Override
    public void setGpuTemperature(double temperature) {
        if (gpuTempGauge != null) {
            Platform.runLater(() -> gpuTempGauge.setValue(temperature));
        }
    }

    @Override
    public void setMemoryUsed(double used) {
        if (memoryGauge != null) {
            Platform.runLater(() -> memoryGauge.setValue(used));
        }
    }
}

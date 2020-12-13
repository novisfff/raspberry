package cn.novisfff.raspberry.views;

import cn.novisfff.raspberry.JavafxApplication;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.Section;
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

/**
 * <h1>电脑信息JavaFx页面</h1>
 *
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/11
 * @see cn.novisfff.raspberry.views.schedule.UpdateSysInfoSchedule
 */
@Component
public class ComputerInfoView implements ApplicationListener<JavafxApplication.StageReadyEvent>, UpdateComputerInfo {

    private final static Logger logger = LoggerFactory.getLogger(WakeOnView.class);

    private ConfigurableApplicationContext applicationContext;

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

    private Gauge cpuUsedGauge, cpuTempGauge, gpuUsedGauge, gpuTempGauge, memoryGauge;

    /**
     * 初始化页面
     *
     * @see cn.novisfff.raspberry.JavafxApplication.StageReadyEvent
     */
    @Override
    public void onApplicationEvent(JavafxApplication.StageReadyEvent stageReadyEvent) {
        Platform.runLater(() -> {

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("computerInfo.fxml"));
                fxmlLoader.setControllerFactory(applicationContext::getBean);
                computerInfoPane = fxmlLoader.load();
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }

            cpuUsedGauge = buildCpuGpuGauge();
            cpuUsedPane.getChildren().setAll(cpuUsedGauge);

            cpuTempGauge = buildTempGauge();
            cpuTempPane.getChildren().setAll(cpuTempGauge);

            gpuUsedGauge = buildCpuGpuGauge();
            gpuUsedPane.getChildren().setAll(gpuUsedGauge);

            gpuTempGauge = buildTempGauge();
            gpuTempPane.getChildren().setAll(gpuTempGauge);

            //TODO 动态获取内存总量
            memoryGauge = buildMemoryGauge(16);
            memoryPane.getChildren().setAll(memoryGauge);

            logger.info("加载 ComputerInfoView");

        });
    }

    /**
     * 构建绘制CPU和GPU使用率的{@link Gauge}
     */
    private Gauge buildCpuGpuGauge() {
        return GaugeBuilder
                .create()
                .skinType(Gauge.SkinType.TILE_SPARK_LINE)
                .prefSize(150, 150)
                .minValue(0)
                .maxValue(100)
                .averagingPeriod(20)
                .valueColor(Color.WHITE)
                .smoothing(true)
                .backgroundPaint(new Color(0, 0, 0, 0))
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
                .valueColor(new Color(0,0,0.05,0.9))
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
                .valueColor(new Color(0,0,0.05,0.9))
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
        if(cpuUsedGauge != null) {
            Platform.runLater(() -> cpuUsedGauge.setValue(load * 100));
        }
    }

    @Override
    public void setGpuUsed(double load) {
        if(gpuUsedGauge != null) {
            Platform.runLater(() -> gpuUsedGauge.setValue(load * 100));
        }
    }

    @Override
    public void setCpuTemperature(double temperature) {
        if(cpuTempGauge != null) {
            Platform.runLater(() -> cpuTempGauge.setValue(temperature));
        }
    }

    @Override
    public void setGpuTemperature(double temperature) {
        if(gpuTempGauge != null) {
            Platform.runLater(() -> gpuTempGauge.setValue(temperature));
        }
    }

    @Override
    public void setMemoryUsed(double used) {
        if(memoryGauge != null) {
            Platform.runLater(() -> memoryGauge.setValue(used));
        }
    }
}

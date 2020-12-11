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
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author ：zyf
 * @date ：Created in 2020/12/11
 * @description：
 * @modified By：
 * @version: $
 */

@Component
public class ComputerInfoView implements ApplicationListener<JavafxApplication.StageReadyEvent> {

    private ConfigurableApplicationContext applicationContext;

    boolean isConnecting = false;

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

            memoryGauge = buildMemoryGauge();
            memoryPane.getChildren().setAll(memoryGauge);

        });
    }

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

    private Gauge buildTempGauge() {
        return GaugeBuilder
                .create()
                .skinType(Gauge.SkinType.KPI)
                .prefSize(150, 150)
                .maxValue(100)
                .valueColor(Color.WHITE)
                .barColor(Color.LIME)
                .needleColor(new Color(0,0.73,0.72,1))
                .thresholdVisible(true)
                .threshold(70)
                .thresholdColor(Color.RED)
                .checkThreshold(true)
                .build();
    }

    private Gauge buildMemoryGauge() {
        return GaugeBuilder
                .create()
                .skinType(Gauge.SkinType.BULLET_CHART)
                .prefSize(340, 60)
                .maxValue(16)
                .decimals(1)
                .barColor(Color.WHITE)
                .barBackgroundColor(Color.WHITE)
                .barBorderColor(Color.WHITE)
                .tickLabelColor(Color.WHITE)
                .valueColor(Color.WHITE)
                .barColor(new Color(0.1, 0.7, 1, 1))
                .sectionsVisible(true)
                .sections(new Section(0, 8, new Color(0.1, 1, 0.1, 0.3)),
                        new Section(8, 12, new Color(1, 1, 0.3, 0.3)),
                        new Section(12, 16, new Color(1, 0.1, 0.1, 0.3)))
                .majorTickSpace(1)
                .build();
    }

}

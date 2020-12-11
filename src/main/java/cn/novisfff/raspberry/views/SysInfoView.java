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
import javafx.scene.paint.Stop;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author ：zyf
 * @date ：Created in 2020/12/10
 * @description：
 * @modified By：
 * @version: $
 */

@Component
public class SysInfoView implements ApplicationListener<JavafxApplication.StageReadyEvent> {

    private ConfigurableApplicationContext applicationContext;

    private HomeView homeView;

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


    public Gauge cpu0LoadGauge, cpu1LoadGauge, cpu2LoadGauge, cpu3LoadGauge, cpuLoadGauge, memoryGauge, temperatureGauge;


    public SysInfoView(ConfigurableApplicationContext applicationContext, HomeView homeView) {
        this.applicationContext = applicationContext;
        this.homeView = homeView;
    }

    @Override
    public void onApplicationEvent(JavafxApplication.StageReadyEvent stageReadyEvent) {
        Platform.runLater(() -> {
            Pane root;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sysInfo.fxml"));
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

            cpuLoadGauge = buildCpuLoadGauge();
            cpuPane.getChildren().setAll(cpuLoadGauge);

            memoryGauge = buildMemoryGauge();
            memoryPane.getChildren().setAll(memoryGauge);

            temperatureGauge = buildTemperatureGauge();
            temperaturePane.getChildren().setAll(temperatureGauge);

            homeView.rightPane1.getChildren().setAll(root);
        });
    }

    private Gauge buildCpuCoreGauge() {
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

    private Gauge buildCpuLoadGauge() {
        return GaugeBuilder
                .create()
                .skinType(Gauge.SkinType.TILE_SPARK_LINE)
                .prefSize(120, 120)
                .minValue(0)
                .maxValue(100)
                .averagingPeriod(20)
                .valueColor(Color.WHITE)
                .gradientBarEnabled(true)
                .smoothing(true)
                .backgroundPaint(new Color(0, 0, 0, 0))
                .gradientBarStops(new Stop(0.0, Color.LIME),
                        new Stop(0.4, Color.YELLOW),
                        new Stop(0.75, Color.RED))
                .build();
    }

    private Gauge buildMemoryGauge() {
        return GaugeBuilder
                .create()
                .skinType(Gauge.SkinType.DIGITAL)
                .prefSize(120, 120)
                .maxValue(3741)
                .decimals(0)
                .valueColor(Color.WHITE)
                .sectionsVisible(true)
                .sections(new Section(0, 2000, Color.LIME),
                        new Section(2000, 3000, Color.ORANGE),
                        new Section(3000, 3741, Color.RED))
                .build();
    }

    private Gauge buildTemperatureGauge() {
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



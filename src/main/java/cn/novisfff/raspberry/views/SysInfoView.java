package cn.novisfff.raspberry.views;

import cn.novisfff.raspberry.JavafxApplication;
import cn.novisfff.raspberry.condition.LinuxCondition;
import cn.novisfff.raspberry.service.SysInfoUtilService;
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
import org.springframework.context.annotation.Conditional;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
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
    private Pane cpuPane;
    @FXML
    private Pane cpu0Pane;
    @FXML
    private Pane cpu1Pane;
    @FXML
    private Pane cpu2Pane;
    @FXML
    private Pane cpu3Pane;
    @FXML
    private Pane memoryPane;
    @FXML
    private Pane temperaturePane;


    Gauge CPU0LoadGauge;
    Gauge CPU1LoadGauge;
    Gauge CPU2LoadGauge;
    Gauge CPU3LoadGauge;
    Gauge CPULoadGauge;
    Gauge memoryGauge;
    Gauge temperatureGauge;

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

            CPU0LoadGauge = buildCpuCoreGauge();
            cpu0Pane.getChildren().setAll(CPU0LoadGauge);

            CPU1LoadGauge = buildCpuCoreGauge();
            cpu1Pane.getChildren().setAll(CPU1LoadGauge);

            CPU2LoadGauge = buildCpuCoreGauge();
            cpu2Pane.getChildren().setAll(CPU2LoadGauge);

            CPU3LoadGauge = buildCpuCoreGauge();
            cpu3Pane.getChildren().setAll(CPU3LoadGauge);

            CPULoadGauge = buildCpuLoadGauge();
            cpuPane.getChildren().setAll(CPULoadGauge);

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


@Component
@EnableScheduling
@Conditional(LinuxCondition.class)
class SysInfoViewSchedule {

    SysInfoView sysInfoView;

    private SysInfoUtilService sysInfoUtilService;

    public SysInfoViewSchedule(SysInfoView sysInfoView, SysInfoUtilService sysInfoUtilService) {
        this.sysInfoView = sysInfoView;
        this.sysInfoUtilService = sysInfoUtilService;
    }

    @Scheduled(initialDelay = 5000, fixedRate = 3000)
    private void sysInfoUpdateTask() {

        if(sysInfoView.CPU0LoadGauge == null) {
            return;
        }

        double[] cpuUsed = sysInfoUtilService.getCPUUsed();
        int usedMemory = sysInfoUtilService.getUsedMemory();
        double cpuTemp = sysInfoUtilService.getCPUTemp();
        Platform.runLater(() -> {
            if (cpuUsed != null) {
                sysInfoView.CPU0LoadGauge.setValue(cpuUsed[0] * 100);
                sysInfoView.CPU1LoadGauge.setValue(cpuUsed[1] * 100);
                sysInfoView.CPU2LoadGauge.setValue(cpuUsed[2] * 100);
                sysInfoView.CPU3LoadGauge.setValue(cpuUsed[3] * 100);
                sysInfoView.CPULoadGauge.setValue(cpuUsed[4] * 100);
            }
            sysInfoView.memoryGauge.setValue(usedMemory);
            sysInfoView.temperatureGauge.setValue(cpuTemp);
        });
    }

}

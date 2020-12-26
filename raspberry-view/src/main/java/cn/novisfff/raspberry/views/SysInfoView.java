package cn.novisfff.raspberry.views;

import cn.novisfff.raspberry.JavafxApplication;
import cn.novisfff.raspberry.event.StageReadyEvent;
import cn.novisfff.raspberry.utils.SysInfoUtil;
import cn.novisfff.raspberry.views.skin.SysInfoSkin;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.Section;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.chart.ChartData;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
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

    private SysInfoSkin sysInfoSkin;

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
    @FXML
    private ImageView backgroundImage;

    public Gauge cpu0LoadGauge, cpu1LoadGauge, cpu2LoadGauge, cpu3LoadGauge, memoryGauge, temperatureGauge;
    public Tile cpuLoadTile;


    public SysInfoView(ConfigurableApplicationContext applicationContext, HomeView homeView, SysInfoSkin sysInfoSkin) {
        this.applicationContext = applicationContext;
        this.homeView = homeView;
        this.sysInfoSkin = sysInfoSkin;
    }

    /**
     * 初始化页面
     */
    @Override
    public void onApplicationEvent(StageReadyEvent stageReadyEvent) {
        Platform.runLater(() -> {
            Pane root;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(sysInfoSkin.getSysInfoFxml(SysInfoView.class));
                fxmlLoader.setControllerFactory(applicationContext::getBean);
                root = fxmlLoader.load();
                homeView.rightPane1.getChildren().add(root);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }

            cpu0LoadGauge = sysInfoSkin.buildCpuCoreGauge();
            cpu0Pane.getChildren().setAll(cpu0LoadGauge);

            cpu1LoadGauge = sysInfoSkin.buildCpuCoreGauge();
            cpu1Pane.getChildren().setAll(cpu1LoadGauge);

            cpu2LoadGauge = sysInfoSkin.buildCpuCoreGauge();
            cpu2Pane.getChildren().setAll(cpu2LoadGauge);

            cpu3LoadGauge = sysInfoSkin.buildCpuCoreGauge();
            cpu3Pane.getChildren().setAll(cpu3LoadGauge);

            dataList = new LinkedList<>();
            for (int i = 0; i < DATA_LENGTH; i++) {
                dataList.add(new ChartData("data", 0, Tile.GREEN));
            }
            cpuLoadTile = sysInfoSkin.buildCpuLoadTile();
            cpuLoadTile.setChartData(dataList);
            cpuPane.getChildren().setAll(cpuLoadTile);

            int totalMemory = 3700;
            if (applicationContext.getEnvironment().getProperty("os.name").contains("Linux")) {
                totalMemory = SysInfoUtil.getTotalMemory();
            }
            memoryGauge = sysInfoSkin.buildMemoryGauge(totalMemory);
            memoryPane.getChildren().setAll(memoryGauge);

            temperatureGauge = sysInfoSkin.buildTemperatureGauge();
            temperaturePane.getChildren().setAll(temperatureGauge);

            if(sysInfoSkin.getBackground() != null) {
                backgroundImage.setImage(sysInfoSkin.getBackground());
            }
            homeView.rightPane1.getChildren().setAll(root);

            logger.info("加载 SysInfoView");
        });
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



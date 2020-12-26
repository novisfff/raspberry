package cn.novisfff.raspberry.views;

import cn.novisfff.raspberry.JavafxApplication;
import cn.novisfff.raspberry.event.StageReadyEvent;
import cn.novisfff.raspberry.views.skin.ComputerInfoSkin;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

    private ComputerInfoSkin computerInfoSkin;

    private LinkedList<ChartData> cpuDataList, gpuDataList;

    private int totalMemory = 8;

    Pane computerInfoPane;

    public ComputerInfoView(ConfigurableApplicationContext applicationContext, ComputerInfoSkin computerInfoSkin,
                            @Value("${computer.memory}")int totalMemory) {
        this.applicationContext = applicationContext;
        this.computerInfoSkin = computerInfoSkin;
        this.totalMemory = totalMemory;
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
    @FXML
    private ImageView backgroundImage;

    private Gauge cpuTempGauge, gpuTempGauge, memoryGauge;
    private Tile cpuUsedTile, gpuUsedTile;

    /**
     * 初始化页面
     */
    @Override
    public void onApplicationEvent(StageReadyEvent stageReadyEvent) {
        Platform.runLater(() -> {

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(computerInfoSkin.getComputerInfoFxml(ComputerInfoView.class));
                fxmlLoader.setControllerFactory(applicationContext::getBean);
                computerInfoPane = fxmlLoader.load();
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
            cpuDataList = new LinkedList<>();
            for (int i = 0; i < DATA_LENGTH; i++) {
                cpuDataList.add(new ChartData("data", 0, Tile.GREEN));
            }
            cpuUsedTile = computerInfoSkin.buildCpuGpuTile();
            cpuUsedTile.setChartData(cpuDataList);
            cpuUsedPane.getChildren().setAll(cpuUsedTile);

            cpuTempGauge = computerInfoSkin.buildTempGauge();
            cpuTempPane.getChildren().setAll(cpuTempGauge);

            gpuDataList = new LinkedList<>();
            for (int i = 0; i < DATA_LENGTH; i++) {
                gpuDataList.add(new ChartData("data", 0, Tile.GREEN));
            }
            gpuUsedTile = computerInfoSkin.buildCpuGpuTile();
            gpuUsedTile.setChartData(gpuDataList);
            gpuUsedPane.getChildren().setAll(gpuUsedTile);

            gpuTempGauge = computerInfoSkin.buildTempGauge();
            gpuTempPane.getChildren().setAll(gpuTempGauge);

            memoryGauge = computerInfoSkin.buildComputerMemoryGauge(totalMemory);
            memoryPane.getChildren().setAll(memoryGauge);

            if(computerInfoSkin.getBackground() != null) {
                backgroundImage.setImage(computerInfoSkin.getBackground());
            }

            logger.info("加载 ComputerInfoView");

        });
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

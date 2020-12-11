package cn.novisfff.raspberry.views;

import cn.novisfff.raspberry.JavafxApplication;
import cn.novisfff.raspberry.condition.LinuxCondition;
import cn.novisfff.raspberry.service.NetworkUtilService;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author ：zyf
 * @date ：Created in 2020/12/9
 * @description：
 * @modified By：
 * @version: $
 */


@Component
public class WakeOnView implements ApplicationListener<JavafxApplication.StageReadyEvent> {

    private ConfigurableApplicationContext applicationContext;

    private HomeView homeView;

    ComputerInfoView computerInfoView;

    NetworkUtilService networkUtilService;

    public WakeOnView(ConfigurableApplicationContext applicationContext, HomeView homeView,
                      ComputerInfoView computerInfoView, NetworkUtilService networkUtilService) {
        this.applicationContext = applicationContext;
        this.homeView = homeView;
        this.computerInfoView = computerInfoView;
        this.networkUtilService = networkUtilService;
        computerInfoPane = computerInfoView.computerInfoPane;
    }

    Pane wakeOnPane;
    Pane computerInfoPane;

    @FXML
    public Button wakeOnButton;

    @FXML
    public Pane progressPane;

    public boolean isInfoPane = false;

    @Override
    public void onApplicationEvent(JavafxApplication.StageReadyEvent stageReadyEvent) {

        Platform.runLater(() -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("wakeon.fxml"));
                fxmlLoader.setControllerFactory(applicationContext::getBean);
                wakeOnPane = fxmlLoader.load();
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }

            progressPane.setVisible(false);

            wakeOnButton.setOnMouseEntered(mouseEvent -> {
                progressPane.setVisible(true);
                networkUtilService.wakeOnLan();
            });

            switchToWakeOnPane();

        });
    }

    void switchToWakeOnPane() {
        homeView.leftPane.getChildren().setAll(wakeOnPane);
    }

    void switchToComputerInfoPane() {
        homeView.leftPane.getChildren().setAll(computerInfoPane);
    }

}



@Component
@EnableScheduling
@Conditional(LinuxCondition.class)
class WakeOnViewSchedule {

    WakeOnView wakeOnView;

    private NetworkUtilService networkUtilService;

    public WakeOnViewSchedule(WakeOnView wakeOnView, NetworkUtilService networkUtilService) {
        this.wakeOnView = wakeOnView;
        this.networkUtilService = networkUtilService;
    }

    @Scheduled(initialDelay = 2000, fixedRate = 200)
    private void wakeOnPaneCheckTask() {
        if (wakeOnView.wakeOnButton == null) {
            return;
        }
        if (networkUtilService.ping() && !wakeOnView.isInfoPane) {
            wakeOnView.isInfoPane = true;
            Platform.runLater(() -> {
                wakeOnView.progressPane.setVisible(false);
                wakeOnView.switchToComputerInfoPane();
            });
        } else if (!networkUtilService.ping() && wakeOnView.isInfoPane) {
            wakeOnView.isInfoPane = false;
            Platform.runLater(() -> wakeOnView.switchToWakeOnPane());
        }
    }

}

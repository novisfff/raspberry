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

    private NetworkUtilService networkUtilService;

    public boolean isConnecting = false;

    @FXML
    public Button wakeonButton;

    @FXML
    public Pane progressPane;

    public WakeOnView(ConfigurableApplicationContext applicationContext, HomeView homeView, NetworkUtilService networkUtilService) {
        this.applicationContext = applicationContext;
        this.homeView = homeView;
        this.networkUtilService = networkUtilService;
    }

    @Override
    public void onApplicationEvent(JavafxApplication.StageReadyEvent stageReadyEvent) {
        Platform.runLater(() -> {
            Pane root;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("wakeon.fxml"));
                fxmlLoader.setControllerFactory(applicationContext::getBean);
                root = fxmlLoader.load();
                homeView.timePane.getChildren().add(root);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }

            progressPane.setVisible(false);

            wakeonButton.setOnMouseEntered(mouseEvent -> {
                progressPane.setVisible(true);
                networkUtilService.wakeOnLan();
            });

            homeView.leftPane.getChildren().setAll(root);
        });
    }

}

@Component
@EnableScheduling
@Conditional(LinuxCondition.class)
class WakeOnViewSchedule {

    private WakeOnView wakeOnView;

    private NetworkUtilService networkUtilService;

    public WakeOnViewSchedule(WakeOnView wakeOnView, NetworkUtilService networkUtilService) {
        this.wakeOnView = wakeOnView;
        this.networkUtilService = networkUtilService;
    }

    @Scheduled(initialDelay = 2000, fixedRate = 200)
    private void wakeOnPaneCheckTask() {
        if (wakeOnView.wakeonButton == null) {
            return;
        }
        if (networkUtilService.ping() && !wakeOnView.isConnecting) {
            wakeOnView.isConnecting = true;
            Platform.runLater(() -> wakeOnView.progressPane.setVisible(true));
        } else if (!networkUtilService.ping() && wakeOnView.isConnecting) {
            wakeOnView.isConnecting = false;
            Platform.runLater(() -> wakeOnView.progressPane.setVisible(false));
        }
    }

}

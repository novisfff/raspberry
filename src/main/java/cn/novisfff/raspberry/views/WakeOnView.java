package cn.novisfff.raspberry.views;

import cn.novisfff.raspberry.JavafxApplication;
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
@EnableScheduling
public class WakeOnView implements ApplicationListener<JavafxApplication.StageReadyEvent> {

    private ConfigurableApplicationContext applicationContext;

    private HomeController homeController;

    private NetworkUtilService networkUtilService;

    public boolean isConnecting = false;

    @FXML
    public Button wakeonButton;

    @FXML
    public Pane progressPane;

    public WakeOnView(ConfigurableApplicationContext applicationContext, HomeController homeController, NetworkUtilService networkUtilService) {
        this.applicationContext = applicationContext;
        this.homeController = homeController;
        this.networkUtilService = networkUtilService;
    }

    @Override
    public void onApplicationEvent(JavafxApplication.StageReadyEvent stageReadyEvent) {
        Platform.runLater(() -> {
            Pane root = null;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("wakeon.fxml"));
                fxmlLoader.setControllerFactory(applicationContext::getBean);
                root = fxmlLoader.load();
                //Scene scene = new Scene(root);
                homeController.timePane.getChildren().add(root);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }

            progressPane.setVisible(false);

            wakeonButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    progressPane.setVisible(true);
                    networkUtilService.wakeOnLan();
                }
            });

            homeController.leftPane.getChildren().setAll(root);
        });
    }

    @Scheduled(fixedRate = 200)
    private void wakeOnPaneCheckTask() {
        if (wakeonButton == null) {
            return;
        }
        if (networkUtilService.ping() && !isConnecting) {
            isConnecting = true;
            Platform.runLater(() -> progressPane.setVisible(true));
        } else if (!networkUtilService.ping() && isConnecting) {
            isConnecting = false;
            Platform.runLater(() -> progressPane.setVisible(false));
        }
    }

}

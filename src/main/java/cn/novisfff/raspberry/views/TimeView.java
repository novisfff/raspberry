package cn.novisfff.raspberry.views;

import cn.novisfff.raspberry.JavafxApplication;
import cn.novisfff.raspberry.service.NetworkUtilService;
import eu.hansolo.medusa.Clock;
import eu.hansolo.medusa.ClockBuilder;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Locale;

/**
 * @author ：zyf
 * @date ：Created in 2020/12/9
 * @description：
 * @modified By：
 * @version: $
 */
@Component
@EnableScheduling
public class TimeView implements ApplicationListener<JavafxApplication.StageReadyEvent> {

    private ConfigurableApplicationContext applicationContext;

    private HomeController homeController;

    NetworkUtilService networkUtilService;

    @FXML
    public Pane timePane;

    public Clock timeClock;

    public TimeView(ConfigurableApplicationContext applicationContext, HomeController homeController, NetworkUtilService networkUtilService) {
        this.applicationContext = applicationContext;
        this.homeController = homeController;
        this.networkUtilService = networkUtilService;
    }

    @Override
    public void onApplicationEvent(JavafxApplication.StageReadyEvent stageReadyEvent) {
        Platform.runLater(() -> {
            Pane root = null;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("time.fxml"));
                fxmlLoader.setControllerFactory(applicationContext::getBean);
                root = fxmlLoader.load();
                //Scene scene = new Scene(root);
                homeController.timePane.getChildren().add(root);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }

            timeClock = ClockBuilder
                    .create()
                    .prefSize(300, 120)
                    .skinType(Clock.ClockSkinType.DIGITAL)
                    .locale(Locale.CHINA).textColor(Color.WHITE)
                    .backgroundPaint(new Color(0, 0, 0, 0.5))
                    .dateColor(Color.WHITE)
                    .build();

            timePane.getChildren().setAll(timeClock);

            homeController.timePane.getChildren().setAll(root);
        });
    }

    @Scheduled(fixedRate = 1000)
    private void timePaneTask() {
        if (timeClock == null) {
            return;
        }
        Platform.runLater(() -> timeClock.setTime(System.currentTimeMillis() / 1000));

    }

}

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
public class TimeView implements ApplicationListener<JavafxApplication.StageReadyEvent> {

    private ConfigurableApplicationContext applicationContext;

    private HomeView homeView;

    private NetworkUtilService networkUtilService;

    public TimeView(ConfigurableApplicationContext applicationContext, HomeView homeView, NetworkUtilService networkUtilService) {
        this.applicationContext = applicationContext;
        this.homeView = homeView;
        this.networkUtilService = networkUtilService;
    }

    @FXML
    public Pane timePane;

    public Clock timeClock;

    @Override
    public void onApplicationEvent(JavafxApplication.StageReadyEvent stageReadyEvent) {
        Platform.runLater(() -> {
            Pane root;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("time.fxml"));
                fxmlLoader.setControllerFactory(applicationContext::getBean);
                root = fxmlLoader.load();
                homeView.timePane.getChildren().add(root);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }

            timeClock = buildTimeClock();
            timePane.getChildren().setAll(timeClock);

            homeView.timePane.getChildren().setAll(root);
        });
    }

    private Clock buildTimeClock() {
        return ClockBuilder
                .create()
                .prefSize(300, 120)
                .skinType(Clock.ClockSkinType.DIGITAL)
                .locale(Locale.CHINA).textColor(Color.WHITE)
                .backgroundPaint(new Color(0, 0, 0, 0.5))
                .dateColor(Color.WHITE)
                .build();
    }

}

@Component
@EnableScheduling
class TimeViewSchedule {

    TimeView timeView;

    public TimeViewSchedule(TimeView timeView) {
        this.timeView = timeView;
    }


    @Scheduled(initialDelay = 2000, fixedRate = 1000)
    private void timePaneTask() {
        if (timeView.timeClock == null) {
            return;
        }
        Platform.runLater(() -> timeView.timeClock.setTime(System.currentTimeMillis() / 1000));

    }

}

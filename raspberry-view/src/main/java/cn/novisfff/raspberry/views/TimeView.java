package cn.novisfff.raspberry.views;

import cn.novisfff.raspberry.JavafxApplication;
import cn.novisfff.raspberry.service.NetworkUtilService;
import eu.hansolo.medusa.Clock;
import eu.hansolo.medusa.ClockBuilder;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Locale;

/**
 * <h1>显示时间页面</h1>
 *
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/9
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

    /**
     * 初始化页面
     *
     * @see cn.novisfff.raspberry.JavafxApplication.StageReadyEvent
     */
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

    /**
     * 构建显示时间的{@link Clock}
     */
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



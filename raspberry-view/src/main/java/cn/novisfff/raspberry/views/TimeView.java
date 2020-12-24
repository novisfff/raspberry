package cn.novisfff.raspberry.views;

import cn.novisfff.raspberry.JavafxApplication;
import cn.novisfff.raspberry.event.StageReadyEvent;
import eu.hansolo.medusa.Clock;
import eu.hansolo.medusa.ClockBuilder;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class TimeView implements ApplicationListener<StageReadyEvent>, UpdateTime {

    private final static Logger logger = LoggerFactory.getLogger(WakeOnView.class);

    private ConfigurableApplicationContext applicationContext;

    private HomeView homeView;

    public TimeView(ConfigurableApplicationContext applicationContext, HomeView homeView) {
        this.applicationContext = applicationContext;
        this.homeView = homeView;
    }

    @FXML
    public Pane timePane;

    public Clock timeClock;

    /**
     * 初始化页面
     *
     */
    @Override
    public void onApplicationEvent(StageReadyEvent stageReadyEvent) {
        Platform.runLater(() -> {
            Pane root;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(TimeView.class.getResource("time.fxml"));
                fxmlLoader.setControllerFactory(applicationContext::getBean);
                root = fxmlLoader.load();
                homeView.timePane.getChildren().add(root);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }

            timeClock = buildTimeClock();
            timePane.getChildren().setAll(timeClock);

            homeView.timePane.getChildren().setAll(root);

            logger.info("加载 TimeView");
        });
    }

    /**
     * 构建显示时间的{@link Clock}
     */
    private Clock buildTimeClock() {
        return ClockBuilder
                .create()
                .prefSize(300, 115)
                .skinType(Clock.ClockSkinType.DIGITAL)
                .locale(Locale.CHINA).textColor(Color.WHITE)
                .textColor(new Color(0,0,0.05,0.9))
                .dateColor(new Color(0,0,0.05,0.9))
                .build();
    }

    @Override
    public void setTime(long seconds) {
        if(timeClock != null) {
            Platform.runLater(() -> timeClock.setTime(seconds));
        }
    }

}



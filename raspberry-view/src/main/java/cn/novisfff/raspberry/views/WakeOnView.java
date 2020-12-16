package cn.novisfff.raspberry.views;

import cn.novisfff.raspberry.JavafxApplication;
import cn.novisfff.raspberry.event.StageReadyEvent;
import cn.novisfff.raspberry.utils.NetworkUtil;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * <h1>唤醒开关页面</h1>
 *
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/9
 */
@Component
public class WakeOnView implements ApplicationListener<StageReadyEvent>, UpdateWakeOnStatus{

    private final static Logger logger = LoggerFactory.getLogger(WakeOnView.class);

    private ConfigurableApplicationContext applicationContext;

    private HomeView homeView;

    private ComputerInfoView computerInfoView;


    public WakeOnView(ConfigurableApplicationContext applicationContext, HomeView homeView,
                      ComputerInfoView computerInfoView) {
        this.applicationContext = applicationContext;
        this.homeView = homeView;
        this.computerInfoView = computerInfoView;
    }

    Pane wakeOnPane;

    @FXML
    public Button wakeOnButton;

    @FXML
    public ImageView buttonImage;

    public boolean isInfoPane = false;

    RotateTransition rotateTransition;

    /**
     * 初始化页面
     */
    @Override
    public void onApplicationEvent(StageReadyEvent stageReadyEvent) {

        Platform.runLater(() -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("wakeon.fxml"));
                fxmlLoader.setControllerFactory(applicationContext::getBean);
                wakeOnPane = fxmlLoader.load();
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }

            initRotateTransition();

            wakeOnButton.setOnMouseEntered(mouseEvent -> {
                NetworkUtil.wakeOnLan();
                rotateTransition.play();
            });

            homeView.leftPane.getChildren().setAll(wakeOnPane);

            logger.info("加载 WakenOnView");

        });
    }

    @Override
    public void switchToWakeOnPane() {
        if(wakeOnPane != null && isInfoPane) {
            Platform.runLater(() -> {
                homeView.leftPane.getChildren().setAll(wakeOnPane);
                rotateTransition.stop();
                isInfoPane = false;
            });
        }
    }

    @Override
    public void switchToComputerInfoPane() {
        if(computerInfoView.computerInfoPane != null && !isInfoPane) {
            Platform.runLater(() -> {
                homeView.leftPane.getChildren().setAll(computerInfoView.computerInfoPane);
                rotateTransition.stop();
                isInfoPane = true;
            });
        }
    }

    private void initRotateTransition() {
        //开始角度
        double fromAngle=0.0;
        //结束角度
        double toAngle=360.0;
        //根据旋转角度大小计算动画播放持续时间
        double play_time =Math.abs(toAngle-fromAngle)*0.02;
        rotateTransition = new  RotateTransition(Duration.seconds(play_time), buttonImage);
        //设置旋转角度
        rotateTransition.setFromAngle(fromAngle);
        rotateTransition.setToAngle(toAngle);
        // 每次旋转后是否改变旋转方向
        rotateTransition.setCycleCount(20);
        rotateTransition.setAutoReverse(false);
    }

}


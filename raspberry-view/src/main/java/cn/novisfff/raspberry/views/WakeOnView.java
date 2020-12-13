package cn.novisfff.raspberry.views;

import cn.novisfff.raspberry.JavafxApplication;
import cn.novisfff.raspberry.utils.NetworkUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
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
public class WakeOnView implements ApplicationListener<JavafxApplication.StageReadyEvent>, UpdateWakeOnStatus{

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
    public Pane progressPane;

    public boolean isInfoPane = false;

    /**
     * 初始化页面
     * @see cn.novisfff.raspberry.JavafxApplication.StageReadyEvent
     */
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
                NetworkUtil.wakeOnLan();
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
                progressPane.setVisible(false);
                isInfoPane = false;
            });
        }
    }

    @Override
    public void switchToComputerInfoPane() {
        if(computerInfoView.computerInfoPane != null && !isInfoPane) {
            Platform.runLater(() -> {
                homeView.leftPane.getChildren().setAll(computerInfoView.computerInfoPane);
                progressPane.setVisible(false);
                isInfoPane = true;
            });
        }
    }

}


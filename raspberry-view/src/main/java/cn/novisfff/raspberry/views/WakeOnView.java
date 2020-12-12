package cn.novisfff.raspberry.views;

import cn.novisfff.raspberry.JavafxApplication;
import cn.novisfff.raspberry.utils.NetworkUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
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
public class WakeOnView implements ApplicationListener<JavafxApplication.StageReadyEvent> {

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

            switchToWakeOnPane();

        });
    }

    /**
     * 切换到唤醒开关页面
     */
    public void switchToWakeOnPane() {
        homeView.leftPane.getChildren().setAll(wakeOnPane);
    }

    /**
     * 切换到电脑信息页面
     * @see ComputerInfoView
     */
    public void switchToComputerInfoPane() {
        homeView.leftPane.getChildren().setAll(computerInfoView.computerInfoPane);
    }

}


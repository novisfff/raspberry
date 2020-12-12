package cn.novisfff.raspberry.views;

import cn.novisfff.raspberry.JavafxApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/12
 */
@Component
public class WeatherView implements ApplicationListener<JavafxApplication.StageReadyEvent> {

    @FXML
    private WebView browser;

    private String url;

    private ConfigurableApplicationContext applicationContext;

    private HomeView homeController;

    public WeatherView(ConfigurableApplicationContext applicationContext, HomeView homeController) {
        this.applicationContext = applicationContext;
        this.homeController = homeController;
    }

    @Override
    public void onApplicationEvent(JavafxApplication.StageReadyEvent stageReadyEvent) {
        Platform.runLater(() -> {
            Pane root = null;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("weather.fxml"));
                url = getClass().getResource("weather.html").toExternalForm();
                fxmlLoader.setControllerFactory(applicationContext::getBean);
                root = fxmlLoader.load();
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }

            WebEngine engine = browser.getEngine();
            engine.load(url);

            homeController.rightPane0.getChildren().setAll(root);

        });

    }
}

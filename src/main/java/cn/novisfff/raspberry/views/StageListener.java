package cn.novisfff.raspberry.views;

import cn.novisfff.raspberry.JavafxApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
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
public class StageListener implements ApplicationListener<JavafxApplication.StageReadyEvent> {

    private final String applicationTitle;
    private final ApplicationContext applicationContext;
    private final int width;
    private final int height;
    private final String rootFxml;

    public StageListener(@Value("${javafx.ui.title}")String applicationTitle,
                         @Value("${javafx.ui.width}")int width,
                         @Value("${javafx.ui.height}")int height,
                         @Value("${javafx.ui.rootFxml}")String rootFxml,
                         ApplicationContext applicationContext) {
        this.applicationTitle = applicationTitle;
        this.width = width;
        this.height = height;
        this.rootFxml = rootFxml;
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(JavafxApplication.StageReadyEvent stageReadyEvent) {

        try {
            Stage stage = stageReadyEvent.getStage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(rootFxml));
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, width, height);
            scene.setCursor(Cursor.NONE);
            Font font = Font.loadFont(getClass().getResource("SourceHanSansCNBold.otf").toExternalForm(), 14);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.setTitle(this.applicationTitle);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

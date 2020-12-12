package cn.novisfff.raspberry.views;

import cn.novisfff.raspberry.JavafxApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * <h1>首页JavaFx页面</h1>
 * 构建首页页面，同时初始化JavaFx{@link Stage}
 *
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/9
 */
@Component
public class HomeView implements ApplicationListener<JavafxApplication.StageReadyEvent> {

    private final static Logger logger = LoggerFactory.getLogger(WakeOnView.class);

    @FXML
    Pane rightPane2;

    @FXML
    Pane rightPane3;

    @FXML
    Pane rightPane0;

    @FXML
    Pane rightPane1;

    @FXML
    Pane leftPane;

    @FXML
    Pane timePane;

    /**
     * 窗口标题，通过javafx.ui.title属性配置
     */
    private final String applicationTitle;

    /**
     * 窗口宽度，通过javafx.ui.width属性配置
     */
    private final int width;

    /**
     * 窗口高度，通过javafx.ui.height属性配置
     */
    private final int height;

    /**
     * 主页Fxml文件，通过javafx.ui.rootFxml属性配置
     */
    private final String rootFxml;

    /**
     * Spring上下文
     */
    private final ApplicationContext applicationContext;

    public HomeView(@Value("${javafx.ui.title}") String applicationTitle,
                    @Value("${javafx.ui.width}") int width,
                    @Value("${javafx.ui.height}") int height,
                    @Value("${javafx.ui.rootFxml}") String rootFxml,
                    ApplicationContext applicationContext) {
        this.applicationTitle = applicationTitle;
        this.width = width;
        this.height = height;
        this.rootFxml = rootFxml;
        this.applicationContext = applicationContext;
    }

    /**
     * 初始化页面
     *
     * @see cn.novisfff.raspberry.JavafxApplication.StageReadyEvent
     */
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

        logger.info("加载 HomeView");

    }
}

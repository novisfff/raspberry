package cn.novisfff.raspberry.views;

import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author ：张宇飞
 * @date ：Created in 2020/9/2 20:35
 * @description：
 * @modified By：
 * @version: $
 */

@Component
public class HomeController {

    private final HostServices hostServices;

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

    @FXML
    Button button;

    @FXML
    public Label label;

    public HomeController(HostServices hostServices) {
        this.hostServices = hostServices;
    }

}

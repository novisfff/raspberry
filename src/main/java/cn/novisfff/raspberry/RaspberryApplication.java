package cn.novisfff.raspberry;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author novisfff
 */
@SpringBootApplication
public class RaspberryApplication{

    public static void main(String[] args) {
        Application.launch(JavafxApplication.class, args);
    }

}

package cn.novisfff.raspberry;

import javafx.application.Application;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

/**
 * 启动JavaFx服务
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/18
 */
@Service
public class RaspberryViewMainServer implements ApplicationListener<ApplicationReadyEvent> {

    private ConfigurableApplicationContext context;

    public RaspberryViewMainServer(ConfigurableApplicationContext context) {
        this.context = context;
    }

    /**
     * Spring启动完成后，启动JavaFx
     */
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        JavafxApplication.applicationContext = context;
        new Thread(() -> {
            Application.launch(JavafxApplication.class);
        }).run();
    }

}

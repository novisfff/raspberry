package cn.novisfff.raspberry;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;

import javafx.stage.Stage;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;


/**
 * <h1>JavaFx Application 启动类</h1>
 *
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/9
 */

public class JavafxApplication extends Application {

    /**
     * Spring 上下文
     */
    private ConfigurableApplicationContext context;

    /**
     * 重写初始化方法
     * <p>
     * 在JavaFx启动线程中初始化Spring，并将javaFx相关组件注册到Spring中
     *
     * @author novisfff
     */
    @Override
    public void init() {

        ApplicationContextInitializer<GenericApplicationContext> initializer = ac -> {
            ac.registerBean(Application.class, () -> this);
            ac.registerBean(Parameters.class, this::getParameters);
            ac.registerBean(HostServices.class, this::getHostServices);
        };
        this.context = new SpringApplicationBuilder()
                .sources(RaspberryApplication.class)
                .initializers(initializer)
                .run(getParameters().getRaw().toArray(new String[0]));
    }

    /**
     * 重写启动方法
     * <p>
     * 在启动时发布{@link cn.novisfff.raspberry.JavafxApplication.StageReadyEvent}事件，
     * 表明JavaFx相关视图部分可以进行初始化
     *
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        this.context.publishEvent(new StageReadyEvent(primaryStage));
    }

    /**
     * 重写stop方法
     * <p>
     * 关闭Spring与JavaFx
     *
     */
    @Override
    public void stop() {
        this.context.close();
        Platform.exit();
    }

    /**
     * StageReady事件类
     */
    public static class StageReadyEvent extends ApplicationEvent {

        public Stage getStage() {
            return Stage.class.cast(getSource());
        }

        public StageReadyEvent(Object source) {
            super(source);
        }
    }

}
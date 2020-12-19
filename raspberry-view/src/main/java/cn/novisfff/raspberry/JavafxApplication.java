package cn.novisfff.raspberry;

import cn.novisfff.raspberry.event.StageReadyEvent;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;



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
    static ConfigurableApplicationContext applicationContext;


    /**
     * 重写初始化方法
     * <p>
     * 将javaFx相关组件注册到Spring中
     *
     * @author novisfff
     */
    @Override
    public void init() {
        applicationContext.getBeanFactory().registerResolvableDependency(Application.class, this);
        applicationContext.getBeanFactory().registerResolvableDependency(Parameters.class, this.getParameters());
        applicationContext.getBeanFactory().registerResolvableDependency(HostServices.class, this.getHostServices());
    }

    /**
     * 重写启动方法
     * <p>
     * 在启动时发布{@link cn.novisfff.raspberry.event.StageReadyEvent}事件，
     * 表明JavaFx相关视图部分可以进行初始化
     *
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        applicationContext.publishEvent(new StageReadyEvent(primaryStage));
    }

    /**
     * 重写stop方法
     * <p>
     * 关闭Spring与JavaFx
     *
     */
    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }


}
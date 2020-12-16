package cn.novisfff.raspberry.event;

import javafx.stage.Stage;
import org.springframework.context.ApplicationEvent;

/**
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/16
 */

public class StageReadyEvent extends ApplicationEvent {

    public Stage getStage() {
        return Stage.class.cast(getSource());
    }

    public StageReadyEvent(Object source) {
        super(source);
    }
}

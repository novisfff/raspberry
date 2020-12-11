package cn.novisfff.raspberry.views.schedule;

import cn.novisfff.raspberry.views.TimeView;
import javafx.application.Platform;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 显示时间页面定时器
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/11
 * @see TimeView
 */

@Component
public class TimeViewSchedule {

    TimeView timeView;

    public TimeViewSchedule(TimeView timeView) {
        this.timeView = timeView;
    }


    @Scheduled(cron = "*/1 * * * * ?")
    private void timePaneTask() {
        if (timeView.timeClock == null) {
            return;
        }
        Platform.runLater(() -> timeView.timeClock.setTime(System.currentTimeMillis() / 1000));

    }

}
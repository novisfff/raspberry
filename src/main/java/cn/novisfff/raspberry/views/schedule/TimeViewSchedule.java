package cn.novisfff.raspberry.views.schedule;

import cn.novisfff.raspberry.views.TimeView;
import javafx.application.Platform;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author ：zyf
 * @date ：Created in 2020/12/11
 * @description：
 * @modified By：
 * @version: $
 */

@Component
class TimeViewSchedule {

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
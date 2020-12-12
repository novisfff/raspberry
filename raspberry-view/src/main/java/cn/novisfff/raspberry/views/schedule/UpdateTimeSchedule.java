package cn.novisfff.raspberry.views.schedule;

import cn.novisfff.raspberry.views.TimeView;
import cn.novisfff.raspberry.views.UpdateTime;
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
public class UpdateTimeSchedule {

    UpdateTime updateTime;

    public UpdateTimeSchedule(UpdateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Scheduled(cron = "*/1 * * * * ?")
    private void timePaneTask() {
        updateTime.setTime(System.currentTimeMillis() / 1000);
    }

}
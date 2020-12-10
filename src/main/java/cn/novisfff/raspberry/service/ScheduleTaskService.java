package cn.novisfff.raspberry.service;

import cn.novisfff.raspberry.views.HomeController;
import cn.novisfff.raspberry.views.TimeView;
import cn.novisfff.raspberry.views.WakeOnView;
import javafx.application.Platform;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author ：zyf
 * @date ：Created in 2020/12/9
 * @description：
 * @modified By：
 * @version: $
 */

@Component
@EnableScheduling
public class ScheduleTaskService {

    HomeController homeController;

    NetworkUtilService networkUtilService;

    WakeOnView wakeOnView;

    TimeView timeView;

    public ScheduleTaskService(HomeController homeController, NetworkUtilService networkUtilService, WakeOnView wakeOnView, TimeView timeView) {
        this.homeController = homeController;
        this.networkUtilService = networkUtilService;
        this.wakeOnView = wakeOnView;
        this.timeView = timeView;
    }

    @Scheduled(fixedRate = 1000)
    private void timePaneTask() {
        System.out.println(1);
        if (timeView.timeClock == null) {
            return;
        }
        System.out.println(2);
        Platform.runLater(() -> timeView.timeClock.setTime(System.currentTimeMillis() / 1000));

    }

    @Scheduled(fixedRate = 200)
    private void wakeOnPaneCheckTask() {
        if (wakeOnView.wakeonButton == null) {
            return;
        }
        if (networkUtilService.ping() && !wakeOnView.isConnecting) {
            wakeOnView.isConnecting = true;
            Platform.runLater(() -> wakeOnView.progressPane.setVisible(true));
        } else if (!networkUtilService.ping() && wakeOnView.isConnecting) {
            wakeOnView.isConnecting = false;
            Platform.runLater(() -> wakeOnView.progressPane.setVisible(false));
        }
    }

}

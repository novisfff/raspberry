package cn.novisfff.raspberry.views.schedule;

import cn.novisfff.raspberry.condition.LinuxCondition;
import cn.novisfff.raspberry.service.NetworkUtilService;
import cn.novisfff.raspberry.views.WakeOnView;
import javafx.application.Platform;
import org.springframework.context.annotation.Conditional;
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
@Conditional(LinuxCondition.class)
class WakeOnViewSchedule {

    private WakeOnView wakeOnView;

    private NetworkUtilService networkUtilService;

    public WakeOnViewSchedule(WakeOnView wakeOnView, NetworkUtilService networkUtilService) {
        this.wakeOnView = wakeOnView;
        this.networkUtilService = networkUtilService;
    }

    @Scheduled(initialDelay = 2000, fixedDelay = 500)
    private void wakeOnPaneCheckTask() {
        if (wakeOnView.wakeOnButton == null) {
            return;
        }
        boolean pingResult = networkUtilService.ping();
        if (pingResult && !wakeOnView.isInfoPane) {
            wakeOnView.isInfoPane = true;
            Platform.runLater(() -> {
                wakeOnView.progressPane.setVisible(false);
                wakeOnView.switchToComputerInfoPane();
            });
        } else if (!pingResult && wakeOnView.isInfoPane) {
            wakeOnView.isInfoPane = false;
            Platform.runLater(() -> wakeOnView.switchToWakeOnPane());
        }
    }

}

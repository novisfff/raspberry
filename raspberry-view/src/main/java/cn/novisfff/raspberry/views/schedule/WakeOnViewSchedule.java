package cn.novisfff.raspberry.views.schedule;

import cn.novisfff.raspberry.condition.LinuxCondition;
import cn.novisfff.raspberry.utils.NetworkUtil;
import cn.novisfff.raspberry.views.WakeOnView;
import javafx.application.Platform;
import org.springframework.context.annotation.Conditional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//TODO 重写这部分的接口

/**
 * 唤醒页面定时器
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/11
 * @see WakeOnView
 * @see cn.novisfff.raspberry.views.SysInfoView
 */

@Component
@Conditional(LinuxCondition.class)
public class WakeOnViewSchedule {

    private WakeOnView wakeOnView;

    public WakeOnViewSchedule(WakeOnView wakeOnView) {
        this.wakeOnView = wakeOnView;
    }

    @Scheduled(initialDelay = 2000, fixedDelay = 500)
    private void wakeOnPaneCheckTask() {
        if (wakeOnView.wakeOnButton == null) {
            return;
        }
        boolean pingResult = NetworkUtil.ping();
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

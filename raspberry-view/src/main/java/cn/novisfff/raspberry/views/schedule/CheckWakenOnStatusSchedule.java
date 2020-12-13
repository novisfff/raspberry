package cn.novisfff.raspberry.views.schedule;

import cn.novisfff.raspberry.condition.LinuxCondition;
import cn.novisfff.raspberry.utils.NetworkUtil;
import cn.novisfff.raspberry.views.UpdateWakeOnStatus;
import cn.novisfff.raspberry.views.WakeOnView;
import org.springframework.context.annotation.Conditional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * 唤醒页面定时器
 *
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/11
 * @see WakeOnView
 * @see cn.novisfff.raspberry.views.SysInfoView
 */

@Component
@Conditional(LinuxCondition.class)
public class CheckWakenOnStatusSchedule {

    private UpdateWakeOnStatus updateWakeOnStatus;

    public CheckWakenOnStatusSchedule(UpdateWakeOnStatus updateWakeOnStatus) {
        this.updateWakeOnStatus = updateWakeOnStatus;
    }

    @Scheduled(initialDelay = 2000, fixedDelay = 500)
    private void wakeOnPaneCheckTask() {

        if (NetworkUtil.ping()) {
            updateWakeOnStatus.switchToComputerInfoPane();
        } else {
            updateWakeOnStatus.switchToWakeOnPane();
        }

    }

}

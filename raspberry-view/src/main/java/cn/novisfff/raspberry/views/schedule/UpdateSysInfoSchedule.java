package cn.novisfff.raspberry.views.schedule;

import cn.novisfff.raspberry.condition.LinuxCondition;
import cn.novisfff.raspberry.utils.SysInfoUtil;
import cn.novisfff.raspberry.views.SysInfoView;
import cn.novisfff.raspberry.views.UpdateSystemInfo;
import javafx.application.Platform;
import org.springframework.context.annotation.Conditional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 系统消息页面定时任务
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/11
 * @see SysInfoView
 */

@Component
@Conditional(LinuxCondition.class)
public class UpdateSysInfoSchedule {

    private UpdateSystemInfo updateSystemInfo;

    public UpdateSysInfoSchedule(UpdateSystemInfo updateSystemInfo) {
        this.updateSystemInfo = updateSystemInfo;
    }

    @Scheduled(initialDelay = 5000, fixedRate = 3000)
    private void sysInfoUpdateTask() {

        double[] cpuUsed = SysInfoUtil.getCPUUsed();
        int usedMemory = SysInfoUtil.getUsedMemory();
        double cpuTemp = SysInfoUtil.getCPUTemp();
        updateSystemInfo.setCpuLoad(cpuUsed);
        updateSystemInfo.setMemoryUsed(usedMemory);
        updateSystemInfo.setTemperature(cpuTemp);
    }

}

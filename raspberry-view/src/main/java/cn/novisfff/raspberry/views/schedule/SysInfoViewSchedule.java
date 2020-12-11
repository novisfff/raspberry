package cn.novisfff.raspberry.views.schedule;

import cn.novisfff.raspberry.condition.LinuxCondition;
import cn.novisfff.raspberry.service.SysInfoUtilService;
import cn.novisfff.raspberry.views.SysInfoView;
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
public class SysInfoViewSchedule {

    private SysInfoView sysInfoView;

    private SysInfoUtilService sysInfoUtilService;

    public SysInfoViewSchedule(SysInfoView sysInfoView, SysInfoUtilService sysInfoUtilService) {
        this.sysInfoView = sysInfoView;
        this.sysInfoUtilService = sysInfoUtilService;
    }

    @Scheduled(initialDelay = 5000, fixedRate = 3000)
    private void sysInfoUpdateTask() {

        if(sysInfoView.cpu0LoadGauge == null) {
            return;
        }

        double[] cpuUsed = sysInfoUtilService.getCPUUsed();
        int usedMemory = sysInfoUtilService.getUsedMemory();
        double cpuTemp = sysInfoUtilService.getCPUTemp();
        Platform.runLater(() -> {
            if (cpuUsed != null) {
                sysInfoView.cpu0LoadGauge.setValue(cpuUsed[0] * 100);
                sysInfoView.cpu1LoadGauge.setValue(cpuUsed[1] * 100);
                sysInfoView.cpu2LoadGauge.setValue(cpuUsed[2] * 100);
                sysInfoView.cpu3LoadGauge.setValue(cpuUsed[3] * 100);
                sysInfoView.cpuLoadGauge.setValue(cpuUsed[4] * 100);
            }
            sysInfoView.memoryGauge.setValue(usedMemory);
            sysInfoView.temperatureGauge.setValue(cpuTemp);
        });
    }

}

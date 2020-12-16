package cn.novisfff.raspberry.views.eventhander;

import cn.novisfff.raspberry.event.PcDataMessageReadyEvent;
import cn.novisfff.raspberry.views.UpdateComputerInfo;
import javafx.application.Platform;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/16
 */
@Component
public class PcDataHeader implements ApplicationListener<PcDataMessageReadyEvent> {

    private UpdateComputerInfo updateComputerInfo;

    public PcDataHeader(UpdateComputerInfo updateComputerInfo) {
        this.updateComputerInfo = updateComputerInfo;
    }

    @Async
    @Override
    public void onApplicationEvent(PcDataMessageReadyEvent pcDataMessageReadyEvent) {
        String pcData = pcDataMessageReadyEvent.getPcData();
        for (String each : pcData.split("::")) {
            String[] rawData = each.split("<>");
            String dataType = rawData[0];
            switch (dataType) {
                case "GPU_LOAD":
                    updateComputerInfo.setGpuUsed(Double.parseDouble(rawData[1]));
                    break;
                case "CPU_LOAD":
                    updateComputerInfo.setCpuUsed(Double.parseDouble(rawData[1]));
                    break;
                case "CPU_TEMP":
                    updateComputerInfo.setCpuTemperature(Double.parseDouble(rawData[1]));
                    break;
                case "GPU_TEMP":
                    updateComputerInfo.setGpuTemperature(Double.parseDouble(rawData[1]));
                    break;
                case "USED_RAM":
                    updateComputerInfo.setMemoryUsed(Double.parseDouble(rawData[1]));
                    break;
                default:
                    break;
            }
        }
    }
}

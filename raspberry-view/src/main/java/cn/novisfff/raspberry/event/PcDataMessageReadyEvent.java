package cn.novisfff.raspberry.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/16
 */

public class PcDataMessageReadyEvent extends ApplicationEvent {

    public PcDataMessageReadyEvent(Object source) {
        super(source);
    }

    public String getPcData() {
        return String.valueOf(getSource());
    }

}

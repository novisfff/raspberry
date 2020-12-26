package cn.novisfff.raspberry.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/15
 */

@Component
public class UtilPropertites {

    static String ip;

    static String mac;

    static String broadcast;

    public UtilPropertites(@Value("${computer.ip}")String ip,
                       @Value("${computer.mac}")String mac,
                       @Value("${computer.broadcast}")String broadcast) {
        UtilPropertites.ip = ip;
        UtilPropertites.mac = mac;
        UtilPropertites.broadcast = broadcast;
    }
}

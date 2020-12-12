package cn.novisfff.raspberry.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


import java.io.IOException;

/**
 * 测试与电脑链接和启动电脑工具服务
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/9
 */

@Component
public class NetworkUtil{


    static String ip;

    static String mac;

    static String broadcast;

    public NetworkUtil(@Value("${networkUtil.ip}")String ip,
                              @Value("${networkUtil.mac}")String mac,
                              @Value("${networkUtil.broadcast}")String broadcast) {
        NetworkUtil.ip = ip;
        NetworkUtil.mac = mac;
        NetworkUtil.broadcast = broadcast;
    }

    /**
     * 通过指令wakeonlan启动电脑
     */
    public static void wakeOnLan() {
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("wakeonlan -i " + broadcast + " " + mac);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回是否能ping通电脑
     */
    public static boolean ping() {
        System.out.println(ip);
        try {
            return 0 == Runtime.getRuntime().exec("ping -c 1 " + ip).waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

package cn.novisfff.raspberry.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author ：zyf
 * @date ：Created in 2020/12/9
 * @description：
 * @modified By：
 * @version: $
 */

@Component
public class NetworkUtilService {

    private final String ip;
    private final String mac;
    private final String broadcast;

    public NetworkUtilService(@Value("${networkUtil.ip}")String ip,
                              @Value("${networkUtil.mac}")String mac,
                              @Value("${networkUtil.broadcast}")String broadcast) {
        this.ip = ip;
        this.mac = mac;
        this.broadcast = broadcast;
    }

    public void wakeOnLan() {
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("wakeonlan -i " + broadcast + " " + mac);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean ping() {
        try {
            return 0 == Runtime.getRuntime().exec("ping -c 1 " + ip).waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

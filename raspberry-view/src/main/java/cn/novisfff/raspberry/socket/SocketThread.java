package cn.novisfff.raspberry.socket;

import cn.novisfff.raspberry.event.PcDataMessageReadyEvent;
import cn.novisfff.raspberry.utils.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;


/**
 * Socket线程
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/16
 */

@Component("socketThread")
public class SocketThread extends Thread {

    private Logger logger = LoggerFactory.getLogger(SocketThread.class);

    private ConfigurableApplicationContext applicationContext;

    @Autowired
    public ConfigurableApplicationContext getApplicationContext() {
        return applicationContext;
    }

    private int port;

    private ServerSocket serverSocket = null;
    Socket socket;
    BufferedReader bufferedReader;
    boolean isConnected;

    public SocketThread(@Value("${socket.port}") int port) {
        this.port = port;
        if (serverSocket == null) {
            try {
                serverSocket = new ServerSocket(port);
                logger.info("创建TCP连接成功 " + port);
            } catch (Exception e) {
                logger.error("创建Socket服务异常" + e);
            }
        }
    }

    @Override
    public void run() {
        logger.info("服务已启动...进入到长连接状态等待请求...");
        while (true) {
            try {
                socket = serverSocket.accept();
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                isConnected = true;
                while (isConnected) {
                    try {
                        String message = bufferedReader.readLine();
                        logger.debug(message);
                        String[] msg = message.split("!!");
                        String msgHeader = msg[0];
                        switch (msgHeader) {
                            case "PC_DATA":
                                SpringContextUtil.getApplicationContext().publishEvent(new PcDataMessageReadyEvent(msg[1]));
                                break;
                            default:
                                break;
                        }
                    } catch (Exception e) {
                        isConnected = false;
                        logger.info("连接断开");
                    }

                }
            } catch (IOException e) {
                logger.error("服务端接收请求异常" + e);
            } finally {
                try {
                    if (socket != null) {
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /***
     * 线程销毁方法
     */
    public void closeSocketServe() {
        if (null != serverSocket && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

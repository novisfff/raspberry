package cn.novisfff.raspberry.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/16
 */

@Component
public class SocketListen implements ServletContextListener {
    private SocketThread socketThread;
    private Logger log = LoggerFactory.getLogger(SocketListen.class);

    /**
     * 初始化方法
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("初始化启动Socket服务...");
        if (socketThread == null) {
            socketThread = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext()).getBean(SocketThread.class);
            socketThread.start();
        }
    }

    /**
     * 销毁方法
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("将Socket服务关闭...");
        if (null != socketThread && !socketThread.isInterrupted()) {
            socketThread.closeSocketServe();
            socketThread.interrupt();
        }
    }
}



package cn.novisfff.raspberry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/12
 */

@Service
public class TestServer {

    private final static Logger logger = LoggerFactory.getLogger(TestServer.class);

    public TestServer() {
        logger.info("TestServer启动成功");
    }
}

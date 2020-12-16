package cn.novisfff.raspberry.server;

import cn.novisfff.raspberry.server.Impl.HeWeatherImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/16
 */
@Configuration
public class ServerRegister {

    private ConfigurableApplicationContext applicationContext;

    @Value("${heweather.key}")
    private String key;

    @Value("${heweather.location}")
    private String location;

    @Value("${socket.port}")
    private int port;

    public ServerRegister(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public WeatherServer weatherServer() {
        return new HeWeatherImpl(key, location);
    }

}

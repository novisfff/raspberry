package cn.novisfff.raspberry.views.schedule;

import cn.novisfff.raspberry.domain.Weather;
import cn.novisfff.raspberry.server.HeWeather;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/13
 */
@Component
public class UpdateWeatherSchedule {

    HeWeather heWeather;

    public UpdateWeatherSchedule(HeWeather heWeather) {
        this.heWeather = heWeather;
    }

    @Scheduled(initialDelay = 5000, fixedRate = 300000)
    private void weatherUpdateTask() {
        Weather weatherNow = heWeather.getWeatherNow();
        System.out.println(weatherNow);
    }
}

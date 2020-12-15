package cn.novisfff.raspberry.views.schedule;

import cn.novisfff.raspberry.domain.Weather;
import cn.novisfff.raspberry.server.HeWeather;
import cn.novisfff.raspberry.views.UpdateWeather;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/13
 */
@Component
public class UpdateWeatherSchedule {

    HeWeather heWeather;

    UpdateWeather updateWeather;

    public UpdateWeatherSchedule(HeWeather heWeather, UpdateWeather updateWeather) {
        this.heWeather = heWeather;
        this.updateWeather = updateWeather;
    }

    @Scheduled(initialDelay = 5000, fixedRate = 600000)
    private void weatherUpdateNowTask() {
        Weather weatherNow = heWeather.getWeatherNow();
        updateWeather.updateNow(weatherNow);
    }

    @Scheduled(initialDelay = 5000, fixedRate = 36000000)
    private void weatherUpdateThreeTask() {
        Weather[] threeDay = heWeather.getThreeDay();
        updateWeather.updateThreeDay(threeDay);
    }
}

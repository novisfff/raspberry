package cn.novisfff.raspberry.views;

import cn.novisfff.raspberry.domain.Weather;

/**
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/15
 */
public interface UpdateWeather {

    /**
     * 更新当前天气
     * @param weather
     */
    void updateNow(Weather weather);

    /**
     * 更新三天天气
     * @param weathers
     */
    void updateThreeDay(Weather[] weathers);
}

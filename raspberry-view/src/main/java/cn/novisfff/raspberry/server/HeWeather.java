package cn.novisfff.raspberry.server;

import cn.novisfff.raspberry.domain.Weather;

/**
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/13
 */

public interface HeWeather {

    /**
     * 获取当前天气
     */
    Weather getWeatherNow();

    /**
     * 获取3天的天气
     */
    Weather[] getThreeDay();
}

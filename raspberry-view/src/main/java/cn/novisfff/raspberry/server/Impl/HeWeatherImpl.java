package cn.novisfff.raspberry.server.Impl;

import cn.novisfff.raspberry.domain.Weather;
import cn.novisfff.raspberry.server.HeWeather;
import cn.novisfff.raspberry.utils.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Multimaps;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/13
 */
@Service
public class HeWeatherImpl implements HeWeather {

    private final static String NOW_URL = "https://devapi.qweather.com/v7/weather/now";
    private final static String THREE_DAY_URL = "https://devapi.qweather.com/v7/weather/3d";
    private final static String AIR_URL = "https://devapi.qweather.com/v7/air/now";

    @Value("${heweather.key}")
    private String key;

    @Value("${heweather.location}")
    private String location;


    @Override
    public Weather getWeatherNow() {
        Weather weather = new Weather();
        updateNow(weather);
        updateAir(weather);
        return weather;
    }

    @Override
    public Weather[] getThreeDay() {
        Weather[] weathers = new Weather[3];
        updateThreeDay(weathers);
        return weathers;
    }

    private void updateNow(Weather weather) {
        Map<String, String> paramMap = new HashMap<>(8);
        paramMap.put("key", key);
        paramMap.put("location", location);
        String response = getResponse(NOW_URL, paramMap);
        JSONObject jsonObject = JSON.parseObject(response).getJSONObject("now");
        weather.setIcon(jsonObject.getInteger("icon"));
        weather.setTemp(jsonObject.getInteger("temp"));
        weather.setText(jsonObject.getString("text"));
    }

    private void updateAir(Weather weather) {
        Map<String, String> paramMap = new HashMap<>(8);
        paramMap.put("key", key);
        paramMap.put("location", location);
        String response = getResponse(AIR_URL, paramMap);
        JSONObject jsonObject = JSON.parseObject(response).getJSONObject("now");
        weather.setAir(jsonObject.getString("category"));
    }

    private void updateThreeDay(Weather[] weathers) {
        Map<String, String> paramMap = new HashMap<>(8);
        paramMap.put("key", key);
        paramMap.put("location", location);
        String response = getResponse(THREE_DAY_URL, paramMap);
        JSONArray jsonArray = JSON.parseObject(response).getJSONArray("daily");
        for (int i = 0; i < 3; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            weathers[i] = new Weather();
            weathers[i].setText(jsonObject.getString("textDay"));
            weathers[i].setTempMin(jsonObject.getInteger("tempMin"));
            weathers[i].setTempMax(jsonObject.getInteger("tempMax"));
            weathers[i].setIcon(jsonObject.getInteger("iconDay"));
        }
    }

    private String getResponse(String url, Map<String, String> paramMap) {
        Map<String, String> headerMap = new HashMap<>(4);
        HttpUtil http = HttpUtil.getHttp(url, Multimaps.forMap(paramMap), headerMap);
        http.setConnectTimeout(5000);
        http.setSocketTimeout(7000);
        return http.execute().getString();
    }

}

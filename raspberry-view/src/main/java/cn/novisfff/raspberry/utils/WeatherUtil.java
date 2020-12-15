package cn.novisfff.raspberry.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/15
 */

public class WeatherUtil {

    private static final Map<String, AirMessage> map = new HashMap<>(){{
        put("优", new AirMessage("优", "#99CC66"));
        put("良", new AirMessage("良", "#CCCC00"));
        put("轻度污染", new AirMessage("轻", "#FF9933"));
        put("中度污染", new AirMessage("中", "#CC3333"));
        put("重度污染", new AirMessage("重", "#660066"));
        put("严重污染", new AirMessage("危", "#330033"));
    }};

    public static AirMessage getAirMessage(String s) {
        return map.get(s);
    }

    public static class AirMessage {

        private String text;
        private String color;

        AirMessage(String text, String color) {
            this.text = text;
            this.color = color;
        }

        public String getText() {
            return text;
        }

        public String getColor() {
            return color;
        }
    }
}

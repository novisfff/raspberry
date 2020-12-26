package cn.novisfff.raspberry.views.skin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/26
 */
@Component
public class SkinRegister {

    private String skinName;

    private Skin skin;

    public SkinRegister(@Value("${javafx.ui.skin}") String skinName) {
        this.skinName = skinName.toUpperCase();
        skin = SkinEnum.valueOf(skinName).getSkin();
    }

    @Bean
    ComputerInfoSkin computerInfoSkin() {
        return skin.getComputerInfoSkin();
    }

    @Bean
    HomeSkin homeSkin() {
        return skin.getHomeSkin();
    }

    @Bean
    SysInfoSkin sysInfoSkin() {
        return skin.getSysInfoSkin();
    }

    @Bean
    TimeSkin timeSkin() {
        return skin.getTimeSkin();
    }

    @Bean
    WakeOnSkin wakeOnSkin() {
        return skin.getWakeOnSkin();
    }

    @Bean
    WeatherSkin weatherSkin() {
        return skin.getWeatherSkin();
    }

}

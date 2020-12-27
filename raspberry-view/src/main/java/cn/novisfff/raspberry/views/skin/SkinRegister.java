package cn.novisfff.raspberry.views.skin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/26
 */
@Component
public class SkinRegister {

    private final static Logger logger = LoggerFactory.getLogger(SkinRegister.class);

    private static final String DEFAULT_SKIN = "DEFAULT";

    private String skinName;

    private Skin skin;

    public SkinRegister(@Value("${javafx.ui.skin}") String skinName) {
        this.skinName = skinName.toUpperCase();
        initSkin();
    }

    /**
     * 初始化皮肤
     */
    private void initSkin() {
        SkinEnum skinEnum;
        try {
            skinEnum = SkinEnum.valueOf(skinName);
        } catch (Exception e) {
            skinEnum = SkinEnum.valueOf(DEFAULT_SKIN);
            logger.warn("未找到指定皮肤或未指定皮肤，加载默认皮肤");
        }

        skin = skinEnum.getSkin();
        logger.info("加载" + skinEnum.name() + "皮肤");
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

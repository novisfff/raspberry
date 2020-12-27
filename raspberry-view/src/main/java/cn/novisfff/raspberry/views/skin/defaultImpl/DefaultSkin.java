package cn.novisfff.raspberry.views.skin.defaultImpl;


import cn.novisfff.raspberry.views.skin.*;

/**
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/26
 */

public class DefaultSkin implements Skin {

    private static DefaultSkin instance;

    private DefaultSkin() {
    }

    public static synchronized DefaultSkin getInstance() {
        if(instance == null) {
            instance = new DefaultSkin();
        }
        return instance;
    }

    @Override
    public ComputerInfoSkin getComputerInfoSkin() {
        return DefaultComputerInfoSkin.getInstance();
    }

    @Override
    public HomeSkin getHomeSkin() {
        return DefaultHomeSkin.getInstance();
    }

    @Override
    public SysInfoSkin getSysInfoSkin() {
        return DefaultSysInfoSkin.getInstance();
    }

    @Override
    public TimeSkin getTimeSkin() {
        return DefaultTimeSkin.getInstance();
    }

    @Override
    public WakeOnSkin getWakeOnSkin() {
        return DefaultWakeOnSkin.getInstance();
    }

    @Override
    public WeatherSkin getWeatherSkin() {
        return DefaultWeatherSkin.getInstance();
    }

}

package cn.novisfff.raspberry.views.skin.cyberImpl;


import cn.novisfff.raspberry.views.skin.*;

/**
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/26
 */

public class CyberSkin implements Skin {

    private static CyberSkin instance;

    private CyberSkin() {
    }

    public static synchronized CyberSkin getInstance() {
        if(instance == null) {
            instance = new CyberSkin();
        }
        return instance;
    }

    @Override
    public ComputerInfoSkin getComputerInfoSkin() {
        return CyberComputerInfoSkin.getInstance();
    }

    @Override
    public HomeSkin getHomeSkin() {
        return CyberHomeSkin.getInstance();
    }

    @Override
    public SysInfoSkin getSysInfoSkin() {
        return CyberSysInfoSkin.getInstance();
    }

    @Override
    public TimeSkin getTimeSkin() {
        return CyberTimeSkin.getInstance();
    }

    @Override
    public WakeOnSkin getWakeOnSkin() {
        return CyberWakeOnSkin.getInstance();
    }

    @Override
    public WeatherSkin getWeatherSkin() {
        return CyberWeatherSkin.getInstance();
    }

}

package cn.novisfff.raspberry.views.skin;

import cn.novisfff.raspberry.views.skin.cyberImpl.CyberSkin;
import cn.novisfff.raspberry.views.skin.defaultImpl.DefaultSkin;

/**
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/26
 */
public enum SkinEnum {

    /**
     * 默认蓝色皮肤
     */
    DEFAULT(DefaultSkin.getInstance()),

    /**
     * 赛博朋克皮肤
     */
    CYBER(CyberSkin.getInstance());


    private final Skin skin;

    SkinEnum(Skin skin) {
        this.skin = skin;
    }

    public Skin getSkin() {
        return skin;
    }
}

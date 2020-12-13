package cn.novisfff.raspberry.views;

/**
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/13
 */
public interface UpdateWakeOnStatus {

    /**
     * 切换到唤醒开关页面
     * @see WakeOnView
     */
    void switchToWakeOnPane();

    /**
     * 切换到电脑信息页面
     * @see ComputerInfoView
     */
    void switchToComputerInfoPane();

}

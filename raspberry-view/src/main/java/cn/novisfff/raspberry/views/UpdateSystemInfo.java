package cn.novisfff.raspberry.views;

/**
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/12
 */
public interface UpdateSystemInfo {

    /**
     * 更新cpu占用率
     * @param loads loads数组第一位为总占用率，后几位为每个内核的占用率
     */
    void setCpuLoad(double[] loads);

    /**
     * 更新内存占用率
     * @param used 内存占用量
     */
    void setMemoryUsed(double used);

    /**
     * 更新CPU温度
     * @param temperature 温度
     */
    void setTemperature(double temperature);

}

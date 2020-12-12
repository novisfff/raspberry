package cn.novisfff.raspberry.views;

/**
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/12
 */
public interface UpdateComputerInfo {

    /**
     * 更新CPU占用
     * @param load
     */
    void setCpuUsed(double load);

    /**
     * 更新GPU占用
     * @param load
     */
    void setGpuUsed(double load);

    /**
     * 更新CPU温度
     * @param temperature
     */
    void setCpuTemperature(double temperature);

    /**
     * 更新GPU温度
     * @param temperature
     */
    void setGpuTemperature(double temperature);

    /**
     * 更新内存占用量
     * @param used
     */
    void setMemoryUsed(double used);
}

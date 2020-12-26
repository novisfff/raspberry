package cn.novisfff.raspberry.views.skin;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.tilesfx.Tile;
import javafx.scene.image.Image;

import java.net.URL;

/**
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/26
 */
public interface SysInfoSkin {

    /**
     * 获取SysInfoView的Fxml文件Url
     * @param source classPath
     * @return SysInfoView的Fxml文件Url
     */
    URL getSysInfoFxml(Class<?> source);

    /**
     * 获取SystemInfoView背景图
     * @return SystemInfoView背景图
     */
    Image getBackground();

    /**
     * 构建绘制CPU单个核心使用率的{@link Gauge}
     * @return CPU单个核心使用率Gauge
     */
    Gauge buildCpuCoreGauge();

    /**
     * 构建绘制CPU总体使用率的{@link Tile}
     * @return CPU总体使用率Tile
     */
    Tile buildCpuLoadTile();

    /**
     * 构建绘制内存使用率的{@link Gauge}
     * @return 内存使用率Gauge
     */
    Gauge buildMemoryGauge(double total);

    /**
     * 构建绘制cpu温度的{@link Gauge}
     * @return cpu温度Gauge
     */
    Gauge buildTemperatureGauge();

}

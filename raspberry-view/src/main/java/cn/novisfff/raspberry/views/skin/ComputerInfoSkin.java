package cn.novisfff.raspberry.views.skin;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.tilesfx.Tile;
import javafx.scene.image.Image;

import java.net.URL;

/**
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/26
 */
public interface ComputerInfoSkin {

    /**
     * 获取ComputerInfoView的Fxml文件Url
     * @param source classPath
     * @return ComputerInfoView的Fxml文件Url
     */
    URL getComputerInfoFxml(Class<?> source);

    /**
     * 获取ComputerInfoView背景图
     * @return ComputerInfoView背景图
     */
    Image getBackground();

    /**
     * 构建绘制CPU和GPU使用率的{@link Tile}
     * @return CPU和GPU使用率Tile
     */
    Tile buildCpuGpuTile();

    /**
     * 构建绘制CPU和GPU温度的{@link Gauge}
     * @return CPU和GPU温度Gauge
     */
    Gauge buildTempGauge();

    /**
     * 构建绘制内存使用率的{@link Gauge}
     * @param total 总内存
     * @return 内存使用率的Gauge
     */
    Gauge buildComputerMemoryGauge(int total);


}

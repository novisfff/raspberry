package cn.novisfff.raspberry.service;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 获取linux系统信息工具服务
 * @author ：<a href="156125813@qq.com">novisfff</a>
 * @date ：Created in 2020/12/10
 */

@Component
public class SysInfoUtilService {

    /**
     * @see Sigar
     */
    private static Sigar sigar;

    static {
        sigar = new Sigar();
    }

    /**
     * description: 获取CPU占用（0-1）
     * create time: 2020/9/7
     *
     * @param
     * @return double[] 最后一位为平均占用率，前几位为每颗CPU占用
     */
    public double[] getCPUUsed() {

        CpuPerc cpuList[] = null;

        try {
            cpuList = sigar.getCpuPercList();
            double[] doubles = new double[cpuList.length + 1];
            double totalUsed = 0;
            for (int i = 0; i < cpuList.length; i++) {
                doubles[i] = cpuList[i].getCombined();
                totalUsed += doubles[i];
            }
            doubles[cpuList.length] = totalUsed / cpuList.length;
            return doubles;
        } catch (SigarException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * description: 获取占用内存（MB）
     * create time: 2020/9/7
     *
     * @param
     * @return int 占用内存（MB）
     */
    public int getUsedMemory() {

        try {
            Mem mem = sigar.getMem();
            return new Long(mem.getActualUsed() / (1024L * 1024L)).intValue();
        } catch (SigarException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取cpu温度信息
     * @return
     */
    public double getCPUTemp() {
        // 获取当前程序的运行进对象
        Runtime runtime = Runtime.getRuntime();
        Process process = null;
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            process = runtime.exec("cat /sys/class/thermal/thermal_zone0/temp");
            is = process.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            return Double.parseDouble(br.readLine()) / 1000;
        } catch (IOException e) {
            System.out.println(e);
            return 0;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                isr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

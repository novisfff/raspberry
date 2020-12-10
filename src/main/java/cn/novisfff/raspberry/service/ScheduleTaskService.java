package cn.novisfff.raspberry.service;

import cn.novisfff.raspberry.views.HomeController;
import cn.novisfff.raspberry.views.TimeView;
import cn.novisfff.raspberry.views.WakeOnView;
import javafx.application.Platform;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author ：zyf
 * @date ：Created in 2020/12/9
 * @description：
 * @modified By：
 * @version: $
 */

public class ScheduleTaskService {

    HomeController homeController;

    NetworkUtilService networkUtilService;

    WakeOnView wakeOnView;

    TimeView timeView;


}

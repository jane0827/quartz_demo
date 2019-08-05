package com.jane.quartz_demo.test.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: jane
 * @Date: 2019/8/5 21:44
 */
@Component
public class ScheduleTask {

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Scheduled(cron = "0/10 * * * * ?")
    public void fixTimeExecution() {
        System.out.println("在指定时间 " + format.format(new Date()) + "执行");
    }
}

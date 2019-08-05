package com.jane.quartz_demo.test.controller;

import com.jane.quartz_demo.config.Job;
import com.jane.quartz_demo.config.ScheduleManager;
import lombok.Data;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: jane
 * @Date: 2019/8/5 16:49
 */
@RestController
public class TestController {

    @Autowired
    private ScheduleManager scheduleManager;

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @GetMapping("/startA")
    public void startA() {
        JobA jobA = new JobA();
        jobA.setName("test");

        try {
            scheduleManager.start(new Job<JobA>() {
                @Override
                public void run() {
                    runTest(this.getGroup(), this.getNextFireTime());
                }
            }.name(jobA.getName()).target(jobA).group(jobA.getGroup()).cron(jobA.getCron()));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }

    @GetMapping("/startB")
    public void startB() {
        JobB jobB = new JobB();
        jobB.setName("test");

        try {
            scheduleManager.start(new Job<JobB>() {
                @Override
                public void run() {
                    runTest(this.getGroup(), this.getNextFireTime());
                }
            }.name(jobB.getName()).target(jobB).group(jobB.getGroup()).cron(jobB.getCron()));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }

    @GetMapping("/parse")
    public void parse() {
        JobA jobA = new JobA();
        jobA.setName("test");
        try {
            scheduleManager.pause(jobA.getName(), jobA.getGroup());
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/resume")
    public void resume() {
        JobA jobA = new JobA();
        jobA.setName("test");
        try {
            scheduleManager.resume(jobA.getName(), jobA.getGroup());
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/delete")
    public void delete() {
        JobA jobA = new JobA();
        jobA.setName("test");
        try {
            scheduleManager.delete(jobA.getName(), jobA.getGroup());
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    private void runTest(String group, Date nextFireTime) {
        System.out.println(group + "执行成功！" + "nextFireTime: " + format.format(nextFireTime));
    }


    @Data
    public class JobA {
        private String id;
        private String name;
        private String cron = "0/15 * * * * ?";
        private String group = "typeA";
    }

    @Data
    public class JobB {
        private String id;
        private String name;
        private String cron = "0 0/5 * * * ?";
        private String group = "typeB";
    }
}


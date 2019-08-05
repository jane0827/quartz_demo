package com.jane.quartz_demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @Author: jane
 * @Date: 2019/8/5 16:12
 */
@Configuration
@EnableScheduling
public class JobSchedule {

    @Autowired
    private JobFactory jobFactory;


    @Bean(name = "scheduler")
    public SchedulerFactoryBean schedulerFactory() {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setOverwriteExistingJobs(true);
        // 延时启动
        // factory.setStartupDelay(20);
        // 自定义Job Factory，用于Spring注入
        factory.setJobFactory(jobFactory);
        return factory;
    }
}

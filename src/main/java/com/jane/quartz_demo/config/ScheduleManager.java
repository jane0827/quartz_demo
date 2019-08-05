package com.jane.quartz_demo.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

/**
 * @Author: jane
 * @Date: 2019/8/5 16:14
 */
@Component
public class ScheduleManager {

    private static final Log log = LogFactory.getLog(ScheduleManager.class);

    @Autowired
    @Qualifier(value = "scheduler")
    private SchedulerFactoryBean factoryBean;


    public void start(Job<?> job) throws SchedulerException {
        Scheduler scheduler = factoryBean.getScheduler();
        log.info(scheduler + "---add job: " + job.getName() + "---cron: " + job.getCron());
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getName(), job.getGroup());

        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCron());
        if (trigger == null) {
            JobDetail jobDetail = JobBuilder.newJob(JobRunner.class).withIdentity(job.getName(), job.getGroup()).build();
            jobDetail.getJobDataMap().put("scheduleJob", job);
            trigger = TriggerBuilder.newTrigger().withIdentity(job.getName(), job.getGroup()).withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, trigger);
        } else {
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            scheduler.rescheduleJob(triggerKey, trigger);
        }
    }

    /**
     * 暂停
     * @param name
     * @param group
     * @throws SchedulerException
     */
    public void pause(String name, String group) throws SchedulerException {
        Scheduler scheduler = factoryBean.getScheduler();
        JobKey jobKey = new JobKey(name, group);
        if (scheduler.checkExists(jobKey)) {
            log.info(scheduler + "---pause job: " + name + " " + group);
            scheduler.pauseJob(jobKey);
        }
    }

    /**
     * 恢复
     * @param name
     * @param group
     * @throws SchedulerException
     */
    public void resume(String name, String group) throws SchedulerException {
        Scheduler scheduler = factoryBean.getScheduler();
        JobKey jobKey = new JobKey(name, group);
        if (scheduler.checkExists(jobKey)) {
            log.info(scheduler + "---resume job: " + name + " " + group);
            scheduler.resumeJob(jobKey);
        }
    }

    /**
     * 删除
     * @param name
     * @param group
     * @throws SchedulerException
     */
    public void delete(String name, String group) throws SchedulerException {
        Scheduler scheduler = factoryBean.getScheduler();
        JobKey jobKey = new JobKey(name, group);
        if (scheduler.checkExists(jobKey)) {
            log.info(scheduler + "---delete job: " + name + " " + group);
            scheduler.deleteJob(jobKey);
        }
    }

    /**
     * 暂停所有
     * @throws SchedulerException
     */
    public void pauseAll() throws SchedulerException {
        Scheduler scheduler = factoryBean.getScheduler();
        scheduler.pauseAll();
        log.info(scheduler + "---pauseAll job");
    }

    /**
     * 恢复所有任务
     * @throws SchedulerException
     */
    public void resumeAll() throws SchedulerException {
        Scheduler scheduler = factoryBean.getScheduler();
        scheduler.resumeAll();
        log.info(scheduler + "---resumeAll job");
    }

    /**
     * 清空所有任务
     * @throws SchedulerException
     */
    public void clearAll() throws SchedulerException {
        Scheduler scheduler = factoryBean.getScheduler();
        scheduler.clear();
        log.info(scheduler + "---clearAll job");
    }

}

package com.jane.quartz_demo.config;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Author: jane
 * @Date: 2019/8/5 18:27
 */
public class JobRunner implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        com.jane.quartz_demo.config.Job<?> job = (com.jane.quartz_demo.config.Job<?>) context.getMergedJobDataMap()
                .get("scheduleJob");
        job.setNextFireTime(context.getNextFireTime());
        job.run();
    }
}

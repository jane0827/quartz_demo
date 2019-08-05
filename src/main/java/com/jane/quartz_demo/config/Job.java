package com.jane.quartz_demo.config;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Job抽象类
 * @Author: jane
 * @Date: 2019/8/2 17:45
 */
@Data
@NoArgsConstructor
public abstract class Job<T> {

    private String name;

    private String group;

    // cron 表达式
    private String cron;

    // 下一次触发时间
    private Date nextFireTime;

    // 目标job
    private T target;

    public abstract void run();

    public Job<T> name(String name) {
        this.name = name;
        return this;
    }

    public Job<T> group(String group) {
        this.group = group;
        return this;
    }

    public Job<T> cron(String cron) {
        this.cron = cron;
        return this;
    }

    public Job<T> target(T target) {
        this.target = target;
        return this;
    }
}

package com.example.dynamic.scheduling.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class ScheduleConfig {

    /*
     值得注意的是：
        默认配置下，ThreadPoolTaskScheduler的初始化线程数是一，执行定时方法的线程只有一个，故任务之间会挤占。
        Spring在任务调度时，fixedRate, fixedDelay 或 cron 只是决定提交任务到线程池的时刻，至于真正执行任务的时间就看有没有空闲的线程。
        这种情况一般不满足需求，所以需要自定义配置任务调度的线程池，有足够的线程，就不会出现挤占的情况。

        当corn表达式报错 expression must consist of 6 fields时，原因是：不支持年位定时
            Spring提供的任务调度毕竟不是quartz，只是简单的任务调度框架，比起jdk Timer就加入了线程池而以
            org.springframework.scheduling.support.CronSequenceGenerator中说明了不支持年位定位

    相关：
        任务调度自动化配置类：org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration
        任务调度注册类：org.springframework.scheduling.config.ScheduledTaskRegistrar
        注解式任务调度处理类：org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor
    */

}

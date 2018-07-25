package com.example.springframework.boot.dynamic.spring.scheduler.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Slf4j
@Configuration
@EnableScheduling
public class ScheduleConfig implements InitializingBean {

    @Autowired
    private ScheduleHandler scheduleHandler;

    /*
    值得注意的是：当corn表达式报错 expression must consist of 6 fields时，原因是：
        不支持年位定时,它毕竟不是quartz,只是简单的定时框架,比起jdk Timer就加入了线程池而以.
        org.springframework.scheduling.support.CronSequenceGenerator中说明了不支持年位定位
    */

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        //线程池名的前缀
        threadPoolTaskScheduler.setThreadNamePrefix("taskThreadPool");
        //核心线程数：线程池创建时候初始化的线程数
        threadPoolTaskScheduler.setPoolSize(20);
        //设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean，这样这些异步任务的销毁就会先于其他bean的销毁
        threadPoolTaskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        //用来设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住
        threadPoolTaskScheduler.setAwaitTerminationSeconds(60);
        return threadPoolTaskScheduler;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //初始化任务配置
        scheduleHandler.init();
        //启动任务
        scheduleHandler.start();
    }

    /**
     * 定时刷新定时任务配置
     */
    @Scheduled(cron = "0 1/1 * * * ?")
    void refresh() {
        scheduleHandler.refresh();
    }
}

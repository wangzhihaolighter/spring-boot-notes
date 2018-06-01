package com.example.springframework.boot.schedule.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.Executor;

@Configuration
@EnableScheduling
public class ScheduleConfig {

    /*
    注解说明：
        @EnableScheduling：启用定时任务的配置
        @Scheduled：
            fixedRate：下一个任务不会等到上个任务结束之后才开始，无论如何，在 n(ms)之后，一个新的任务就会开始执行
            fixedDelay：固定时延，上一个任务的结束时间和下一个任务的开始时间之间的时间间隔是固定的。下一个任务总是等上一个任务结束之后，并等待固定时延之后才开始
            initialDelay：初始时延,任务的第一次运行会是在进程启动的n(ms)之后
            cron：通过cron表达式定义规则
            zone：给cron表达式用的时区
                时区参考：http://www.cnblogs.com/tracy/archive/2010/07/16/1778566.html
                中国时区：GMT+8 格林威治标准时间+8小时

     值得注意的是：schedule，执行定时方法的线程是同一个（哪怕两个component），故任务之间会挤占，可以理解为
         Spring在任务调度时，fixedRate, fixedDelay 或 cron 只是决定提交任务到线程池的时刻，至于真正执行任务的时间就看有没有空闲的线程，因此最终决定于线程池的配置
         默认配置下，同一时刻执行任务的线程只有一个
         这种情况一般不满足需求，所以需要配置用于定时任务执行的线程池，有足够的线程，就不会出现挤占的情况

    值得注意的是：当corn表达式报错 expression must consist of 6 fields时，原因是：
        不支持年位定时,它毕竟不是quartz,只是简单的定时框架,比起jdk Timer就加入了线程池而以.
        org.springframework.scheduling.support.CronSequenceGenerator中说明了不支持年位定位

    实现：
    定时任务注册类：org.springframework.scheduling.config.ScheduledTaskRegistrar
    注解式定时任务处理类：org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor
    */

    @Bean
    public Executor taskScheduler() {
        ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
        //核心线程数20：线程池创建时候初始化的线程数
        executor.setPoolSize(20);
        //线程池名的前缀：设置好了之后可以方便我们定位处理任务所在的线程池
        executor.setThreadNamePrefix("taskScheduler-");
        //设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean，这样这些异步任务的销毁就会先于其他bean的销毁
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //用来设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住
        executor.setAwaitTerminationSeconds(60);
        return executor;
    }

}

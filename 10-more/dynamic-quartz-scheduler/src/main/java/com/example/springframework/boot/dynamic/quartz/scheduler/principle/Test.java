package com.example.springframework.boot.dynamic.quartz.scheduler.principle;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.TimeUnit;

public class Test {

    public static void main(String[] args) throws SchedulerException, InterruptedException {
        /*
         *在 Quartz 中， scheduler 由 scheduler 工厂创建：DirectSchedulerFactory 或者StdSchedulerFactory。第二种工厂 StdSchedulerFactory 使用较多，
         *因为 DirectSchedulerFactory 使用起来不够方便，需要作许多详细的手工编码设置。
         */

        // 获取Scheduler实例
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        System.out.println("scheduler.start");

        //具体任务.
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("job", "group").build();

        //触发时间点
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger", "group").startNow().withSchedule(simpleScheduleBuilder).build();

        // 交由Scheduler安排触发
        scheduler.scheduleJob(jobDetail, trigger);

        //睡眠
        TimeUnit.SECONDS.sleep(20);
        scheduler.shutdown();//关闭定时任务调度器.
        System.out.println("scheduler.shutdown");
    }

}

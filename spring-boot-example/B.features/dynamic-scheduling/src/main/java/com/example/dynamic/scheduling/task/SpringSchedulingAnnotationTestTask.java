package com.example.dynamic.scheduling.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Spring注解式定时任务测试任务
 */
@Slf4j
@Component
public class SpringSchedulingAnnotationTestTask {

    /*
        @Scheduled：
            fixedRate：下一个任务不会等到上个任务结束之后才开始，无论如何，在n(ms)之后，一个新的任务就会开始执行
            fixedDelay：固定时延，上一个任务的结束时间和下一个任务的开始时间之间的时间间隔是固定的。下一个任务总是等上一个任务结束之后，并等待固定时延之后才开始
            initialDelay：初始时延,任务的第一次运行会是在进程启动的n(ms)之后
            cron：通过cron表达式定义规则
            zone：给cron表达式用的时区
                时区类型：java.util.TimeZone
                中国时区：GMT+8 格林威治标准时间+8小时
     */

    /**
     * 0秒后开始执行，方法每10秒执行一次
     */
    @Scheduled(initialDelay = 0, fixedRate = 10000)
    public void test1() {
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("[test1] initialDelay = 0, fixedRate = 10000, timestamp: " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

    /**
     * 10秒后开始执行，方法执行结束后10秒再重复执行
     */
    @Scheduled(initialDelay = 10000, fixedDelay = 10000)
    public void test2() {
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("[test2] initialDelay = 10000, fixedDelay = 10000, timestamp:" + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

    /**
     * 从0分0秒开始，每分钟执行一次
     * zone时区：ZoneId.systemDefault()
     */
    @Scheduled(cron = "0 0/1 * * * ?", zone = "GMT+8")
    public void test3() {
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("[test3] cron = \"0 0/1 * * * ?\", zone = \"GMT+8\", timestamp:" + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

}

package com.example.springframework.boot.schedule.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class SimpleScheduleTask {
    private final static Logger log = LoggerFactory.getLogger(SimpleScheduleTask.class);

    /**
     * 0秒后开始执行，方法每10秒执行一次
     */
    @Scheduled(initialDelay = 0, fixedRate = 10000)
    public void doSomethingOne() {
        //do something
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("[schedule one] do something,time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

    /**
     * 10秒后开始执行，方法执行结束后10秒再重复执行
     */
    @Scheduled(initialDelay = 10000, fixedDelay = 10000)
    public void doSomethingTwo() {
        //do something
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("[schedule two] do something,time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

    /**
     * 每分钟执行一次，从0分0秒开始
     * zone时区:ZoneId.systemDefault()
     */
    @Scheduled(cron = "0 0/1 * * * ? ", zone = "Asia/Shanghai")
    public void doSomethingThree() {
        //do something
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("[schedule three] do something,time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

}

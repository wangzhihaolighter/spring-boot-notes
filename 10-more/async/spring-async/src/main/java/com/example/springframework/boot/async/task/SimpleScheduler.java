package com.example.springframework.boot.async.task;

import com.example.springframework.boot.async.config.async.AsyncConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Future;

@Slf4j
@Component
public class SimpleScheduler {

    private static Random random = new Random();

    @Value("${management.endpoints.web.exposure.include}")
    private String include;

    @Async(AsyncConfig.SCHEDULER_ID)
    public Future<Long> doSchedulerOne() {
        String taskName = "程序一";
        return doTaskFuture(taskName);
    }

    @Async(AsyncConfig.SCHEDULER_ID)
    public Future<Long> doSchedulerTwo() {
        String taskName = "程序二";
        return doTaskFuture(taskName);
    }

    @Async(AsyncConfig.SCHEDULER_ID)
    public Future<Long> doSchedulerThree() {
        String taskName = "程序三";
        return doTaskFuture(taskName);
    }

    private Future<Long> doTaskFuture(String taskName) {
        log.info("开始执行"+taskName);
        long start = System.currentTimeMillis();

        //properties
        log.info("scheduler three include:" + include);

        //sleep
        try {
            Thread.sleep(random.nextInt(10000));
        } catch (InterruptedException e) {
            log.error("task thread sleep interrupted");
        }

        //do something
        for (int i = 0; i < 1000; i++) {
            Date date = new Date();
        }

        long end = System.currentTimeMillis();
        log.info("完成"+taskName+"，耗时：" + (end - start) + "毫秒");
        return new AsyncResult<>(end - start);
    }

}

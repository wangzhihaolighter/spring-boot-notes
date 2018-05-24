package com.example.springframework.boot.async.task;

import com.example.springframework.boot.async.config.async.AsyncConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Random;

@Slf4j
@Component
public class SimpleTask {

    private static Random random = new Random();

    @Value("${management.endpoints.web.exposure.include}")
    private String include;

    @Async(AsyncConfig.EXECUTOR_ID)
    public void doTaskOne() {
        log.info("开始做任务一");
        long start = System.currentTimeMillis();
        log.info("task one include:" + include);
        try {
            Thread.sleep(random.nextInt(10000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        log.info("完成任务一，耗时：" + (end - start) + "毫秒");
    }

    @Async(AsyncConfig.EXECUTOR_ID)
    public void doTaskTwo() {
        log.info("开始做任务二");
        log.info("task two include:" + include);
        long start = System.currentTimeMillis();
        try {
            Thread.sleep(random.nextInt(10000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        log.info("完成任务二，耗时：" + (end - start) + "毫秒");
    }

    @Async(AsyncConfig.EXECUTOR_ID)
    public void doTaskThree() {
        log.info("开始做任务三");
        long start = System.currentTimeMillis();
        log.info("task three include:" + include);
        try {
            Thread.sleep(random.nextInt(10000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        log.info("完成任务三，耗时：" + (end - start) + "毫秒");
    }

}

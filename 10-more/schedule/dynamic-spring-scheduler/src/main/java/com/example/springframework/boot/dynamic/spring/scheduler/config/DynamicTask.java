package com.example.springframework.boot.dynamic.spring.scheduler.config;

import lombok.Data;
import org.springframework.scheduling.support.CronTrigger;

import java.util.concurrent.Future;

@Data
public class DynamicTask {
    /**
     * 任务
     */
    private Runnable taskRunnable;
    /**
     * corn触发器
     */
    private CronTrigger cronTrigger;
    /**
     * 异步结果
     */
    private Future<?> future;
}

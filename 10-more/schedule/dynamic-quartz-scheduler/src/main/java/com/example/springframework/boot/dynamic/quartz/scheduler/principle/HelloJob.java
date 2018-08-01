package com.example.springframework.boot.dynamic.quartz.scheduler.principle;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 任务类
 */
public class HelloJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // 执行响应的任务.
        System.out.println("HelloJob.execute," + new SimpleDateFormat("yyyy-MM-dd HH:ss:mm").format(new Date()));
    }

}
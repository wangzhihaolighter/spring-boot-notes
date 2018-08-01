package com.example.springframework.boot.dynamic.quartz.scheduler.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class SimpleJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("[ SIMPLE JOB ] executeInternal : " + new SimpleDateFormat("yyyy-MM-dd HH:ss:mm").format(new Date()));
    }
}

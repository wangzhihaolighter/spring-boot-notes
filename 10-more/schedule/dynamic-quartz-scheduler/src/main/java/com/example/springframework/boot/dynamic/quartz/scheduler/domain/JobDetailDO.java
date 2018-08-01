package com.example.springframework.boot.dynamic.quartz.scheduler.domain;

import lombok.Data;
import org.quartz.*;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 任务抽象业务类 Quartz JodDetail 等价类
 */
@Data
public class JobDetailDO {

    private JobDO jobDO;

    private Set<TriggerDO> triggerDOs;

    /**
     * 处理job
     */
    public transient Consumer<JobDetail> fillWithQuartzJobDetail = jobDetail -> {
        jobDO = new JobDO();

        // job
        JobKey jobKey = jobDetail.getKey();

        // name group desc
        BeanUtils.copyProperties(jobKey, jobDO);
        jobDO.setDescription(jobDetail.getDescription());
        jobDO.setTargetClass(jobDetail.getJobClass().getCanonicalName());

        // ext
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        if (Objects.nonNull(jobDataMap)) {
            jobDO.setExtInfo(jobDataMap.getWrappedMap());
        }

        this.setJobDO(jobDO);
    };

    /**
     * 处理triggers
     */
    public transient Consumer<List<Trigger>> fillWithQuartzTriggers = triggerList -> {
        // triggers
        Set<TriggerDO> triggerDOSet = triggerList.stream().map(trigger -> {
            TriggerDO triggerDO = new TriggerDO();
            if (trigger instanceof CronTrigger) {
                CronTrigger ctr = (CronTrigger) trigger;
                triggerDO.setCronExpression(ctr.getCronExpression());
            }
            TriggerKey trk = trigger.getKey();
            triggerDO.setName(trk.getName());
            triggerDO.setGroup(trk.getGroup());
            triggerDO.setDescription(trigger.getDescription());
            return triggerDO;
        }).collect(Collectors.toSet());
        this.setTriggerDOs(triggerDOSet);
    };

}

package com.example.springframework.boot.dynamic.quartz.scheduler.service.impl;

import com.example.springframework.boot.dynamic.quartz.scheduler.domain.JobDetailDO;
import com.example.springframework.boot.dynamic.quartz.scheduler.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class JobServiceImpl implements JobService {

    /**
     * 注入任务调度器
     */
    @Qualifier("schedulerFactoryBean")
    @Autowired
    private Scheduler scheduler;

    @Override
    public List<JobDetailDO> queryJobList() {
        List<JobDetailDO> jobDetailDOs = null;

        //定义函数：传入jobKey任务key列表获取JobDetailDO任务信息列表
        Function<Set<JobKey>, List<JobDetailDO>> copyPropFun = jobKeySet -> {
            List<JobDetailDO> jobDetailDOList;
            jobDetailDOList = jobKeySet.stream().map(jobKey -> {
                //JobDetail - quartz任务
                JobDetail jobDetail = this.getJobDetailByKey(jobKey);

                //triggerList - quartz触发器列表
                List<Trigger> triggerList = this.getTriggerByKey(jobKey);

                //jobDetailDO
                JobDetailDO jobDetailDO = new JobDetailDO();
                jobDetailDO.fillWithQuartzJobDetail.accept(jobDetail);
                jobDetailDO.fillWithQuartzTriggers.accept(triggerList);
                //获取调度器的执行状态
                getTriggerState(jobDetailDO);
                return jobDetailDO;
            }).collect(Collectors.toList());
            return jobDetailDOList;
        };

        //获取任务列表
        try {
            Set<JobKey> jobSet = scheduler.getJobKeys(GroupMatcher.anyJobGroup());
            jobDetailDOs = copyPropFun.apply(jobSet);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return jobDetailDOs;
    }

    @Override
    public JobDetailDO queryByJobKey(JobKey jobKey) {
        JobDetailDO jobDetailDO = null;

        //get quartz jobDetail
        JobDetail jobDetail = this.getJobDetailByKey(jobKey);
        if (Objects.nonNull(jobDetail)) {
            jobDetailDO = new JobDetailDO();
            //get quartz trigger
            List<Trigger> triggerList = this.getTriggerByKey(jobKey);
            //填充任务信息和调度器信息至任务业务对象中
            jobDetailDO.fillWithQuartzJobDetail.accept(jobDetail);
            jobDetailDO.fillWithQuartzTriggers.accept(triggerList);
            //获取调度器的执行状态
            getTriggerState(jobDetailDO);

        }

        return jobDetailDO;
    }

    /**
     * 封装调度器的执行状态
     *
     * @param jobDetailDO 任务信息
     */
    private void getTriggerState(JobDetailDO jobDetailDO) {
        //获取调度器执行状态
        jobDetailDO.getTriggerDOs().forEach(triggerDO -> {
            TriggerKey triggerKey = new TriggerKey(triggerDO.getName(), triggerDO.getGroup());
            try {
                Trigger.TriggerState triggerState = scheduler.getTriggerState(triggerKey);
                triggerDO.setTriggerState(triggerState);
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public boolean add(JobDetailDO jobDetailDO) {
        //JobDO --> QuartzJobDetail
        JobDetail jobDetail = jobDetailDO.getJobDO().convert2QuartzJobDetail();
        //TriggerDOSet --> QuartzTriggerSet
        Set<CronTrigger> cronTriggers = jobDetailDO.getTriggerDOs().stream().map(
                triggerDO -> triggerDO.convert2QuartzTrigger(jobDetail)
        ).collect(Collectors.toSet());

        //项quartz中添加任务，逻辑：若任务已存在，替换
        try {
            scheduler.scheduleJob(jobDetail, cronTriggers, true);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        log.info("添加任务失败失败");
        return false;
    }

    @Override
    public boolean remove(List<JobKey> jobKeyList) {
        try {
            return scheduler.deleteJobs(jobKeyList);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean disable(GroupMatcher<JobKey> matcher) {
        try {
            scheduler.pauseJobs(matcher);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean disableAll() {
        try {
            scheduler.pauseAll();
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean enable(GroupMatcher<JobKey> matcher) {
        try {
            scheduler.resumeJobs(matcher);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean enableAll() {
        try {
            scheduler.resumeAll();
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean triggerNow(JobKey jobKey, JobDataMap jobDataMap) {
        try {
            scheduler.triggerJob(jobKey, jobDataMap);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public JobDetail getJobDetailByKey(JobKey jobKey) {
        JobDetail jobDetail = null;
        try {
            jobDetail = scheduler.getJobDetail(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return jobDetail;
    }

    @Override
    public List<Trigger> getTriggerByKey(JobKey jobKey) {
        List<Trigger> triggers = new ArrayList<>();
        try {
            triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return triggers;
    }

    @Override
    public boolean pauseTrigger(TriggerKey triggerKey) {
        try {
            scheduler.pauseTrigger(triggerKey);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean resumeTrigger(TriggerKey triggerKey) {
        try {
            scheduler.resumeTrigger(triggerKey);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean unScheduleJob(TriggerKey triggerKey) {
        try {
            return scheduler.unscheduleJob(triggerKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return false;
    }

}

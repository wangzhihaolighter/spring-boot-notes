package com.example.springframework.boot.dynamic.quartz.scheduler.service;

import com.example.springframework.boot.dynamic.quartz.scheduler.domain.JobDetailDO;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;

import java.util.List;

public interface JobService {

    /**
     * 查询任务列表
     *
     * @return 任务列表
     */
    List<JobDetailDO> queryJobList();

    /**
     * 根据任务key查询任务
     *
     * @param jobKey 任务key
     * @return 任务信息
     */
    JobDetailDO queryByJobKey(JobKey jobKey);

    /**
     * 添加任务，若任务已存在，替换
     *
     * @param jobDetailDO 任务信息
     * @return 是否成功
     */
    boolean add(JobDetailDO jobDetailDO);

    /**
     * 删除任务
     *
     * @param jobKeyList 任务key列表
     * @return 是否成功
     */
    boolean remove(List<JobKey> jobKeyList);

    /**
     * 停用任务
     *
     * @param matcher 任务组
     * @return 是否成功
     */
    boolean disable(GroupMatcher<JobKey> matcher);

    /**
     * 停用所有任务
     *
     * @return 是否成功
     */
    boolean disableAll();

    /**
     * 启用任务
     *
     * @param matcher 任务组
     * @return 是否成功
     */
    boolean enable(GroupMatcher<JobKey> matcher);

    /**
     * 启用所有任务
     *
     * @return 是否成功
     */
    boolean enableAll();

    /**
     * 立即触发任务
     *
     * @param jobKey     任务key
     * @param jobDataMap 任务执行信息
     * @return 是否成功
     */
    boolean triggerNow(JobKey jobKey, JobDataMap jobDataMap);

    /**
     * 根据jobKey获取JobDetail
     *
     * @param jobKey 任务key
     * @return quartz任务信息
     */
    JobDetail getJobDetailByKey(JobKey jobKey);

    /**
     * 根据jobKey获取jobTrigger列表
     *
     * @param jobKey 任务key
     * @return quartz触发器列表
     */
    List<Trigger> getTriggerByKey(JobKey jobKey);

    /**
     * 暂停触发器
     *
     * @param triggerKey 触发器key
     * @return 是否成功
     */
    boolean pauseTrigger(TriggerKey triggerKey);

    /**
     * 启用触发器
     *
     * @param triggerKey 触发器key
     * @return 是否成功
     */
    boolean resumeTrigger(TriggerKey triggerKey);

    /**
     * 移除触发器
     * 注意：如果相关工作没有任何其他触发器，并且工作是不耐用，那么这份工作也将被删除。
     *
     * @param triggerKey 触发器key
     * @return 是否成功
     */
    boolean unScheduleJob(TriggerKey triggerKey);

}

package com.example.springframework.boot.dynamic.quartz.scheduler.web;

import com.example.springframework.boot.dynamic.quartz.scheduler.domain.JobDetailDO;
import com.example.springframework.boot.dynamic.quartz.scheduler.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.TriggerKey;
import org.quartz.core.jmx.JobDataMapSupport;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class SimpleController {

    @Autowired
    private JobService jobService;

    @GetMapping("/")
    public String home() {
        return "hello dynamic quartz schedule";
    }

    @GetMapping("/something/get")
    public void getSomething() {
        log.info(" ----- get something -----");
    }

    @PostMapping("/something/do")
    public void doSomething() {
        log.info(" ----- do something -----");
    }

    /* job read */

    /**
     * 获取所有任务
     *
     * @return 任务列表
     */
    @GetMapping("/job/query/all")
    public List<JobDetailDO> queryJobDetailDOAll() {
        return jobService.queryJobList();
    }

    /**
     * 查询指定jobKey任务信息
     *
     * @param name  任务名称
     * @param group 任务组名
     * @return 任务信息
     */
    @GetMapping("/job/query/{group}/{name}")
    public JobDetailDO queryByJobKey(@PathVariable String name,
                                     @PathVariable String group) {
        JobKey jobKey = new JobKey(name, group);
        return jobService.queryByJobKey(jobKey);
    }

    /* job write */

    /**
     * 添加任务job，若任务已存在，替换
     * 注意，一个触发器只能执行一个任务，若触发器被指派给其他任务，它之前的任务则无触发器，无法继续执行
     *
     * @param jobDetailDO 任务信息
     * @return 是否成功
     */
    @PostMapping("/job/add")
    public boolean createOrReplaceJob(@RequestBody JobDetailDO jobDetailDO) {
        return jobService.add(jobDetailDO);
    }

    /**
     * 批量删除任务job
     *
     * @param jobKeyGroups key：group[任务组名] - value：name[任务名称list]
     * @return 是否成功
     */
    @DeleteMapping("/job/remove")
    public boolean remove(@RequestBody Map<String, List<String>> jobKeyGroups) {
        List<JobKey> jobKeyList = new ArrayList<>();
        jobKeyGroups.forEach((k, v) ->
                v.forEach(name -> {
                    JobKey jobKey = new JobKey(name, k);
                    jobKeyList.add(jobKey);
                })
        );
        return jobService.remove(jobKeyList);
    }

    /**
     * 立即触发任务
     * 注意：任务必须存在，任务会立即执行一次，没有触发器的任务会消失
     *
     * @param group   任务组名
     * @param name    任务名
     * @param jobData 额外数据
     * @return 是否成功
     */
    @PutMapping("/job/trigger/now/{group}/{name}")
    public boolean triggerNow(@PathVariable String group,
                              @PathVariable String name,
                              @RequestBody Map<String, Object> jobData) {
        JobKey jobKey = new JobKey(name, group);
        JobDataMap jobDataMap = JobDataMapSupport.newJobDataMap(jobData);
        return jobService.triggerNow(jobKey, jobDataMap);
    }

    /**
     * 暂停任务
     *
     * @param group 任务组名
     * @return 是否成功
     */
    @PutMapping("/job/disable/{group}")
    public boolean disable(@PathVariable("group") String group) {
        GroupMatcher<JobKey> matcher = GroupMatcher.jobGroupEquals(group);
        return jobService.disable(matcher);
    }

    /**
     * 暂停所有任务
     *
     * @return 是否成功
     */
    @PutMapping("/job/disable/all")
    public boolean disableAll() {
        return jobService.disableAll();
    }

    /**
     * 启用任务
     *
     * @param group 任务组名
     * @return 是否成功
     */
    @PutMapping("/job/enable/{group}")
    public boolean enable(@PathVariable("group") String group) {
        GroupMatcher<JobKey> matcher = GroupMatcher.jobGroupEquals(group);
        return jobService.enable(matcher);
    }

    /**
     * 启用所有任务
     *
     * @return 是否成功
     */
    @PutMapping("/job/enable/all")
    public boolean enableAll() {
        return jobService.enableAll();
    }

    /**
     * 暂停触发器
     *
     * @param triggerName  触发器名
     * @param triggerGroup 触发器组名
     * @return 是否成功
     */
    @PutMapping("/job/trigger/pause/{triggerGroup}/{triggerName}")
    public boolean pauseTrigger(@PathVariable("triggerName") String triggerName,
                                @PathVariable("triggerGroup") String triggerGroup) {
        TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroup);
        return jobService.pauseTrigger(triggerKey);
    }

    /**
     * 启用触发器
     *
     * @param triggerName  触发器名
     * @param triggerGroup 触发器组名
     * @return 是否成功
     */
    @PutMapping("/job/trigger/resume/{triggerGroup}/{triggerName}")
    public boolean resumeTrigger(@PathVariable("triggerName") String triggerName,
                                 @PathVariable("triggerGroup") String triggerGroup) {
        TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroup);
        return jobService.resumeTrigger(triggerKey);
    }

    /**
     * 移除触发器
     * 注意：如果相关工作没有任何其他触发器，并且工作是不耐用，那么这份工作也将被删除。
     *
     * @param triggerName  触发器名
     * @param triggerGroup 触发器组名
     * @return 是否成功
     */
    @PutMapping("/job/unSchedule/{triggerGroup}/{triggerName}")
    public boolean unScheduleJob(@PathVariable("triggerName") String triggerName,
                                 @PathVariable("triggerGroup") String triggerGroup) {
        TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroup);
        return jobService.unScheduleJob(triggerKey);
    }


}

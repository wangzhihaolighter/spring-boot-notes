package com.example.springframework.boot.dynamic.spring.scheduler.config;

import com.example.springframework.boot.dynamic.spring.scheduler.dao.SimpleScheduleRepository;
import com.example.springframework.boot.dynamic.spring.scheduler.entity.SimpleSchedule;
import com.example.springframework.boot.dynamic.spring.scheduler.service.SimpleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.function.Consumer;

@Slf4j
@Component
public class ScheduleHandler {

    /**
     * 任务列表
     */
    private Map<String, DynamicTask> taskList;

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    @Autowired
    private SimpleService simpleService;
    @Autowired
    private SimpleScheduleRepository simpleScheduleRepository;

    /**
     * 初始化任务配置
     */
    public void init() {
        taskList = Collections.synchronizedMap(new HashMap<>(2));
        DynamicTask testTask = new DynamicTask();
        testTask.setTaskRunnable(() -> simpleService.test());
        taskList.put("testTask", testTask);

        DynamicTask doSomethingTask = new DynamicTask();
        doSomethingTask.setTaskRunnable(() -> simpleService.doSomething());
        taskList.put("doSomethingTask", doSomethingTask);
    }

    /**
     * 启动定时任务
     */
    public void start() {
        //查询定时任务配置
        List<SimpleSchedule> simpleScheduleList = simpleScheduleRepository.findAll();
        //启动定时任务
        for (SimpleSchedule simpleSchedule : simpleScheduleList) {
            String taskName = simpleSchedule.getTaskName();
            DynamicTask dynamicTask = taskList.get(taskName);
            if (dynamicTask == null) {
                continue;
            }
            CronTrigger cronTrigger = new CronTrigger(simpleSchedule.getTaskCorn());
            ScheduledFuture<?> scheduledFuture = threadPoolTaskScheduler.schedule(dynamicTask.getTaskRunnable(), cronTrigger);
            dynamicTask.setFuture(scheduledFuture);
            simpleScheduleRepository.dealtScheduleByTaskName(taskName);
        }
    }

    /**
     * 刷新定时任务配置
     */
    public void refresh() {
        log.info("----- handler schedule refresh -----");
        List<SimpleSchedule> simpleScheduleList = simpleScheduleRepository.findAll();
        Iterator<Map.Entry<String, DynamicTask>> iterator = taskList.entrySet().iterator();
        while (iterator.hasNext()) {
            boolean existFlag = false;
            //判断任务是否在配置中，不存在，任务取消并移除
            Map.Entry<String, DynamicTask> taskEntry = iterator.next();
            for (SimpleSchedule simpleSchedule : simpleScheduleList) {
                String taskName = simpleSchedule.getTaskName();
                String taskCorn = simpleSchedule.getTaskCorn();
                boolean enable = simpleSchedule.isEnable();
                boolean dealt = simpleSchedule.isDealt();
                String key = taskEntry.getKey();
                DynamicTask task = taskEntry.getValue();
                Future<?> taskFuture = task.getFuture();
                if (!key.equals(taskName)) {
                    continue;
                }
                existFlag = true;
                //函数：取消任务
                Consumer<Future> cancelFuture = future -> {
                    if (null != future && !future.isCancelled()) {
                        future.cancel(true);
                    }
                };
                //未启用 - 取消任务、跳出循环
                if (!enable) {
                    cancelFuture.accept(taskFuture);
                    continue;
                }
                //判断是否 - 已处理：不配置任务跳出循环；未处理，取消任务，重新配置任务
                if (!dealt) {
                    cancelFuture.accept(taskFuture);
                    //重新配置任务
                    CronTrigger cronTrigger = new CronTrigger(taskCorn);
                    ScheduledFuture<?> scheduledFuture = threadPoolTaskScheduler.schedule(task.getTaskRunnable(), cronTrigger);
                    task.setFuture(scheduledFuture);
                    simpleScheduleRepository.dealtScheduleByTaskName(taskName);
                }
                break;
            }

            //移除未配置任务
            if (!existFlag) {
                iterator.remove();
            }
        }
    }
}

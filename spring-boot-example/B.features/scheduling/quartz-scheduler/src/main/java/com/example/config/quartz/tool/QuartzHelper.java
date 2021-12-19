package com.example.config.quartz.tool;

import java.util.Date;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.stereotype.Component;

@Component
public class QuartzHelper {

  private final Scheduler scheduler;

  public QuartzHelper(Scheduler scheduler) {
    this.scheduler = scheduler;
  }

  /**
   * 添加任务 - SimpleScheduler
   *
   * @param jobClass jobClass
   * @param taskName jobName、triggerName使用同一个name
   * @param intervalTime 间隔时间, 单位：秒
   */
  public void addSingleJob(Class<? extends Job> jobClass, String taskName, int intervalTime)
      throws SchedulerException {
    JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(taskName).build();
    SimpleScheduleBuilder simpleScheduler =
        SimpleScheduleBuilder.simpleSchedule()
            .withMisfireHandlingInstructionNextWithRemainingCount();
    Trigger trigger =
        TriggerBuilder.newTrigger()
            .startAt(new Date(System.currentTimeMillis() + 1000 * 10))
            .withIdentity(taskName)
            .withSchedule(simpleScheduler.withIntervalInSeconds(intervalTime).repeatForever())
            .build();
    scheduler.scheduleJob(jobDetail, trigger);
  }

  /**
   * 添加任务 - cron
   *
   * @param jobClass jobClass
   * @param taskName jobName、triggerName使用同一个name
   * @param cron cron定时任务规则
   */
  public void addCronJob(Class<? extends Job> jobClass, String taskName, String cron)
      throws SchedulerException {
    JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(taskName).build();
    CronScheduleBuilder cronScheduler =
        CronScheduleBuilder.cronSchedule(cron).withMisfireHandlingInstructionDoNothing();
    Trigger trigger =
        TriggerBuilder.newTrigger()
            .startNow()
            .withIdentity(taskName)
            .withSchedule(cronScheduler)
            .build();
    scheduler.scheduleJob(jobDetail, trigger);
  }

  /**
   * 删除任务
   *
   * @param taskName jobName、triggerName使用同一个name
   * @throws SchedulerException /
   */
  public void removeJob(String taskName) throws SchedulerException {
    TriggerKey triggerKey = TriggerKey.triggerKey(taskName);
    if (scheduler.checkExists(triggerKey)) {
      scheduler.pauseTrigger(triggerKey);
      scheduler.unscheduleJob(triggerKey);
    }
    JobKey jobKey = JobKey.jobKey(taskName);
    if (scheduler.checkExists(jobKey)) {
      scheduler.deleteJob(jobKey);
    }
  }

  /**
   * 修改cron规则
   *
   * @param taskName jobName、triggerName使用同一个name
   * @param cron cron表达式
   * @throws SchedulerException /
   */
  public void modifyCron(String taskName, String cron) throws SchedulerException {
    TriggerKey triggerKey = TriggerKey.triggerKey(taskName);
    CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
    String oldTime = trigger.getCronExpression();
    if (!oldTime.equalsIgnoreCase(cron)) {
      CronScheduleBuilder cronBuilder = CronScheduleBuilder.cronSchedule(cron);
      TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
      triggerBuilder.withIdentity(taskName).startNow().withSchedule(cronBuilder);
      trigger = (CronTrigger) triggerBuilder.build();
      scheduler.rescheduleJob(triggerKey, trigger);
    }
  }
}

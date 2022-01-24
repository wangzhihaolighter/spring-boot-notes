package com.example.core.scheduling.tool;

import com.example.core.scheduling.constant.SchedulingConstant;
import com.example.core.scheduling.constant.SchedulingJobTopicEnum;
import com.example.core.scheduling.job.BaseSchedulingJobRunnable;
import com.example.core.scheduling.job.SchedulingJob;
import com.example.core.util.SpringContextUtil;
import com.example.modules.scheduling.dao.SchedulingTaskInfoRepository;
import com.example.modules.scheduling.domain.entity.SchedulingTaskInfo;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.ScheduledFuture;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SchedulingHelper {
  private final ThreadPoolTaskScheduler threadPoolTaskScheduler;
  private final SchedulingTaskInfoRepository schedulingTaskInfoRepository;
  private final RedissonClient redissonClient;

  public SchedulingHelper(
      ThreadPoolTaskScheduler threadPoolTaskScheduler,
      SchedulingTaskInfoRepository schedulingTaskInfoRepository,
      RedissonClient redissonClient) {
    this.threadPoolTaskScheduler = threadPoolTaskScheduler;
    this.schedulingTaskInfoRepository = schedulingTaskInfoRepository;
    this.redissonClient = redissonClient;
  }

  // =============== 调度调度变更 ===============

  /**
   * 新增调度任务
   *
   * @param jobId /
   */
  @SneakyThrows(NoSuchMethodException.class)
  public void addJob(Long jobId) {
    SchedulingTaskInfo schedulingTaskInfo = schedulingTaskInfoRepository.getById(jobId);

    if (!schedulingTaskInfo.isEnable()) {
      return;
    }

    // 获取bean方法，这里省略下校验和异常处理
    Object bean = SpringContextUtil.getBean(schedulingTaskInfo.getBeanName());
    Method method = bean.getClass().getDeclaredMethod(schedulingTaskInfo.getMethodName());
    log.info(
        "Scheduling Task : {}.{}",
        schedulingTaskInfo.getBeanName(),
        schedulingTaskInfo.getMethodName());

    // 新增调度任务
    BaseSchedulingJobRunnable schedulingJobRunnable =
        new BaseSchedulingJobRunnable(schedulingTaskInfo) {
          @SneakyThrows({IllegalAccessException.class, InvocationTargetException.class})
          @Override
          protected void execute() {
            method.invoke(bean);
          }
        };
    CronTrigger trigger = new CronTrigger(schedulingTaskInfo.getTaskCron());
    ScheduledFuture<?> future = threadPoolTaskScheduler.schedule(schedulingJobRunnable, trigger);
    SchedulingJob schedulingJob = new SchedulingJob(schedulingJobRunnable, trigger, future);

    // 记录调度任务，通过消息提示方式，完成分布式的消息新增与删除
    SchedulingConstant.SCHEDULING_JOB_MAP.put(schedulingTaskInfo.getId(), schedulingJob);
  }

  /**
   * 更新任务调度
   *
   * @param jobId /
   */
  public void updateJob(Long jobId) {
    removeJob(jobId);
    addJob(jobId);
  }

  /**
   * 删除调度任务
   *
   * @param jobId /
   */
  public void removeJob(Long jobId) {
    SchedulingJob schedulingJob = SchedulingConstant.SCHEDULING_JOB_MAP.get(jobId);
    if (Objects.nonNull(schedulingJob)) {
      // 取消调度任务（不中断正在执行的调度任务）
      ScheduledFuture<?> future = schedulingJob.getFuture();
      if (Objects.nonNull(future) && !future.isCancelled()) {
        future.cancel(false);
      }
      // 调度任务记录中清除
      SchedulingConstant.SCHEDULING_JOB_MAP.remove(jobId);
    }
  }

  /**
   * 执行一次指定的调度任务
   *
   * @param jobId /
   */
  @SneakyThrows({
    NoSuchMethodException.class,
    IllegalAccessException.class,
    InvocationTargetException.class
  })
  public void runJob(Long jobId) {
    SchedulingTaskInfo schedulingTaskInfo = schedulingTaskInfoRepository.getById(jobId);

    Object bean = SpringContextUtil.getBean(schedulingTaskInfo.getBeanName());
    Method method = bean.getClass().getDeclaredMethod(schedulingTaskInfo.getMethodName());
    method.invoke(bean);

    log.info(
        "Scheduling Task Run Once: {}.{}",
        schedulingTaskInfo.getBeanName(),
        schedulingTaskInfo.getMethodName());
  }

  // =============== 消息队列 ===============

  public void publishAddJob(Long jobId) {
    RTopic topic = redissonClient.getTopic(SchedulingJobTopicEnum.TOPIC_ADD.getTopic());
    topic.publish(jobId);
  }

  public void publishUpdateJob(Long jobId) {
    RTopic topic = redissonClient.getTopic(SchedulingJobTopicEnum.TOPIC_UPDATE.getTopic());
    topic.publish(jobId);
  }

  public void publishRemoveJob(Long jobId) {
    RTopic topic = redissonClient.getTopic(SchedulingJobTopicEnum.TOPIC_REMOVE.getTopic());
    topic.publish(jobId);
  }
}

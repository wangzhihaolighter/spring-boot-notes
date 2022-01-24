package com.example.core.scheduling.job;

import com.example.core.util.SpringContextUtil;
import com.example.modules.scheduling.dao.SchedulingTaskLogRepository;
import com.example.modules.scheduling.domain.entity.SchedulingTaskInfo;
import com.example.modules.scheduling.domain.entity.SchedulingTaskLog;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeansException;

/** 调度任务 */
@Slf4j
public abstract class BaseSchedulingJobRunnable implements Runnable {

  /** 线程本地变量 */
  private static final ThreadLocal<LocalDateTime> THREAD_LOCAL = new ThreadLocal<>();

  /** 调度任务信息 */
  private final SchedulingTaskInfo schedulingTaskInfo;

  /** 分布式锁 */
  private RLock lock;

  protected BaseSchedulingJobRunnable(SchedulingTaskInfo schedulingTaskInfo) {
    this.schedulingTaskInfo = schedulingTaskInfo;
  }

  /** 执行前 */
  protected void before() {
    log.info("execute before");

    // 记录开始时间
    THREAD_LOCAL.set(LocalDateTime.now());

    // 获取锁
    lock();
  }

  /** 执行 */
  protected abstract void execute();

  /** 执行后 */
  protected void after(Exception e) {
    try {
      LocalDateTime start = THREAD_LOCAL.get();
      LocalDateTime end = LocalDateTime.now();
      log.info("耗时：{} 毫秒", Duration.between(start, end).toMillis());

      // 记录调度任务执行日志
      SchedulingTaskLogRepository schedulingTaskLogRepository =
          SpringContextUtil.getBean(SchedulingTaskLogRepository.class);
      SchedulingTaskLog schedulingTaskLog = new SchedulingTaskLog();
      schedulingTaskLog.setTaskId(schedulingTaskInfo.getId());
      schedulingTaskLog.setStartTime(start);
      schedulingTaskLog.setEndTime(end);
      if (Objects.isNull(e)) {
        log.info("execute after");
        schedulingTaskLog.setSuccess(true);
      } else {
        log.error("execute error", e);
        schedulingTaskLog.setSuccess(false);
        String message = e.getMessage();
        int maxMessageLength = 200;
        if (Objects.nonNull(message) && message.length() > maxMessageLength) {
          message = message.substring(0, maxMessageLength);
        }
        schedulingTaskLog.setErrorMsg(message);
      }
      schedulingTaskLogRepository.save(schedulingTaskLog);
    } catch (BeansException ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public void run() {
    try {
      // 前置处理
      before();

      // 执行调度任务
      execute();

      // 后置处理
      after(null);
    } catch (Exception e) {
      // 异常后置处理
      after(e);
    } finally {
      try {
        // 释放资源
        THREAD_LOCAL.remove();

        // 释放锁
        unlock();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private void lock() {
    // 判断并发执行机制
    if (!schedulingTaskInfo.isAllowConcurrency()) {
      RedissonClient redissonClient = SpringContextUtil.getBean(RedissonClient.class);
      lock = redissonClient.getLock("scheduling:" + schedulingTaskInfo.getTaskName());
      if (!lock.tryLock()) {
        throw new RuntimeException("调度任务禁止并发执行");
      }
    }
  }

  private void unlock() {
    if (Objects.nonNull(lock) && lock.isLocked() && lock.isHeldByCurrentThread()) {
      lock.unlock();
    }
  }
}

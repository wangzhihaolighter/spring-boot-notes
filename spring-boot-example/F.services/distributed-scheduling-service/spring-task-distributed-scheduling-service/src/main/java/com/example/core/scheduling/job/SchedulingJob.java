package com.example.core.scheduling.job;

import java.util.concurrent.ScheduledFuture;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.scheduling.support.CronTrigger;

@Getter
@AllArgsConstructor
public class SchedulingJob {
  /** 调度任务 */
  private BaseSchedulingJobRunnable runnable;

  /** 触发器 */
  private CronTrigger trigger;

  /** 异步调度结果 */
  private ScheduledFuture<?> future;
}

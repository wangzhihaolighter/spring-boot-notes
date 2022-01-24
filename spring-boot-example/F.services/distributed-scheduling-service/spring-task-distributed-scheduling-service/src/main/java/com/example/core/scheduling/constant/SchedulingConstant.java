package com.example.core.scheduling.constant;

import com.example.core.scheduling.job.SchedulingJob;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 调度任务常量
 *
 * @author wangzhihao
 */
public class SchedulingConstant {
  private SchedulingConstant() {}

  /** 系统调度任务Map，key：任务id，value：调度任务实例 */
  public static final Map<Long, SchedulingJob> SCHEDULING_JOB_MAP = new ConcurrentHashMap<>();
}

package com.example.modules.scheduling.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "t_scheduling_task_info")
public class SchedulingTaskInfo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** 任务名称 */
  private String taskName;

  /** 任务说明 */
  private String taskMemo;

  /** 任务cron表达式 */
  private String taskCron;

  /** bean name */
  private String beanName;

  /** bean method name */
  private String methodName;

  /** 是否启用 */
  private boolean enable;

  /** 是否允许并发 */
  private boolean allowConcurrency;
}

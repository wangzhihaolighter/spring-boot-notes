package com.example.core.scheduling.constant;

/**
 * 调度任务消息队列主题枚举类
 *
 * @author wangzhihao
 */
public enum SchedulingJobTopicEnum {
  /** 新增调度任务 */
  TOPIC_ADD("addJobTopic"),
  /** 更新调度任务 */
  TOPIC_UPDATE("updateJobTopic"),
  /** 移除调度任务 */
  TOPIC_REMOVE("removeJobTopic"),
  ;

  private final String topic;

  SchedulingJobTopicEnum(String topic) {
    this.topic = topic;
  }

  public String getTopic() {
    return topic;
  }
}

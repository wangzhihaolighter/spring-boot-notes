package com.example.core.scheduling.runner;

import com.example.core.scheduling.constant.SchedulingJobTopicEnum;
import com.example.core.scheduling.tool.SchedulingHelper;
import com.example.core.scheduling.topic.AddJobMessageListener;
import com.example.core.scheduling.topic.RemoveJobMessageListener;
import com.example.core.scheduling.topic.UpdateJobMessageListener;
import com.example.modules.scheduling.dao.SchedulingTaskInfoRepository;
import com.example.modules.scheduling.domain.entity.SchedulingTaskInfo;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/** 服务启动时，注入调度任务 */
@Slf4j
@Component
public class SchedulingRunner implements ApplicationRunner {

  private final SchedulingTaskInfoRepository schedulingTaskInfoRepository;
  private final SchedulingHelper schedulingHelper;
  private final AddJobMessageListener addJobMessageListener;
  private final UpdateJobMessageListener updateJobMessageListener;
  private final RemoveJobMessageListener removeJobMessageListener;
  private final RedissonClient redissonClient;

  public SchedulingRunner(
      SchedulingTaskInfoRepository schedulingTaskInfoRepository,
      SchedulingHelper schedulingHelper,
      AddJobMessageListener addJobMessageListener,
      UpdateJobMessageListener updateJobMessageListener,
      RemoveJobMessageListener removeJobMessageListener,
      RedissonClient redissonClient) {
    this.schedulingTaskInfoRepository = schedulingTaskInfoRepository;
    this.schedulingHelper = schedulingHelper;
    this.addJobMessageListener = addJobMessageListener;
    this.updateJobMessageListener = updateJobMessageListener;
    this.removeJobMessageListener = removeJobMessageListener;
    this.redissonClient = redissonClient;
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    // 注册调度任务
    schedulingTaskInfoRepository.findAll().stream()
        // 过滤未启用的调度任务
        .filter(SchedulingTaskInfo::isEnable)
        // 将调度任务添加到调度器中
        .forEach(info -> schedulingHelper.addJob(info.getId()));

    // 注册调度任务消息队列主题监听
    // 新增调度任务
    RTopic addJobTopic = redissonClient.getTopic(SchedulingJobTopicEnum.TOPIC_ADD.getTopic());
    addJobTopic.addListener(Long.class, addJobMessageListener);
    // 更新调度任务
    RTopic updateJobTopic = redissonClient.getTopic(SchedulingJobTopicEnum.TOPIC_UPDATE.getTopic());
    updateJobTopic.addListener(Long.class, updateJobMessageListener);
    // 删除调度任务
    RTopic removeJobTopic = redissonClient.getTopic(SchedulingJobTopicEnum.TOPIC_REMOVE.getTopic());
    removeJobTopic.addListener(Long.class, removeJobMessageListener);
  }
}

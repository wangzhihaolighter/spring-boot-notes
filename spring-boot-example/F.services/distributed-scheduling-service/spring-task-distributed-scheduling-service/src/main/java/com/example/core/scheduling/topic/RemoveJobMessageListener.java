package com.example.core.scheduling.topic;

import com.example.core.scheduling.tool.SchedulingHelper;
import com.example.modules.scheduling.dao.SchedulingTaskInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.listener.MessageListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoveJobMessageListener implements MessageListener<Long> {
  private final SchedulingTaskInfoRepository schedulingTaskInfoRepository;
  private final SchedulingHelper schedulingHelper;

  public RemoveJobMessageListener(
      SchedulingTaskInfoRepository schedulingTaskInfoRepository,
      SchedulingHelper schedulingHelper) {
    this.schedulingTaskInfoRepository = schedulingTaskInfoRepository;
    this.schedulingHelper = schedulingHelper;
  }

  @Override
  public void onMessage(CharSequence channel, Long jobId) {
    log.info("接收消息：删除调度任务，jobId {}", jobId);

    schedulingHelper.removeJob(jobId);
  }
}

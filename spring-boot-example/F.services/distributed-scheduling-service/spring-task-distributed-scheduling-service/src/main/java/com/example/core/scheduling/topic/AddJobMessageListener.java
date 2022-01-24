package com.example.core.scheduling.topic;

import com.example.core.scheduling.tool.SchedulingHelper;
import com.example.modules.scheduling.dao.SchedulingTaskInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.listener.MessageListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AddJobMessageListener implements MessageListener<Long> {
  private final SchedulingTaskInfoRepository schedulingTaskInfoRepository;
  private final SchedulingHelper schedulingHelper;

  public AddJobMessageListener(
      SchedulingTaskInfoRepository schedulingTaskInfoRepository,
      SchedulingHelper schedulingHelper) {
    this.schedulingTaskInfoRepository = schedulingTaskInfoRepository;
    this.schedulingHelper = schedulingHelper;
  }

  @Override
  public void onMessage(CharSequence channel, Long jobId) {
    log.info("接收消息：新增调度任务，jobId {}", jobId);

    schedulingHelper.addJob(jobId);
  }
}

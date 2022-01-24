package com.example.modules.scheduling.service.impl;

import com.example.core.scheduling.tool.SchedulingHelper;
import com.example.modules.scheduling.dao.SchedulingTaskInfoRepository;
import com.example.modules.scheduling.domain.dto.PageQueryDTO;
import com.example.modules.scheduling.domain.entity.SchedulingTaskInfo;
import com.example.modules.scheduling.service.SchedulingTaskInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class SchedulingTaskInfoServiceImpl implements SchedulingTaskInfoService {
  private final SchedulingTaskInfoRepository schedulingTaskInfoRepository;
  private final SchedulingHelper schedulingHelper;

  public SchedulingTaskInfoServiceImpl(
      SchedulingTaskInfoRepository schedulingTaskInfoRepository,
      SchedulingHelper schedulingHelper) {
    this.schedulingTaskInfoRepository = schedulingTaskInfoRepository;
    this.schedulingHelper = schedulingHelper;
  }

  @Override
  public Page<SchedulingTaskInfo> getAll(PageQueryDTO dto) {
    if (dto == null || dto.getPageNum() == null || dto.getPageSize() == null) {
      dto = new PageQueryDTO();
      dto.setPageNum(1);
      dto.setPageSize(10);
    }
    return schedulingTaskInfoRepository.findAll(
        PageRequest.of(dto.getPageNum() - 1, dto.getPageSize()));
  }

  @Override
  public SchedulingTaskInfo getById(Long id) {
    return schedulingTaskInfoRepository.getById(id);
  }

  @Override
  public Long add(SchedulingTaskInfo schedulingTaskInfo) {
    // 新增
    SchedulingTaskInfo save = schedulingTaskInfoRepository.saveAndFlush(schedulingTaskInfo);

    // 消息通知
    schedulingHelper.publishAddJob(save.getId());

    return save.getId();
  }

  @Override
  public Integer update(SchedulingTaskInfo schedulingTaskInfo) {
    // 更新
    schedulingTaskInfoRepository.saveAndFlush(schedulingTaskInfo);

    // 消息通知
    schedulingHelper.publishUpdateJob(schedulingTaskInfo.getId());
    return 1;
  }

  @Override
  public Integer delete(Long id) {
    // 删除
    schedulingTaskInfoRepository.deleteById(id);

    // 消息通知
    schedulingHelper.publishRemoveJob(id);
    return 1;
  }

  @Override
  public Boolean runTask(Long id) {
    schedulingHelper.runJob(id);
    return true;
  }

  @Override
  public Boolean pauseTask(Long id) {
    // 关闭调度任务
    SchedulingTaskInfo schedulingTaskInfo = schedulingTaskInfoRepository.getById(id);
    schedulingTaskInfo.setEnable(false);
    schedulingTaskInfoRepository.saveAndFlush(schedulingTaskInfo);

    // 消息通知
    schedulingHelper.publishRemoveJob(id);
    return true;
  }

  @Override
  public Boolean resumeTask(Long id) {
    // 启用调度任务
    SchedulingTaskInfo schedulingTaskInfo = schedulingTaskInfoRepository.getById(id);
    schedulingTaskInfo.setEnable(true);
    schedulingTaskInfoRepository.saveAndFlush(schedulingTaskInfo);

    // 消息通知
    schedulingHelper.publishAddJob(id);
    return true;
  }
}

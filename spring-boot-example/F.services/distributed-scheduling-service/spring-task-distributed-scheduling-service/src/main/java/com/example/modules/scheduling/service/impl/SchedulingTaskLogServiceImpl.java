package com.example.modules.scheduling.service.impl;

import com.example.modules.scheduling.dao.SchedulingTaskLogRepository;
import com.example.modules.scheduling.domain.entity.SchedulingTaskLog;
import com.example.modules.scheduling.service.SchedulingTaskLogService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SchedulingTaskLogServiceImpl implements SchedulingTaskLogService {
  private final SchedulingTaskLogRepository schedulingTaskLogRepository;

  public SchedulingTaskLogServiceImpl(SchedulingTaskLogRepository schedulingTaskLogRepository) {
    this.schedulingTaskLogRepository = schedulingTaskLogRepository;
  }

  @Override
  public List<SchedulingTaskLog> getAll() {
    return schedulingTaskLogRepository.findAll();
  }

  @Override
  public SchedulingTaskLog getById(Long id) {
    return schedulingTaskLogRepository.getById(id);
  }
}

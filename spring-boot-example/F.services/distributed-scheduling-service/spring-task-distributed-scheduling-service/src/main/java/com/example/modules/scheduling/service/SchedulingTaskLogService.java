package com.example.modules.scheduling.service;

import com.example.modules.scheduling.domain.entity.SchedulingTaskLog;
import java.util.List;

public interface SchedulingTaskLogService {

  /**
   * 分页查询
   *
   * @return /
   */
  List<SchedulingTaskLog> getAll();

  /**
   * 根据id查询
   *
   * @param id /
   * @return /
   */
  SchedulingTaskLog getById(Long id);
}

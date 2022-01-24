package com.example.modules.scheduling.service;

import com.example.modules.scheduling.domain.dto.PageQueryDTO;
import com.example.modules.scheduling.domain.entity.SchedulingTaskInfo;
import org.springframework.data.domain.Page;

public interface SchedulingTaskInfoService {

  /**
   * 分页查询
   *
   * @param dto /
   * @return /
   */
  Page<SchedulingTaskInfo> getAll(PageQueryDTO dto);

  /**
   * 通过id查询
   *
   * @param id /
   * @return /
   */
  SchedulingTaskInfo getById(Long id);

  /**
   * 新增
   *
   * @param schedulingTaskInfo /
   * @return /
   */
  Long add(SchedulingTaskInfo schedulingTaskInfo);

  /**
   * 更新
   *
   * @param schedulingTaskInfo /
   * @return /
   */
  Integer update(SchedulingTaskInfo schedulingTaskInfo);

  /**
   * 删除
   *
   * @param id /
   * @return /
   */
  Integer delete(Long id);

  /**
   * 执行一次指定调度任务
   *
   * @param id /
   * @return /
   */
  Boolean runTask(Long id);

  /**
   * 暂停指定调度任务
   *
   * @param id /
   * @return /
   */
  Boolean pauseTask(Long id);

  /**
   * 恢复指定调度任务
   *
   * @param id /
   * @return /
   */
  Boolean resumeTask(Long id);
}

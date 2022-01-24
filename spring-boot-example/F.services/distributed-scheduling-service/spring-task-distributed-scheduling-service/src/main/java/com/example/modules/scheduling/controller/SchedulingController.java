package com.example.modules.scheduling.controller;

import com.example.modules.scheduling.domain.dto.PageQueryDTO;
import com.example.modules.scheduling.domain.entity.SchedulingTaskInfo;
import com.example.modules.scheduling.domain.entity.SchedulingTaskLog;
import com.example.modules.scheduling.service.SchedulingTaskInfoService;
import com.example.modules.scheduling.service.SchedulingTaskLogService;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scheduling")
public class SchedulingController {

  private final SchedulingTaskInfoService schedulingTaskInfoService;
  private final SchedulingTaskLogService schedulingTaskLogService;

  public SchedulingController(
      SchedulingTaskInfoService schedulingTaskInfoService,
      SchedulingTaskLogService schedulingTaskLogService) {
    this.schedulingTaskInfoService = schedulingTaskInfoService;
    this.schedulingTaskLogService = schedulingTaskLogService;
  }

  /** 查询任务调度列表 */
  @GetMapping
  public ResponseEntity<Page<SchedulingTaskInfo>> getInfoList(PageQueryDTO dto) {
    return ResponseEntity.ok(schedulingTaskInfoService.getAll(dto));
  }

  /** 查询任务调度详情 */
  @GetMapping("/{id}")
  public ResponseEntity<SchedulingTaskInfo> getInfoById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(schedulingTaskInfoService.getById(id));
  }

  /** 新增任务调度 */
  @PostMapping
  public ResponseEntity<Long> addInfo(@RequestBody SchedulingTaskInfo info) {
    return ResponseEntity.ok(schedulingTaskInfoService.add(info));
  }

  /** 更新任务调度 */
  @PutMapping
  public ResponseEntity<Integer> updateInfo(@RequestBody SchedulingTaskInfo info) {
    return ResponseEntity.ok(schedulingTaskInfoService.update(info));
  }
  /** 删除任务调度 */
  @DeleteMapping("/{id}")
  public ResponseEntity<Integer> deleteInfoById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(schedulingTaskInfoService.delete(id));
  }
  /** 查询调度任务执行日志列表 */
  @GetMapping("/log")
  public ResponseEntity<List<SchedulingTaskLog>> getLogList() {
    return ResponseEntity.ok(schedulingTaskLogService.getAll());
  }

  /** 查询调度任务执行日志详情 */
  @GetMapping("/log/{id}")
  public ResponseEntity<SchedulingTaskLog> getLogById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(schedulingTaskLogService.getById(id));
  }

  /** 执行一次指定调度任务 */
  @PostMapping("/task/run/{id}")
  public ResponseEntity<Boolean> runTask(@PathVariable("id") Long id) {
    return ResponseEntity.ok(schedulingTaskInfoService.runTask(id));
  }

  /** 暂停指定调度任务 */
  @PostMapping("/task/pause/{id}")
  public ResponseEntity<Boolean> pauseTask(@PathVariable("id") Long id) {
    return ResponseEntity.ok(schedulingTaskInfoService.pauseTask(id));
  }

  /** 恢复指定调度任务 */
  @PostMapping("/task/resume/{id}")
  public ResponseEntity<Boolean> resumeTask(@PathVariable("id") Long id) {
    return ResponseEntity.ok(schedulingTaskInfoService.resumeTask(id));
  }
}

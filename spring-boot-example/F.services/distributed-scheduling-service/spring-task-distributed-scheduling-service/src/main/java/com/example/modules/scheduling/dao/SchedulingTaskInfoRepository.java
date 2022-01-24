package com.example.modules.scheduling.dao;

import com.example.modules.scheduling.domain.entity.SchedulingTaskInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchedulingTaskInfoRepository extends JpaRepository<SchedulingTaskInfo, Long> {}

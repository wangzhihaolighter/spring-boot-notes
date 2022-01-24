package com.example.modules.scheduling.dao;

import com.example.modules.scheduling.domain.entity.SchedulingTaskLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchedulingTaskLogRepository extends JpaRepository<SchedulingTaskLog, Long> {}

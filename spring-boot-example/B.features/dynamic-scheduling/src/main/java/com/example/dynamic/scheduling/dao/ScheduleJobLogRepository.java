package com.example.dynamic.scheduling.dao;

import com.example.dynamic.scheduling.entity.ScheduleJobLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleJobLogRepository extends JpaRepository<ScheduleJobLog, Long> {
}

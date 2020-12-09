package com.example.dynamic.scheduling.dao;

import com.example.dynamic.scheduling.entity.ScheduleJob;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleJobRepository extends JpaRepository<ScheduleJob, Long> {
}

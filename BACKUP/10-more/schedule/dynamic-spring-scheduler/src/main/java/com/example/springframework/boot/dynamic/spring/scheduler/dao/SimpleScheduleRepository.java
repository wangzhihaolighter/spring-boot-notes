package com.example.springframework.boot.dynamic.spring.scheduler.dao;

import com.example.springframework.boot.dynamic.spring.scheduler.entity.SimpleSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SimpleScheduleRepository extends JpaRepository<SimpleSchedule, Long> {

    /**
     * 根据任务名更新任务为已处理
     *
     * @param taskName 任务名
     * @return 更新数据条数
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "update SimpleSchedule ss set ss.dealt=true where ss.taskName = :taskName")
    int dealtScheduleByTaskName(@Param(value = "taskName") String taskName);

}

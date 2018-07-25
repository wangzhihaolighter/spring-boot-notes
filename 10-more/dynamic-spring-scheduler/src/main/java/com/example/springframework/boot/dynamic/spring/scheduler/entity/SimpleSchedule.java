package com.example.springframework.boot.dynamic.spring.scheduler.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "simple_schedule")
public class SimpleSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "task_name")
    private String taskName;
    @Column(name = "task_memo")
    private String taskMemo;
    @Column(name = "task_cron")
    private String taskCorn;
    @Column(name = "is_enable")
    private boolean enable;
    @Column(name = "is_dealt")
    private boolean dealt;
    @Column(name = "last_update_on")
    private Date lastUpdateOn;
}

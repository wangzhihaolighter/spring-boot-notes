package com.example.springframework.boot.dynamic.spring.scheduler.web;

import com.example.springframework.boot.dynamic.spring.scheduler.config.ScheduleHandler;
import com.example.springframework.boot.dynamic.spring.scheduler.dao.SimpleScheduleRepository;
import com.example.springframework.boot.dynamic.spring.scheduler.entity.SimpleSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Consumer;

@RestController
public class SimpleController {

    @Autowired
    private ScheduleHandler scheduleHandler;
    @Autowired
    private SimpleScheduleRepository simpleScheduleRepository;

    @GetMapping("/")
    public String home() {
        return "hello dynamic spring scheduler";
    }

    @GetMapping("/find/all")
    public List<SimpleSchedule> findAll() {
        return simpleScheduleRepository.findAll();
    }

    @PutMapping("/update/corn")
    public void updateTaskCorn(@RequestParam("id") Long id, @RequestParam("corn") String corn) {
        Consumer<SimpleSchedule> updateTaskCorn = simpleSchedule -> {
            simpleSchedule.setId(id);
            simpleSchedule.setTaskCorn(corn);
            simpleSchedule.setDealt(false);
            simpleScheduleRepository.save(simpleSchedule);
        };
        simpleScheduleRepository.findById(id).ifPresent(updateTaskCorn);
    }

    @PostMapping("/refresh")
    public void refresh() {
        scheduleHandler.refresh();
    }
}

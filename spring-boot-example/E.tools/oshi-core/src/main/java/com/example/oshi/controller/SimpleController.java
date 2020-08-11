package com.example.oshi.controller;

import com.example.oshi.service.MonitorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SimpleController {

    private final MonitorService monitorService;

    public SimpleController(MonitorService monitorService) {
        this.monitorService = monitorService;
    }

    @GetMapping("/")
    public Map<String, Object> getServerMonitorInfo() {
        return monitorService.getServerMonitorInfo();
    }
}

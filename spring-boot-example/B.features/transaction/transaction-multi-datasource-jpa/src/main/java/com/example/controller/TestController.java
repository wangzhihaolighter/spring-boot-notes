package com.example.controller;

import com.example.service.TestService;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
  private final TestService testService;

  public TestController(TestService testService) {
    this.testService = testService;
  }

  @GetMapping
  public Map<String, Object> query() {
    return testService.query();
  }

  @PostMapping
  public void save() {
    testService.save();
  }

  @PostMapping("/error")
  public void saveError() {
    testService.saveError();
  }
}

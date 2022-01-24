package com.example.controller;

import com.example.dao.TestRepository;
import com.example.entity.Test;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

  private final TestRepository testRepository;

  public TestController(TestRepository testRepository) {
    this.testRepository = testRepository;
  }

  @GetMapping
  public List<Test> query() {
    return testRepository.findAll();
  }
}

package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

  @GetMapping("/memory")
  public String memory() {
    // 监控观察堆内存变化
    byte[] bytes = new byte[100 * 1024 * 1024];
    System.out.println(bytes.length);
    return "memory";
  }
}

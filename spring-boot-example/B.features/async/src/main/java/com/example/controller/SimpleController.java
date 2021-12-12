package com.example.controller;

import com.example.config.async.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SimpleController {
  private final AsyncService asyncService;

  public SimpleController(AsyncService asyncService) {
    this.asyncService = asyncService;
  }

  @GetMapping("/")
  public String home() {
    return "Hello,World!";
  }

  @GetMapping("/async")
  public String async() {
    log.info("可通过日志输出中的线程名判断是否异步");
    log.info("SimpleController.async, thread: {}", Thread.currentThread().getName());
    asyncService.test();
    return "ok";
  }
}

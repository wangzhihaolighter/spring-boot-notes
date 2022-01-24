package com.example.dubbo.consumer.controller;

import com.example.dubbo.common.DemoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {
  @DubboReference(version = "1.0.0")
  private DemoService demoService;

  @GetMapping("/")
  public String hello() {
    return demoService.sayHello("dubbo");
  }
}

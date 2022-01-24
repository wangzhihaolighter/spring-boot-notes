package com.example.controller;

import com.example.service.AsyncService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
public class SimpleController {
  private final AsyncService asyncService;

  @GetMapping("/")
  public String hello() {
    return "Hello,World!";
  }

  @GetMapping("/async")
  public String async() {
    log.info("controller");
    asyncService.test();
    return "ok";
  }
}

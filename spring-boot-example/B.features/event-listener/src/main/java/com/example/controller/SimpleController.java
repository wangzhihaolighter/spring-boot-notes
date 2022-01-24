package com.example.controller;

import com.example.event.TestDTO;
import com.example.event.TestEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class SimpleController {

  private final ApplicationEventPublisher applicationEventPublisher;

  @GetMapping("/")
  public String home() {
    return "Hello,World!";
  }

  @GetMapping("/event")
  public String hello(String msg) {
    final TestDTO testDTO = new TestDTO();
    testDTO.setMsg(msg);
    applicationEventPublisher.publishEvent(new TestEvent(testDTO));
    return msg;
  }
}

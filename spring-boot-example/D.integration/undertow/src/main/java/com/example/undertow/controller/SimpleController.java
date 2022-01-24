package com.example.undertow.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

  @GetMapping("/")
  public String hello() {
    return "Hello,World!";
  }
}

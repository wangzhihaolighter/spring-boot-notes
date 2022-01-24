package com.example.modules.scheduling.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class IndexController {

  @GetMapping("/")
  public ModelAndView index() {
    return new ModelAndView("index");
  }
}

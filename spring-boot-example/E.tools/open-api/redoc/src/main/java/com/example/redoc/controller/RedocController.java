package com.example.redoc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RedocController {

  @GetMapping
  public ModelAndView redoc() {
    return new ModelAndView("redoc");
  }
}

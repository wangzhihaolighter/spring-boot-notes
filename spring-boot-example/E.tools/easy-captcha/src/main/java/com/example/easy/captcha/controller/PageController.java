package com.example.easy.captcha.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageController {

  @GetMapping("/")
  public ModelAndView index(@RequestParam(value = "token", required = false) String token) {
    String viewName = "index";
    if (token == null) {
      viewName = "login";
    }
    return new ModelAndView(viewName);
  }

  @GetMapping("/login")
  public ModelAndView login() {
    return new ModelAndView("login");
  }

  @GetMapping("/error")
  public ModelAndView error() {
    return new ModelAndView("error");
  }
}

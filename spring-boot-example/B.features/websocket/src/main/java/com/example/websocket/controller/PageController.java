package com.example.websocket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageController {

  @GetMapping("/")
  public String home() {
    return "index";
  }

  @GetMapping("/chat")
  public ModelAndView chat(
      @RequestParam("room") String room, @RequestParam("username") String username) {
    ModelAndView modelAndView = new ModelAndView("chat");
    modelAndView.addObject("room", room);
    modelAndView.addObject("username", username);
    return modelAndView;
  }
}

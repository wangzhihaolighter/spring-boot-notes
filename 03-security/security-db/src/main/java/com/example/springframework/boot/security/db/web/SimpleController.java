package com.example.springframework.boot.security.db.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class SimpleController {

    @GetMapping("/")
    public String home() {
        return "hello spring security db ^_^";
    }

    @GetMapping("/welcome")
    public ModelAndView welcome() {
        return new ModelAndView("welcome");
    }

    @GetMapping("/system/manage")
    public ModelAndView manage() {
        return new ModelAndView("manage");
    }
}

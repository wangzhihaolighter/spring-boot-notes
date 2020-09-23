package com.example.springframework.boot.security.db.json.web;

import com.example.springframework.boot.security.db.json.config.response.DemoResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class SimpleController {

    @GetMapping("/")
    public DemoResult home() {
        return DemoResult.success("hello spring security db json");
    }

    @GetMapping("/welcome")
    public ModelAndView welcome() {
        return new ModelAndView("welcome");
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }
}

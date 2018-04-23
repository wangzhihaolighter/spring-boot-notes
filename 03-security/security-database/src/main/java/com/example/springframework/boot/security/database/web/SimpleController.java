package com.example.springframework.boot.security.database.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RestController
public class SimpleController {

    @GetMapping("/")
    public String home() {
        return "hello security datasource";
    }

    @GetMapping("/welcome")
    public ModelAndView welcome() {
        return new ModelAndView("/welcome");
    }

}

package com.example.springframework.boot.security.database.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class SimpleController {

    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("/welcome");
    }

}

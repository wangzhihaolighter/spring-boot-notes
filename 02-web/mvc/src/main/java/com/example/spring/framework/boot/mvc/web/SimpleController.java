package com.example.spring.framework.boot.mvc.web;

import org.springframework.web.bind.annotation.*;

@RestController
public class SimpleController {

    @GetMapping("/")
    public String home() {
        return "hello mvc";
    }

}

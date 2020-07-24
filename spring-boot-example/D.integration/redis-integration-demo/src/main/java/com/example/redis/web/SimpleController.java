package com.example.redis.web;

import org.springframework.web.bind.annotation.*;

@RestController
public class SimpleController {

    @GetMapping("/")
    public String home() {
        return "hello world";
    }

}

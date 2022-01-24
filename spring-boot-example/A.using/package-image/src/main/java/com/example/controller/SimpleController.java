package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    @GetMapping("/")
    public String hello() {
        return "Hello,World!";
    }

    @GetMapping("/update")
    public String update() {
        return "更新一下接口";
    }
}

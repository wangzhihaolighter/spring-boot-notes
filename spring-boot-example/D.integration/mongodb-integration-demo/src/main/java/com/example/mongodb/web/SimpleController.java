package com.example.mongodb.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    @GetMapping("/")
    public String home() {
        return "hello mongo";
    }

}

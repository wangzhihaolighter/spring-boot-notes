package com.example.springframework.boot.aop.filter.interceptor.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    @GetMapping("/")
    public String home(){
        return "hello aop filter interceptor";
    }

}

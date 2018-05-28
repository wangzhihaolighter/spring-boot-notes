package com.example.springframework.boot.starterconsumer.web;

import com.example.springframework.boot.demo.spring.boot.starter.service.SimpleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    @Autowired
    private SimpleService simpleService;

    @GetMapping("/")
    public String home() {
        return "hello start consumer";
    }

    @GetMapping("/wrap")
    public String doWrap(@RequestParam("word") String word) {
        return simpleService.wrap(word);
    }


}

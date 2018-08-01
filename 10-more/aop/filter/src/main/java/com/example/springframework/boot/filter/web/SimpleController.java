package com.example.springframework.boot.filter.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SimpleController {

    @GetMapping("/")
    public String home() {
        return "hello filter";
    }

    @GetMapping("/do")
    public void doSomething() throws InterruptedException {
        Thread.sleep(1000);
        log.info("do something...");
    }

    @GetMapping("/shutdown")
    public void shutDown() {
        System.exit(0);
    }

}

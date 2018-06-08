package com.example.springframework.boot.async.transaction.web;

import com.example.springframework.boot.async.transaction.service.SimpleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    @Autowired
    private SimpleService simpleService;

    @GetMapping("/")
    public String home() {
        return "hello async transaction";
    }

    @GetMapping("/test/async/no/after/commit")
    public void testAsyncNoAfterCommit() {
        simpleService.testAsyncNoAfterCommit();
    }

    @GetMapping("/test/async/after/commit")
    public void testAsyncAfterCommit() {
        simpleService.testAsyncAfterCommit();
    }

}

package com.example.springframework.boot.interceptor.web;

import com.example.springframework.boot.interceptor.service.SimpleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    @Autowired
    private SimpleService simpleService;

    @GetMapping("/")
    public String home() {
        return "hello interceptor";
    }

    @PostMapping("/login")
    public void login(@RequestParam("username") String username,
                      @RequestParam("password") String password) {
        simpleService.login(username, password);
    }

    @PostMapping("/logout")
    public void logout() {
        simpleService.logout();
    }

    @GetMapping("/do")
    public void doSomething() {
        simpleService.doSomething();
    }

}

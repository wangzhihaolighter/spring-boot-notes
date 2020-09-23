package com.example.springframework.boot.security.authorization.server.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    @GetMapping("/")
    public String home(){
        return "hello security authorization server";
    }

}

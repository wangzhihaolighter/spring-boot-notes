package com.example.springframework.boot.hello.web;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    public String home(){
        return "Hello World";
    }

}

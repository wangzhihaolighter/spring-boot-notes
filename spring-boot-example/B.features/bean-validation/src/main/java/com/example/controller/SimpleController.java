package com.example.controller;

import com.example.exception.BusinessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    @GetMapping("/")
    public String sayHello() {
        return "Hello,World!";
    }

    @GetMapping("/fail")
    public void fail(@RequestParam(value = "code", required = false, defaultValue = "99") String code) {
        BusinessException.throwMessage(code);
    }

}

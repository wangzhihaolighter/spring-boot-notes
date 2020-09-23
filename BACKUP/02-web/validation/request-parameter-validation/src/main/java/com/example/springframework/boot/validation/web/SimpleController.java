package com.example.springframework.boot.validation.web;

import com.example.springframework.boot.validation.config.dto.UserSimpleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SimpleController {

    @GetMapping("/")
    public String home() {
        return "hello validation";
    }

    @PostMapping("/user/save")
    public UserSimpleDTO save(@Validated @RequestBody UserSimpleDTO userSimpleDTO) {
        return userSimpleDTO;
    }

}

package com.example.springframework.boot.security.db.swagger.web;

import com.example.springframework.boot.security.db.swagger.config.response.DemoResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system")
public class SystemController {

    @GetMapping("/home")
    public DemoResult home() {
        return DemoResult.success("system home");
    }

}

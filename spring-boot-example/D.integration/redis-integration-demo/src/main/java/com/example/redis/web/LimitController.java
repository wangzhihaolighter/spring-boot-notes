package com.example.redis.web;

import com.example.redis.config.annotation.IpLimit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/limit")
public class LimitController {

    @IpLimit(key = "test", name = "测试IP限流", duration = 60, maxRate = 10)
    @GetMapping("/test")
    public String test() {
        return "测试IP限流";
    }

}

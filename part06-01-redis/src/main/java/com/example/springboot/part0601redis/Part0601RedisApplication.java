package com.example.springboot.part0601redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class Part0601RedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(Part0601RedisApplication.class, args);
    }
}

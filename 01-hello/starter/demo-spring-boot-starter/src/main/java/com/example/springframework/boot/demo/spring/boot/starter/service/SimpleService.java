package com.example.springframework.boot.demo.spring.boot.starter.service;

/**
 * 将字符串加上前后缀
 */
public class SimpleService {

    private String prefix;
    private String suffix;

    public SimpleService(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public String wrap(String word) {
        return prefix + word + suffix;
    }
}

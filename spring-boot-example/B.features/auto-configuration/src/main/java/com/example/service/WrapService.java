package com.example.service;

/**
 * 将字符串加上前后缀
 */
public class WrapService {

    private String prefix;
    private String suffix;

    public WrapService(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public String wrap(String word) {
        return prefix + word + suffix;
    }
}

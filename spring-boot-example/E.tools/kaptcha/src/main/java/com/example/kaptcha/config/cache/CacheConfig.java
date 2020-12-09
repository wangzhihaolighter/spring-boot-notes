package com.example.kaptcha.config.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class CacheConfig {
    @Resource
    private Caffeine<Object, Object> caffeine;

    @Bean
    public Cache<String, Object> cache() {
        return caffeine.build();
    }

}
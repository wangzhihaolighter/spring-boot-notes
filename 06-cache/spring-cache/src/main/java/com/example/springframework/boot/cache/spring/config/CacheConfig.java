package com.example.springframework.boot.cache.spring.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class CacheConfig {
    /*
    注解说明
        @EnableCaching：开启注解式缓存支持
        @CacheConfig：提供类级别的公共缓存配置
        @Cacheable：标记方法的结果可以缓存
        @CachePut：标记方法触发添加缓存操作
        @CacheEvict：标记方法出发移除缓存操作
     */
}

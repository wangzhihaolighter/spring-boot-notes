package com.example.security.admin.config.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CaffeineConfig {

    @Bean
    public Cache<String, Object> caffeineCache() {
        /*
        Caffeine配置说明：
            initialCapacity=[integer]: 初始的缓存空间大小
            maximumSize=[long]: 缓存的最大条数
            maximumWeight=[long]: 缓存的最大权重
            expireAfterAccess=[duration]: 最后一次写入或访问后经过固定时间过期
            expireAfterWrite=[duration]: 最后一次写入后经过固定时间过期
            refreshAfterWrite=[duration]: 创建缓存或者最近一次更新缓存后经过固定的时间间隔，刷新缓存
            weakKeys: 打开key的弱引用
            weakValues：打开value的弱引用
            softValues：打开value的软引用
            recordStats：开发统计功能
         */
        return Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(1000)
                .expireAfterWrite(60, TimeUnit.SECONDS)
                .build();
    }

}

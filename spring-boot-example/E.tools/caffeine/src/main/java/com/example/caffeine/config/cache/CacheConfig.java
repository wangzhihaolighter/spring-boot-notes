package com.example.caffeine.config.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Slf4j
@EnableCaching
@Configuration
public class CacheConfig extends CachingConfigurerSupport implements InitializingBean {
    @Resource
    private Caffeine<Object, Object> caffeine;

    @Bean
    public Cache<String, Object> cache() {
        return caffeine.build();
    }

    /**
     * 配置缓存管理器
     *
     * @return 缓存管理器
     */
    @Bean("caffeineCacheManager")
    @Override
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("----- 测试Caffeine缓存 -----");
        cache().put("1", 1);
        log.info("Cache EstimatedSize: {}", cache().estimatedSize());
        cache().put("2", 2);
        log.info("Cache EstimatedSize: {}", cache().estimatedSize());
        log.info("Cache key 1 ，value: {}", cache().getIfPresent("1"));
        log.info("Cache key 2 ，value: {}", cache().getIfPresent("2"));
    }

}
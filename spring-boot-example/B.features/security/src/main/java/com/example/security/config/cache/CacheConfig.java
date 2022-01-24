package com.example.security.config.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@EnableCaching
@Configuration
public class CacheConfig extends CachingConfigurerSupport {
  private final Caffeine<Object, Object> caffeine;

  public CacheConfig(Caffeine<Object, Object> caffeine) {
    this.caffeine = caffeine;
  }

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
}

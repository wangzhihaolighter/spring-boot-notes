package com.example.caffeine.controller;

import com.example.caffeine.config.cache.CacheService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cache")
public class CacheController {

  private final CacheService cacheService;

  public CacheController(CacheService cacheService) {
    this.cacheService = cacheService;
  }

  @GetMapping
  public Object getAll() {
    return cacheService.getAll();
  }

  @GetMapping("/key")
  public Object get(String key) {
    return cacheService.getValue(key);
  }

  @PutMapping
  public String put(String key, String value) {
    cacheService.putValue(key, value);
    return "ok";
  }

  @GetMapping("/stats")
  public String cacheStats() {
    return cacheService.cacheStats().toString();
  }
}

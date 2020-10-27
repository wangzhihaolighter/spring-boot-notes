package com.example.caffeine.web;

import com.example.caffeine.config.cache.CacheService;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/cache")
public class CacheController {

    private final CacheService cacheService;

    @GetMapping("/get")
    public Object get(String key) {
        return cacheService.getValue(key);
    }

    @GetMapping("/get/all")
    public Object getAll() {
        return cacheService.getAll();
    }

    @PutMapping("/put")
    public String put(String key, String value) {
        cacheService.putValue(key, value);
        return "success";
    }

    @GetMapping("/stats")
    public String cacheStats() {
        return cacheService.cacheStats().toString();
    }
}

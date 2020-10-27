package com.example.caffeine.config.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class CacheService {

    private final Cache<String, Object> cache;

    public CacheService(Cache<String, Object> cache) {
        this.cache = cache;
    }

    public Object getValue(String key) {
        if (key == null) {
            return null;
        }

        if (cache != null) {
            return cache.getIfPresent(key);
        }

        return null;
    }

    public Map<String, Object> getAll() {
        if (cache != null) {
            return cache.asMap();
        }

        return null;
    }

    public Map<String, Object> get(Set<String> keySet) {
        if (keySet == null || keySet.isEmpty()) {
            return null;
        }

        if (cache != null) {
            return cache.getAllPresent(keySet);
        }

        return null;
    }


    public void putValue(String key, Object value) {
        if (key == null || value == null) {
            return;
        }

        if (cache != null) {
            cache.put(key, value);
        }
    }

    public void removeKey(String key) {
        if (key == null) {
            return;
        }

        if (cache != null) {
            cache.invalidate(key);
        }
    }

    public void removeKey(Set<String> keySet) {
        if (keySet == null || keySet.isEmpty()) {
            return;
        }

        if (cache != null) {
            cache.invalidate(keySet);
        }
    }

    public boolean existKey(String key) {
        return getValue(key) != null;
    }

    public CacheStats cacheStats() {
        if (cache != null) {
            return cache.stats();
        }

        return null;
    }

}

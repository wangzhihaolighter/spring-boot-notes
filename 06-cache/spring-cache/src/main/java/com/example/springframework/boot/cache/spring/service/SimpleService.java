package com.example.springframework.boot.cache.spring.service;

import com.example.springframework.boot.cache.spring.dao.IdeaRepository;
import com.example.springframework.boot.cache.spring.entity.Idea;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@CacheConfig(cacheNames = "ideas")
public class SimpleService {

    @Autowired
    private IdeaRepository ideaRepository;

    public List<Idea> queryAll() {
        return ideaRepository.findAll();
    }

    @CachePut(key = "#p0.id")
    public Idea insert(Idea idea) {
        return ideaRepository.save(idea);
    }

    @Cacheable(key = "#p0")
    public Idea query(Long id) {
        return ideaRepository.findById(id).orElse(null);
    }

    @CacheEvict(key = "#p0")
    public void delete(Long id) {
        ideaRepository.deleteById(id);
    }

    @CacheEvict(allEntries = true)
    public void deleteAll() {
        ideaRepository.deleteAll();
    }
}

package com.example.springframework.boot.cache.spring.dao;

import com.example.springframework.boot.cache.spring.entity.Idea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdeaRepository extends JpaRepository<Idea, Long> {
}

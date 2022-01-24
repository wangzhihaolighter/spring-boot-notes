package com.example.caffeine.service;

import com.example.caffeine.dao.UserRepository;
import com.example.caffeine.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@CacheConfig(cacheNames = "users")
public class UserService {

  public static final String CACHE_KEY_QUERY_ALL = "queryAll";

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Cacheable(
      key = "T(com.example.caffeine.service.UserService).CACHE_KEY_QUERY_ALL",
      unless = "#result == null || #result.isEmpty()")
  public List<User> queryAll() {
    return userRepository.findAll();
  }

  @Caching(
      put = {@CachePut(key = "#result.id")},
      evict = {
        @CacheEvict(key = "T(com.example.caffeine.service.UserService).CACHE_KEY_QUERY_ALL")
      })
  public User save(User user) {
    user.setId(null);
    return userRepository.save(user);
  }

  @Caching(
      put = {@CachePut(key = "#result.id")},
      evict = {
        @CacheEvict(key = "T(com.example.caffeine.service.UserService).CACHE_KEY_QUERY_ALL")
      })
  public User update(User user) {
    return userRepository.save(user);
  }

  @Cacheable(key = "#p0")
  public User query(Long id) {
    return userRepository.findById(id).orElse(null);
  }

  @Caching(
      evict = {
        @CacheEvict(key = "#p0"),
        @CacheEvict(key = "T(com.example.caffeine.service.UserService).CACHE_KEY_QUERY_ALL")
      })
  public void delete(Long id) {
    userRepository.deleteById(id);
  }

  @CacheEvict(allEntries = true)
  public void deleteAll() {
    userRepository.deleteAll();
  }
}

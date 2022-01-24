package com.example.elasticsearch;

import com.example.elasticsearch.dao.UserDao;
import com.example.elasticsearch.entity.User;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
class ElasticSearchApplicationTests {

  @Autowired UserDao userDao;

  @Test
  void insert() {
    userDao.deleteAll();
    for (int i = 0; i < 10; i++) {
      User user = new User();
      user.setUsername("lighter");
      user.setAge(i);
      user.setCreateTime(LocalDateTime.now());
      userDao.save(user);
    }
    userDao.findAll().forEach(System.out::println);
  }

  @Test
  void update() {
    userDao
        .findByAgeBetween(6, 8)
        .forEach(
            user -> {
              user.setUsername("xxx");
              userDao.save(user);
            });
    userDao.findAll().forEach(System.out::println);
  }

  @Test
  void query() {
    System.out.println("===== 查询所有 =====");
    userDao.findAll().forEach(System.out::println);

    System.out.println("===== 分页查询 =====");
    Page<User> userPage = userDao.findAll(PageRequest.of(0, 5));
    System.out.println(userPage.getTotalPages());
    System.out.println(userPage.getTotalElements());
    System.out.println(userPage.getNumber());
    System.out.println(userPage.getNumberOfElements());
    userPage.getContent().forEach(System.out::println);

    System.out.println("===== 根据用户名查询 =====");
    userDao.findByUsername("lighter").forEach(System.out::println);

    System.out.println("===== 通过DSL查询 =====");
    userDao.selectByDsl("light").forEach(System.out::println);

    System.out.println("===== 根据年龄范围查询 =====");
    userDao.findByAgeBetween(6, 8).forEach(System.out::println);
  }

  @Test
  void delete() {
    userDao.deleteAll();
    userDao.findAll().forEach(System.out::println);
  }
}

package com.example.mongodb.controller;

import com.example.mongodb.dao.UserDao;
import com.example.mongodb.dto.PageInfoDTO;
import com.example.mongodb.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/mt")
public class UserByMongoTemplateController {

    private final UserDao userDao;

    public UserByMongoTemplateController(UserDao userDao) {
        this.userDao = userDao;
    }

    /* insert */

    @PostMapping("/save")
    public String save(@RequestBody User user) {
        return userDao.save(user);
    }

    @PostMapping("/save/batch")
    public List<String> save(@RequestBody List<User> users) {
        return userDao.batchSave(users);
    }

    /* select */

    @GetMapping("/query/all")
    public List<User> findAll() {
        return userDao.findAll();
    }

    @GetMapping("/query/{id}")
    public User findById(@PathVariable("id") String id) {
        return userDao.findById(id);
    }

    @GetMapping("/query/username")
    public List<User> findByUsername(@RequestParam("username") String username) {
        return userDao.findByUsername(username);
    }

    @GetMapping("/query/page")
    public PageInfoDTO<User> pageQuery(@RequestParam("pageNum") Integer pageNum,
                                       @RequestParam("pageSize") Integer pageSize) {
        return userDao.pageQuery(pageNum, pageSize);
    }

    @GetMapping("/query/sort")
    public List<User> sortQuery() {
        return userDao.findSort(Sort.Order.asc("id"));
    }

    /* update */

    @PutMapping("/update")
    public Long update(@RequestBody User user) {
        return userDao.update(user);
    }

    @PutMapping("/update/batch")
    public Integer batchUpdate(@RequestBody List<User> users) {
        return userDao.batchUpdate(users);
    }

    /* delete */

    @DeleteMapping("/delete")
    public Long delete(@RequestParam("id") String id) {
        return userDao.delete(id);
    }

    @DeleteMapping("/delete/batch")
    public Long batchDelete(@RequestBody List<String> ids) {
        return userDao.batchDelete(ids);
    }

}

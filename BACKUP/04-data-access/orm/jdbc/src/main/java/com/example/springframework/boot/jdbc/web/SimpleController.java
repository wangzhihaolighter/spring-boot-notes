package com.example.springframework.boot.jdbc.web;

import com.example.springframework.boot.jdbc.dao.UserDao;
import com.example.springframework.boot.jdbc.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SimpleController {

    @Autowired
    private UserDao userDao;

    @GetMapping("/")
    public String home() {
        return "hello jdbc";
    }

    @PostMapping("/user/insert")
    public Long insert(@RequestBody User user) {
        return userDao.insert(user);
    }

    @PutMapping("/user/update")
    public Integer update(@RequestBody User user) {
        return userDao.update(user);
    }

    @DeleteMapping("/user/delete/{id}")
    public Integer delete(@PathVariable("id") Long id) {
        return userDao.delete(id);
    }

    @DeleteMapping("/user/delete/batch")
    public Integer batchDelete(@RequestBody List<Long> ids) {
        return userDao.batchDelete(ids);
    }

    @GetMapping("/user/query/{id}")
    public User queryById(@PathVariable("id")Long id){
        return userDao.selectById(id);
    }

    @GetMapping("/user/query/all")
    public List<User> queryAll(){
        return userDao.selectAll();
    }
}

package com.example.springframework.boot.mybatis.web;

import com.example.springframework.boot.mybatis.entity.User;
import com.example.springframework.boot.mybatis.mapper.UserInfoMapper;
import com.example.springframework.boot.mybatis.mapper.UserMapper;
import com.example.springframework.boot.mybatis.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SimpleController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public String home() {
        return "hello mybatis";
    }

    /*----------单表数据的基本操作----------*/

    @PostMapping("/user/insert")
    public Long insert(@RequestBody User user) {
        userMapper.insert(user);
        return user.getId();
    }

    @PutMapping("/user/update")
    public Integer update(@RequestBody User user){
        return userMapper.update(user);
    }

    @DeleteMapping("/user/delete")
    public Integer update(@RequestParam("id") Long id){
        return userMapper.delete(id);
    }

    @GetMapping("/user/query/{id}")
    public User queryById(@PathVariable("id") Long id){
        return userMapper.queryById(id);
    }

    @GetMapping("/user/query/all")
    public List<User> queryAll(){
        return userMapper.queryAll();
    }

    /*----------高级查询：一对一/一对多----------*/

    @GetMapping("/user/query/detail/{id}")
    public User queryUserDetailById(@PathVariable("id") Long id){
        //一对一：user - user_info
        //一对多：user - user_role list
        return userMapper.queryUserDetailById(id);
    }

    /*----------批处理 查询/新增/更新/删除----------*/

    @PostMapping("/user/query/ids")
    public List<User> queryByIds(@RequestBody List<Long> ids){
        return userMapper.queryByIds(ids);
    }

    @PostMapping("/user/insert/batch")
    public List<Long> batchInsert(@RequestBody List<User> users){
        userMapper.batchInsert(users);
        List<Long> ids = new ArrayList<>();
        users.forEach(user -> ids.add(user.getId()));
        return ids;
    }

    @PutMapping("/user/update/batch")
    public Integer batchUpdate(@RequestBody List<User> users){
        return userMapper.batchUpdate(users);
    }

    @DeleteMapping("/user/delete/batch")
    public Integer batchDelete(@RequestBody List<Long> ids){
        return userMapper.batchDelete(ids);
    }


}

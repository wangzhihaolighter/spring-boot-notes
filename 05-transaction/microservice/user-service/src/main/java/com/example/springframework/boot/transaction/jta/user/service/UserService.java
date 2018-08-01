package com.example.springframework.boot.transaction.jta.user.service;

import com.example.springframework.boot.transaction.jta.user.entity.User;
import com.example.springframework.boot.transaction.jta.user.feign.PeopleSimpleClient;
import com.example.springframework.boot.transaction.jta.user.feign.dto.People;
import com.example.springframework.boot.transaction.jta.user.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class UserService {
    @Autowired
    private PeopleSimpleClient peopleSimpleClient;
    @Autowired
    private UserMapper userMapper;

    public void findAll() {
        List<User> userList = userMapper.queryAll();
        List<People> peopleList = peopleSimpleClient.findAll();
        log.info(userList.toString());
        log.info(peopleList.toString());
    }

    public void saveSuccess() {
        User user = new User();
        user.setName("乐观的小海狸");
        user.setPassword("123456");
        userMapper.insert(user);
        peopleSimpleClient.saveSuccess();
    }

    public void saveFailure() {
        User user = new User();
        user.setName("消极的小海狸");
        user.setPassword("123456");
        userMapper.insert(user);
        peopleSimpleClient.saveSuccess();
        int i = 1/0;
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveSuccessTransactional() {
        User user = new User();
        user.setName("高兴的小鼹鼠");
        user.setPassword("123456");
        userMapper.insert(user);
        peopleSimpleClient.saveSuccessTransactional();
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveFailureTransactional() {
        User user = new User();
        user.setName("失落的小鼹鼠");
        user.setPassword("654321");
        userMapper.insert(user);
        peopleSimpleClient.saveSuccessTransactional();
        int i = 1/0;
    }

    public void clean() {
        userMapper.clean();
        peopleSimpleClient.clean();
    }
}

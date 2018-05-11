package com.example.springframework.boot.data.source.aop.service;

import com.example.springframework.boot.data.source.aop.entity.User;
import com.example.springframework.boot.data.source.aop.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /*---------------主库读操作---------------*/

    public User selectById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    /*---------------从库读操作---------------*/

    public User queryById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public List<User> queryAll() {
        return userMapper.selectAll();
    }

    /*---------------主库写操作---------------*/

    @Transactional(rollbackFor = Exception.class)
    public Long save(User user) {
        userMapper.insert(user);
        return user.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        userMapper.deleteByPrimaryKey(id);
    }

}

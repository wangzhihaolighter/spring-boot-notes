package com.example.springframework.boot.data.source.aop.annotation.service;

import com.example.springframework.boot.data.source.aop.annotation.config.ds.DataSourceKeyConstant;
import com.example.springframework.boot.data.source.aop.annotation.config.ds.DynamicDataSourceKey;
import com.example.springframework.boot.data.source.aop.annotation.entity.User;
import com.example.springframework.boot.data.source.aop.annotation.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /*---------------主库读操作---------------*/

    @DynamicDataSourceKey(DataSourceKeyConstant.MASTER)
    public User masterQueryById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @DynamicDataSourceKey(DataSourceKeyConstant.MASTER)
    public List<User> masterQueryAll() {
        return userMapper.selectAll();
    }

    /*---------------从库读操作---------------*/

    @DynamicDataSourceKey(DataSourceKeyConstant.SLAVE)
    public User slaveQueryById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @DynamicDataSourceKey(DataSourceKeyConstant.SLAVE)
    public List<User> slaveQueryAll() {
        return userMapper.selectAll();
    }

    /*---------------主库读写操作---------------*/

    @DynamicDataSourceKey(DataSourceKeyConstant.MASTER)
    public Long save(User user) {
        userMapper.insert(user);
        return user.getId();
    }

    @DynamicDataSourceKey(DataSourceKeyConstant.MASTER)
    public void delete(Long id) {
        userMapper.deleteByPrimaryKey(id);
    }

    /*---------------事务测试---------------*/

    public void create() {
        //master query
        List<User> users = masterQueryAll();
        log.info(users.toString());

        //master insert
        User user = new User();
        user.setUsername("飞翔的老菜根");
        user.setPassword("123456");
        save(user);
        log.info(user.toString());

        //slave query
        List<User> userList = slaveQueryAll();
        log.info(userList.toString());

        //master insert
        user.setUsername("悲伤的老菜根");
        user.setPassword("123456");
        save(user);
        log.info(user.toString());

        //error
        throw new RuntimeException("未知错误");
    }
}

package com.example.springframework.boot.aop.transaction.service;

import com.example.springframework.boot.aop.transaction.entity.User;
import com.example.springframework.boot.aop.transaction.exception.DeleteRuntimeException;
import com.example.springframework.boot.aop.transaction.exception.OtherException;
import com.example.springframework.boot.aop.transaction.exception.UpdateRuntimeException;
import com.example.springframework.boot.aop.transaction.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public List<User> queryAll() {
        userMapper.queryAll();
        return userMapper.queryAll();
    }

    public User queryById(Long id) {
        //查询
        User user = userMapper.queryById(id);

        //测试设置，只读设置是否生效
        userMapper.delete(id);
        //注意，h2不支持readOnly设置，故只读设置不生效。而mysql、MariaDB支持，不同数据库之前存在差异

        return user;
    }

    public Long insert(User user) throws OtherException {
        //插入数据
        userMapper.insert(user);

        //测试指定非运行时异常回滚是否生效
        if (new Random().nextInt(10) % 2 == 0) {
            throw new OtherException("其他非运行时异常 - 回滚");
        }

        return user.getId();
    }

    public List<Long> insertBatch(List<User> users) throws OtherException {
        //新增数据
        userMapper.batchInsert(users);
        List<Long> ids = new ArrayList<>();
        users.forEach(user -> ids.add(user.getId()));

        //测试指定非运行时异常回滚是否生效
        if (new Random().nextInt(10) % 2 == 0) {
            throw new OtherException("其他非运行时异常 - 回滚");
        }

        return ids;
    }

    public Integer update(User user) {
        //更新
        Integer update = userMapper.update(user);

        //测试指定运行时异常不回滚是否生效
        if (new Random().nextInt(10) % 2 == 0) {
            throw new UpdateRuntimeException("更新数据异常 - 不回滚");
        }

        return update;
    }

    public Integer updateBatch(List<User> users) {
        //更新
        Integer update = userMapper.batchUpdate(users);

        //测试指定运行时异常不回滚是否生效
        if (new Random().nextInt(10) % 2 == 0) {
            throw new UpdateRuntimeException("更新数据异常 - 不回滚");
        }

        return update;
    }

    public Integer delete(Long id) {
        //删除
        Integer delete = userMapper.delete(id);

        //测试指定运行时异常不回滚是否生效
        if (new Random().nextInt(10) % 2 == 0) {
            throw new DeleteRuntimeException("删除数据异常 - 不回滚");
        }

        return delete;
    }

    public Integer batchDelete(List<Long> ids) {
        //删除
        Integer delete = userMapper.batchDelete(ids);

        //测试指定运行时异常不回滚是否生效
        if (new Random().nextInt(10) % 2 == 0) {
            throw new DeleteRuntimeException("删除数据异常 - 不回滚");
        }

        return delete;
    }

}

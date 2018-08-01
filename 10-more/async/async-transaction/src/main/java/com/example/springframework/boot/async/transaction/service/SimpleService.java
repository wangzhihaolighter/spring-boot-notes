package com.example.springframework.boot.async.transaction.service;

import com.example.springframework.boot.async.transaction.entity.User;
import com.example.springframework.boot.async.transaction.mapper.UserMapper;
import com.example.springframework.boot.async.transaction.task.SimpleTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SimpleService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SimpleTask simpleTask;

    public void testAsyncNoAfterCommit() {
        log.info(" ----- 测试未设置事务提交后再执行 -----");
        //对于存在事务的方法，存在：方法执行时间长，此时事务还未提交，异步方法已经开始执行，会出现数据异常
        //新增数据 - 存在事务
        User user = new User();
        user.setId(9527L);
        user.setUsername("华安");
        user.setPassword("我为秋香");
        userMapper.insert(user);

        //异步方法 - 其中有数据查询
        simpleTask.doTask();

        //查询数据 - 延长事务，事务未提交
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //查询用户
        List<User> users = userMapper.queryAll();
        log.info("[ testAsyncNoAfterCommit ] " + users.toString());

        //记录时间
        log.info("[ testAsyncNoAfterCommit ] time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

    public void testAsyncAfterCommit() {
        log.info(" ----- 测试设置事务提交后再执行 -----");

        //新增数据 - 存在事务
        User user = new User();
        user.setId(10000L);
        user.setUsername("轩辕氏");
        user.setPassword("123456");
        userMapper.insert(user);

        //事务提交后执行方法
        //注意：使用TransactionSynchronizationManager，方法（如：testAsyncAfterCommit）必须被spring事务管理
        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronizationAdapter() {
                    @Override
                    public void afterCommit() {
                        simpleTask.doTask();
                    }
                }
        );

        //查询数据 - 延长方法执行，使事务未提交
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //查询用户
        List<User> users = userMapper.queryAll();
        log.info("[ testAsyncAfterCommit ] " + users.toString());

        //记录时间
        log.info("[ testAsyncAfterCommit ] time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

}



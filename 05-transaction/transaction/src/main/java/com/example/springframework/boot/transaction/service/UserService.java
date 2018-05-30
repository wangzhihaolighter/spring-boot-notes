package com.example.springframework.boot.transaction.service;

import com.example.springframework.boot.transaction.config.TransactionConfig;
import com.example.springframework.boot.transaction.entity.User;
import com.example.springframework.boot.transaction.exception.*;
import com.example.springframework.boot.transaction.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Transactional(readOnly = true)
    public List<User> queryAll() {
        return userMapper.queryAll();
    }

    @Transactional(readOnly = true)
    public User queryById(Long id) {
        //查询
        User user = userMapper.queryById(id);

        //测试设置，只读，能否删除
        userMapper.delete(id);
        //删除成功（h2数据库）
        //结论：仍可以成功，这是由于h2数据库的原因，()
        //h2数据库不支持read-only，若是mysql，则会异常：java.sql.SQLException: Connection is read-only. Queries leading to data modification are not allowed

        return user;
    }

    @Transactional(rollbackFor = {InsertRuntimeException.class}, timeout = 5, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public Long insert(User user) {
        //插入数据
        userMapper.insert(user);

        log.info("insert success:" + user);

        //用于测试事务超时是否会回滚
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //不会回滚
        //结论：因为Spring事务超时 = 事务开始时到最后一个Statement创建时时间 + 最后一个Statement的执行时超时时间（即其queryTimeout）。
        //故事务超时时间配置是sql执行时间，与方法时间无关

        return user.getId();
    }

    @Transactional(value = TransactionConfig.DEFAULT_TRANSACTION_MANAGER_NAME, rollbackFor = {InsertRuntimeException.class})
    public List<Long> batchInsert(List<User> users) throws OtherException {
        //新增数据
        userMapper.batchInsert(users);
        List<Long> ids = new ArrayList<>();
        users.forEach(user -> ids.add(user.getId()));

        log.info("batchInsert success:" + users);

        //测试触发回滚的异常
        if (new Random().nextInt(10) % 2 == 0) {
            //回滚
            throw new InsertRuntimeException("批量新增数据异常");
        }
        if (new Random().nextInt(10) % 2 == 0) {
            //回滚
            throw new OtherRuntimeException("其他运行时异常");
        }
        if (new Random().nextInt(10) % 2 == 0) {
            //不回滚
            throw new OtherException("其他非运行时异常");
        }
        //结论，RuntimeException及其子类（运行时异常）会正常回滚，非运行时异常回滚需指定方可回滚

        return ids;
    }

    @Transactional(transactionManager = TransactionConfig.DEFAULT_TRANSACTION_MANAGER_NAME, rollbackForClassName = {"com.example.springframework.boot.transaction.exception.OtherException"})
    public Integer update(User user) throws OtherException {
        //更新
        Integer update = userMapper.update(user);

        //测试异常是否会触发回滚
        if (new Random().nextInt(10) % 2 == 0) {
            throw new OtherException("其他非运行时异常");
        }
        //回滚
        //结论，指定非运行时异常，触发回滚

        return update;
    }

    @Transactional(transactionManager = TransactionConfig.DEFAULT_TRANSACTION_MANAGER_NAME, noRollbackFor = {OtherRuntimeException.class})
    public Integer batchUpdate(List<User> users) throws OtherException {
        //更新
        Integer update = userMapper.batchUpdate(users);

        //测试指定不会触发回滚的异常
        if (new Random().nextInt(10) % 2 == 0) {
            //回滚
            throw new UpdateRuntimeException("批量更新数据异常");
        }
        if (new Random().nextInt(10) % 2 == 0) {
            //不回滚
            throw new OtherRuntimeException("其他运行时异常");
        }
        if (new Random().nextInt(10) % 2 == 0) {
            //不回滚
            throw new OtherException("其他非运行时异常");
        }

        /*
        结论：
        noRollbackFor：
            未指定的运行时异常会触发回滚
            指定的运行时异常不会触发回滚
            未指定的非运行时异常不会触发回滚
         */

        return update;
    }

    @Transactional(noRollbackForClassName = {"com.example.springframework.boot.transaction.exception.DeleteRuntimeException"})
    public Integer delete(Long id) {
        //删除
        Integer delete = userMapper.delete(id);

        //测试noRollbackForClassName指定运行时异常是否不回滚
        if (new Random().nextInt(10) % 2 == 0) {
            throw new DeleteRuntimeException("删除数据异常");
        }
        //不回滚
        //结论：rollbackForClassName指定的运行时异常不会触发回滚

        return delete;
    }

    @Transactional(noRollbackForClassName = {"com.example.springframework.boot.transaction.exception.OtherRuntimeException"})
    public Integer batchDelete(List<Long> ids) {
        //删除
        Integer delete = userMapper.batchDelete(ids);

        //测试noRollbackForClassName未指定运行时异常是否回滚
        if (new Random().nextInt(10) % 2 == 0) {
            throw new DeleteRuntimeException("批量删除数据异常");
        }
        //回滚
        //结论：rollbackForClassName未指定的运行时异常会触发回滚

        return delete;
    }

}

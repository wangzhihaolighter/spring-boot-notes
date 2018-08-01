package com.example.springframework.boot.async.transaction.task;

import com.example.springframework.boot.async.transaction.config.AsyncConfig;
import com.example.springframework.boot.async.transaction.entity.User;
import com.example.springframework.boot.async.transaction.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class SimpleTask {

    @Autowired
    private UserMapper userMapper;

    @Async(AsyncConfig.EXECUTOR_ID)
    public void doTask() {
        //查询
        List<User> users = userMapper.queryAll();
        log.info("[ DO TASK ]" + users.toString());

        log.info("[ DO TASK ] time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

}

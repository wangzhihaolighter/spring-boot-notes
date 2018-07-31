package com.example.springframework.boot.multi.transaction.service;

import com.example.springframework.boot.multi.transaction.entity.User;
import com.example.springframework.boot.multi.transaction.mapper.cluster.ClusterMapper;
import com.example.springframework.boot.multi.transaction.mapper.primary.PrimaryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserService {

    @Autowired
    private PrimaryMapper primaryMapper;
    @Autowired
    private ClusterMapper clusterMapper;

    public List<User> findAll() {
        List<User> result = new ArrayList<>();
        result.addAll(primaryMapper.queryAll());
        result.addAll(clusterMapper.queryAll());
        return result;
    }

    public void saveSuccess() {
        User user = new User();
        user.setUsername("骄傲的小犀牛");
        user.setPassword("123456");
        primaryMapper.insert(user);
        clusterMapper.insert(user);
    }

    public void saveFailure() {
        User user = new User();
        user.setUsername("失落的小水牛");
        user.setPassword("123456");
        primaryMapper.insert(user);
        clusterMapper.insert(user);
        int i = 1 / 0;
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveSuccessTransactional() {
        saveSuccess();
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveFailureTransactional() {
        saveFailure();
    }

    public void clean() {
        primaryMapper.truncate();
        clusterMapper.truncate();
    }
}

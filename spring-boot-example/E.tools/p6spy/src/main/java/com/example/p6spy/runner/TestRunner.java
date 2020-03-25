package com.example.p6spy.runner;

import com.example.p6spy.dao.UserRepository;
import com.example.p6spy.entity.User;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目启动时开始测试
 */
@Component
public class TestRunner implements ApplicationRunner {
    private final UserRepository userRepository;

    public TestRunner(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("--------------- 测试p6spy开始 ---------------");
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setUsername("user" + i);
            user.setPassword("pwd" + i);
            userList.add(user);
        }
        System.out.println("\n初始查询");
        userRepository.findAll();
        System.out.println("\n保存信息");
        userRepository.saveAll(userList);
        System.out.println("\n新增后查询");
        userRepository.findAll();
        System.out.println("\n删除某条记录");
        userRepository.deleteById(5L);
        System.out.println("\n删除后查询");
        userRepository.findAll();
        System.out.println("\n重复查询");
        userRepository.findAll();
        System.out.println("--------------- 测试p6spy结束 ---------------");
    }
}

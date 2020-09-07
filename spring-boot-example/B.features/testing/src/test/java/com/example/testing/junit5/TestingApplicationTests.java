package com.example.testing.junit5;

import com.example.testing.controller.UserController;
import com.example.testing.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class TestingApplicationTests {

    @Autowired
    private UserController userController;

    /**
     * 事务不回滚
     */
    @Rollback(false)
    @Test
    public void test1() {
        User user = new User();
        user.setUsername("Github");
        user.setPassword("123456");
        Long id = userController.insert(user);
        System.out.println(id);
    }

    /**
     * 事务回滚
     */
    @Test
    public void test2() {
        User user = new User();
        user.setUsername("Gitlab");
        user.setPassword("123456");
        Long id = userController.insert(user);
        System.out.println(id);
    }

    @Test
    public void test3() {
        List<User> users = userController.queryAll();
        System.out.println(users);
    }

}

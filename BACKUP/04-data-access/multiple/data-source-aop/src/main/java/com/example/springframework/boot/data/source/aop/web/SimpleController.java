package com.example.springframework.boot.data.source.aop.web;

import com.example.springframework.boot.data.source.aop.entity.User;
import com.example.springframework.boot.data.source.aop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class SimpleController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home() {
        return "hello multiple dataSource aop";
    }

    /*--------------------事务测试部分start--------------------*/

    /**
     * 两个测试：一个处于一个事务(test)，一个处于不同事务(test second)
     * 1.新增1条数据
     * 2.查询数据
     * 3.一个错误
     * 结论：
     * 当处于一个事务，报错在事务中：触发回滚，不会触发切换数据源（只会切换一次）
     * 当处于不同事务，报错不在事务中：不触发回滚，会切换数据源
     * <p>
     * TODO 如何保证不同事务回滚的一致性 - 分布式事务管理
     */

    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/test")
    public void test() {
        //master insert
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        userService.save(user);
        log.info("-----test----insert-----" + user);
        //slave query -> transaction change master query
        List<User> users = userService.queryAll();
        log.info("-----test----query-----" + users);
        //error test transaction rollback
        throw new RuntimeException("未知错误");
    }

    @GetMapping("/test/second")
    public void testSecond() {
        //insert
        User user = new User();
        user.setUsername("test second");
        user.setPassword("test second");
        userService.save(user);
        log.info("-----test second----insert-----" + user);
        //query
        List<User> users = userService.queryAll();
        log.info("-----test second----query-----" + users);
        //error test transaction rollback
        throw new RuntimeException("未知错误");
    }

    /*--------------------事务测试部分end--------------------*/

    /*---------------主库读操作---------------*/

    @GetMapping("/master/user/query/{id}")
    public User selectUserById(@PathVariable("id") Long id) {
        return userService.selectById(id);
    }

    @GetMapping("/master/user/query/all")
    public List<User> selectUserAll() {
        return userService.selectAll();
    }

    /*---------------从库读操作---------------*/

    @GetMapping("/slave/user/query/{id}")
    public User queryUserById(@PathVariable("id") Long id) {
        return userService.queryById(id);
    }

    @GetMapping("/slave/user/query/all")
    public List<User> queryUserAll() {
        return userService.queryAll();
    }

    /*---------------主库写操作---------------*/

    @PostMapping("/user/save")
    public Long saveUser(@RequestBody User user) {
        return userService.save(user);
    }

    @PostMapping("/user/delete/{id}")
    public void deleteUserById(@PathVariable("id") Long id) {
        userService.delete(id);
    }

}

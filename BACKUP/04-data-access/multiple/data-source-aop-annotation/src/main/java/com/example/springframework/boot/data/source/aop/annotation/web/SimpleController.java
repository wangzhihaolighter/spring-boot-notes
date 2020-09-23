package com.example.springframework.boot.data.source.aop.annotation.web;

import com.example.springframework.boot.data.source.aop.annotation.entity.User;
import com.example.springframework.boot.data.source.aop.annotation.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class SimpleController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home() {
        return "hello multiple dataSource aop annotation";
    }

    /*--------------------事务测试部分start--------------------*/

    /**
     * 测试
     * 1.查询
     * 2.新增数据
     * 3.查询
     * 4.新增数据
     * 结论：
     * 当处于一个事务，报错在事务中：触发回滚，不会触发切换数据源（只会切换一次）
     * 当处于不同事务，报错不在事务中：不触发回滚，会切换数据源
     * <p>
     * TODO 如何保证不同事务回滚的一致性 - 分布式事务管理
     */
    @GetMapping("/test")
    public void test() {
        //create方法被自定义事务管理器拦截，自定义其事务管理，不会切换数据源，报错会触发回滚
        //重命名方法，不被自定义事务管理器拦截，会切换数据源，但报错不会回滚
        userService.create();
    }

    /*--------------------事务测试部分end--------------------*/

    /*---------------主库读操作---------------*/

    @GetMapping("/master/user/query/{id}")
    public User masterQueryUserById(@PathVariable("id") Long id) {
        return userService.masterQueryById(id);
    }

    @GetMapping("/master/user/query/all")
    public List<User> masterQueryUserAll() {
        return userService.masterQueryAll();
    }

    /*---------------从库读操作---------------*/

    @GetMapping("/slave/user/query/{id}")
    public User slaveQueryUserById(@PathVariable("id") Long id) {
        return userService.slaveQueryById(id);
    }

    @GetMapping("/slave/user/query/all")
    public List<User> slaveQueryUserAll() {
        return userService.slaveQueryAll();
    }

    /*---------------主库读写操作---------------*/

    @PostMapping("/user/save")
    public Long saveUser(@RequestBody User user) {
        return userService.save(user);
    }

    @PostMapping("/user/delete/{id}")
    public void deleteUserById(@PathVariable("id") Long id) {
        userService.delete(id);
    }

}

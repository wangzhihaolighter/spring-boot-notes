package com.example.springframework.boot.demotomcatoptimize.web;

import com.example.springframework.boot.demotomcatoptimize.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {

    @RequestMapping("/")
    public User home(){
        User user =  new User();
        user.setId(1L);
        user.setName("飞翔的大白菜(●—●)");
        user.setDescription("像一颗海草海草海草，随波飘摇~");
        log.info("用户请求了一次(●—●)，好开森(*￣︶￣)");
        return user;
    }
}

package com.example.springframework.boot.test.web;

import com.example.springframework.boot.test.entity.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class SimpleController {

    @RequestMapping("/")
    public String home(){
        return "test";
    }

    @GetMapping("consumer")
    public User consumer(){
        User user = new User();
        user.setId(1L);
        user.setUsername("飞翔的大白菜");
        user.setPassword("白菜");
        return user;
    }

    @PostMapping("revert")
    public Object revert(@RequestBody Object obj){
        return obj;
    }

}

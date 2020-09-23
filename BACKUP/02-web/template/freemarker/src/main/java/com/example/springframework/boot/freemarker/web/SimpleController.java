package com.example.springframework.boot.freemarker.web;

import com.example.springframework.boot.freemarker.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class SimpleController {

    @GetMapping("/")
    public String home() {
        return "hello freemarker";
    }

    @GetMapping("/welcome")
    public ModelAndView welcome() {
        User user = new User();
        user.setId(1L);
        user.setUsername("飞翔的大白菜");
        user.setPassword("123456");
        ModelAndView modelAndView = new ModelAndView("/welcome");
        modelAndView.addObject("sysUser", user);
        return modelAndView;
    }

    @GetMapping("/test/error")
    public String error(){
        throw new RuntimeException("一个错误");
    }

}

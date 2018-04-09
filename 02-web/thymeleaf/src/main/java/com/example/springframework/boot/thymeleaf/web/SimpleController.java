package com.example.springframework.boot.thymeleaf.web;

import com.example.springframework.boot.thymeleaf.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SimpleController {

    @GetMapping("/")
    public ModelAndView welcome() {
        User user = new User();
        user.setId(1L);
        user.setUsername("飞翔的大白菜");
        user.setPassword("一颗默默生长的白菜");
        ModelAndView modelAndView = new ModelAndView("/welcome");
        modelAndView.addObject("sysUser", user);
        return modelAndView;
    }

    @GetMapping("/test/error")
    public String error() {
        throw new RuntimeException("一个错误");
    }

}

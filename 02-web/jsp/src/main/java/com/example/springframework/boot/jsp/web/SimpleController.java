package com.example.springframework.boot.jsp.web;

import com.example.springframework.boot.jsp.entity.User;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class SimpleController {

    @GetMapping("/")
    public ModelAndView welcome() {
        User user = new User();
        user.setId(1L);
        user.setUsername("飞翔的大白菜");
        user.setPassword("发呆的白菜");
        ModelAndView modelAndView = new ModelAndView("welcome");
        modelAndView.addObject("sysUser", user);
        return modelAndView;
    }

    @GetMapping("/test/error")
    public String error() {
        //cant return error.jsp
        throw new RuntimeException("one error");
    }

    @GetMapping("/to/error")
    public ModelAndView toError() {
        //find WEB-INF/jsp/error.jsp
        //correct
        //return new ModelAndView("/error");

        //error
        //return new ModelAndView("error");

        //spring boot - BasicErrorController user new ModelAndView("error") cant find error.jsp

        //find WEB-INF/jsp/error2.jsp

        //correct
        //return new ModelAndView("error2");
        return new ModelAndView("/error2");
    }

}

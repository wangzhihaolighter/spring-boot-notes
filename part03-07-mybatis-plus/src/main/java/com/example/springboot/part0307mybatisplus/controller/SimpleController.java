package com.example.springboot.part0307mybatisplus.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 *
 * @author Administrator
 * @date 2018/1/22
 */
@RestController
public class SimpleController {
    @RequestMapping("/")
    public String hello(){
        return "hello";
    }
}

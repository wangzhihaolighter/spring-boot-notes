package com.example.springboot.part0503jsp.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.Map;

@Controller
public class WelcomeController {

    @RequestMapping("/")
    public String welcome(ModelMap modelMap) {
        modelMap.put("time", new Date());
        modelMap.put("message", "hi lighter!");
        return "welcome";
    }

}
package com.example.kaptcha.controller;

import com.example.kaptcha.config.cache.CacheService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@AllArgsConstructor
@CrossOrigin
@Controller
public class SimpleController {
    private final CacheService cacheService;

    @GetMapping("/")
    public ModelAndView index(@RequestParam(value = "token", defaultValue = "") String token) {
        String viewName = "index";
        if (!cacheService.existKey(token)) {
            viewName = "login";
        }
        return new ModelAndView(viewName);
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @GetMapping("/error")
    public ModelAndView error() {
        return new ModelAndView("error");
    }

}

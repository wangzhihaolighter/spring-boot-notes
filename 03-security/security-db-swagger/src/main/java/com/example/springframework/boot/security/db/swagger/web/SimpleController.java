package com.example.springframework.boot.security.db.swagger.web;

import com.example.springframework.boot.security.db.swagger.config.response.DemoResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@Api(tags = "simple demo 接口")
public class SimpleController {

    @ApiOperation(value = "hello", produces = MediaType.TEXT_PLAIN_VALUE, notes = "返回：Hello")
    @GetMapping("/")
    public DemoResult home() {
        return DemoResult.success("hello spring security db swagger");
    }

    @ApiOperation(value = "欢迎页", produces = MediaType.TEXT_PLAIN_VALUE, notes = "返回：欢迎页")
    @GetMapping("/welcome")
    public ModelAndView welcome() {
        return new ModelAndView("welcome");
    }

    @ApiOperation(value = "登录页", produces = MediaType.TEXT_PLAIN_VALUE, notes = "返回：登录页")
    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }
}

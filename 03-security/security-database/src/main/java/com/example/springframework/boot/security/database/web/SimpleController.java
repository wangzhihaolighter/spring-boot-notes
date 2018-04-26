package com.example.springframework.boot.security.database.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController
@Api(tags = "simple通用接口")
public class SimpleController {

    @ApiOperation(value = "hello world", produces = MediaType.TEXT_PLAIN_VALUE, notes = "返回：hello world")
    @GetMapping("/")
    public String home() {
        return "hello security datasource";
    }

    @ApiOperation(value = "login", produces = MediaType.TEXT_PLAIN_VALUE, notes = "跳转login的post方法")
    @GetMapping("/login")
    public ModelAndView login() throws IOException {
        return new ModelAndView("login");
    }

    @ApiOperation(value = "跳转welcome页面", produces = MediaType.TEXT_PLAIN_VALUE, notes = "返回：welcome页面")
    @GetMapping("/welcome")
    public ModelAndView welcome() {
        return new ModelAndView("/welcome");
    }

}

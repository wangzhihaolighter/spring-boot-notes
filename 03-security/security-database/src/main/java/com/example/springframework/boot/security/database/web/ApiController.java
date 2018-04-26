package com.example.springframework.boot.security.database.web;

import com.example.springframework.boot.security.database.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api")
@Api(tags = "一些api接口")
public class ApiController {

    @ApiOperation(value = "查询所有用户", produces = MediaType.TEXT_PLAIN_VALUE, notes = "返回：用户信息")
    @GetMapping("/user/all")
    public User getAllUser(){
        User user = new User();
        user.setId(1L);
        user.setName("lighter");
        user.setDescription("一颗飞翔的大白菜");
        return user;
    }

}

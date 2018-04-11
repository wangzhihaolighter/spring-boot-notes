package com.example.springframework.boot.exception.web;

import com.example.springframework.boot.exception.config.exception.CustomErrorException;
import com.example.springframework.boot.exception.config.exception.SystemErrorException;
import com.example.springframework.boot.exception.config.response.DemoResult;
import com.example.springframework.boot.exception.config.response.ResultCodeEnum;
import com.example.springframework.boot.exception.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SimpleController {

    @GetMapping("/")
    public String home() {
        return "hello freemarker";
    }

    @GetMapping("/user")
    public DemoResult<User> getUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("飞翔的大白菜");
        user.setPassword("发呆的白菜");
        return new DemoResult<User>().success(user);
    }

    @GetMapping("/user/all")
    public DemoResult<List<User>> getUserALL() {
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setId(1L);
        user.setUsername("飞翔的大白菜");
        user.setPassword("发呆的白菜");
        users.add(user);
        return new DemoResult<List<User>>().success(users);
    }

    @GetMapping("/error/test")
    public DemoResult<Void> testError() {
        int i = 1 / 0;
        return null;
    }

    @GetMapping("/error/system")
    public DemoResult<Void> systemError() throws Exception {
        throw new SystemErrorException(ResultCodeEnum.NOT_FOUND);
    }

    @GetMapping("/error/custom")
    public DemoResult<Void> customError() throws CustomErrorException {
        throw new CustomErrorException("我错了T_T，下次再也不敢了", ResultCodeEnum.FAIL);
    }

}

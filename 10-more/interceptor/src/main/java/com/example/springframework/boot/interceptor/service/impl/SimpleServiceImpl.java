package com.example.springframework.boot.interceptor.service.impl;

import com.example.springframework.boot.interceptor.entity.User;
import com.example.springframework.boot.interceptor.service.SimpleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Random;

@Slf4j
@Service
public class SimpleServiceImpl implements SimpleService {
    @Override
    public void login(String username, String password) {
        //模拟登陆
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpSession session = request.getSession();
        User user = new User();
        user.setId(new Random().nextLong());
        user.setUsername(username);
        user.setPassword(password);
        session.setAttribute("sessionUser", user);
        log.info("用户:" + username + "登陆了");
    }

    @Override
    public void logout() {
        //模拟登出
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpSession session = request.getSession();
        session.removeAttribute("sessionUser");
        log.info("用户登出了");
    }

    @Override
    public void doSomething() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("----- do something -----");
    }
}

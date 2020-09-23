package com.example.springframework.boot.aop.web;

import com.example.springframework.boot.aop.interceptor.Classified;
import com.example.springframework.boot.aop.service.SimpleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SimpleController {

    @Autowired
    private SimpleService simpleService;

    @GetMapping("/")
    public String home() {
        return "hello aop";
    }

    @GetMapping("/do")
    public void doSomeService() {
        simpleService.doServiceOne();
        simpleService.doServiceTwo();
        simpleService.doServiceThree();
    }

    @GetMapping("/auth/do")
    public void authDoSomething() {
        log.info("执行一些业务方法");
    }

    @Classified
    @GetMapping("/classified")
    public void classified() {
        log.info("方法机密，不允许访问");
    }

    @Classified(false)
    @GetMapping("/unclassified")
    public void unclassified() {
        log.info("方法不是机密，允许访问");
    }

    /**
     * 异常捕获处理
     */
    @ExceptionHandler(value = Exception.class)
    public String defaultErrorHandler(Exception e) {
        e.printStackTrace();
        return e.getMessage();
    }

}

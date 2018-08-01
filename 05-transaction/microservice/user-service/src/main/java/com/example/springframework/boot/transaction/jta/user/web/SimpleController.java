package com.example.springframework.boot.transaction.jta.user.web;

import com.example.springframework.boot.transaction.jta.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SimpleController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home() {
        return "hello user service";
    }

    /* read */

    @GetMapping("/user/all")
    public void findAll() {
        userService.findAll();
    }

    /* write */

    @PostMapping("/user/save/1")
    public void saveSuccess() {
        userService.saveSuccess();
    }

    @PostMapping("/user/save/2")
    public void saveFailure() {
        userService.saveFailure();
    }

    @PostMapping("/user/save/3")
    public void saveSuccessTransactional() {
        userService.saveSuccessTransactional();
    }

    @PostMapping("/user/save/4")
    public void saveFailureTransactional() {
        userService.saveFailureTransactional();
    }

    @DeleteMapping("/user/clean")
    public void clean() {
        userService.clean();
    }

    /* exception handler */

    @ExceptionHandler(value = Exception.class)
    public String defaultErrorHandler(Exception e) {
        e.printStackTrace();
        return e.getMessage();
    }

}

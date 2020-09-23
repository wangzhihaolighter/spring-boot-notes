package com.example.springframework.boot.aop.transaction.web;

import com.example.springframework.boot.aop.transaction.entity.User;
import com.example.springframework.boot.aop.transaction.exception.OtherException;
import com.example.springframework.boot.aop.transaction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SimpleController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home() {
        return "hello aop transaction";
    }

    /* ----- read -----*/

    @GetMapping("/user/query/all")
    public List<User> queryAll() {
        return userService.queryAll();
    }

    @GetMapping("/user/query/{id}")
    public User queryById(@PathVariable("id") Long id) {
        return userService.queryById(id);
    }

    /* ----- write -----*/

    @PostMapping("/user/insert")
    public Long insert(@RequestBody User user) throws OtherException {
        return userService.insert(user);
    }

    @PostMapping("/user/insert/batch")
    public List<Long> insert(@RequestBody List<User> users) throws OtherException {
        return userService.insertBatch(users);
    }

    @PutMapping("/user/update")
    public Integer update(@RequestBody User user) throws OtherException {
        return userService.update(user);
    }

    @PutMapping("/user/update/batch")
    public Integer batchUpdate(@RequestBody List<User> users) throws OtherException {
        return userService.updateBatch(users);
    }

    @DeleteMapping("/user/delete/{id}")
    public Integer delete(@PathVariable("id") Long id) {
        return userService.delete(id);
    }

    @DeleteMapping("/user/delete/batch")
    public Integer batchDelete(@RequestBody List<Long> ids) {
        return userService.batchDelete(ids);
    }

    /* exception handler */

    @ExceptionHandler(value = Exception.class)
    public String defaultErrorHandler(Exception e) {
        e.printStackTrace();
        return e.getMessage();
    }

}

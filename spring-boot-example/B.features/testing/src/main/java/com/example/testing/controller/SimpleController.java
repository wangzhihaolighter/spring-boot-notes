package com.example.testing.controller;

import com.example.testing.entity.User;
import com.example.testing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SimpleController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String sayHello() {
        return "Hello,World!";
    }

    @PostMapping("/user/insert")
    public Long insert(@RequestBody User user) {
        userRepository.save(user);
        return user.getId();
    }

    @PutMapping("/user/update")
    public void update(@RequestBody User user) {
        //save方法在有主键的情况下，会进行更新
        userRepository.save(user);
    }

    @DeleteMapping("/user/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
    }

    @GetMapping("/user/query/{id}")
    public User queryById(@PathVariable("id") Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @GetMapping("/user/query/all")
    public List<User> queryAll() {
        return userRepository.findAll();
    }

    @GetMapping("/user/query")
    public User findUsersByUsernameAndPassword(@RequestParam("username") String username,
                                               @RequestParam("password") String password) {
        return userRepository.findUsersByUsernameAndPassword(username, password);
    }

    @GetMapping("/user/query/like/username")
    public List<User> queryUsersByUsernameLike(@RequestParam("username") String username) {
        return userRepository.queryUsersByUsernameLike("%" + username + "%");
    }

    @GetMapping("/user/query/all/order/username")
    public List<User> readUsersByUsernameNotNullOrderByUsername() {
        return userRepository.readUsersByUsernameNotNullOrderByUsername();
    }

    @GetMapping("/user/query/username/like/count")
    public Integer countByUsernameLike(@RequestParam("username") String username) {
        return userRepository.countByUsernameLike("%" + username + "%");
    }

    /*----------分页查询/排序查询----------*/

    @GetMapping("/user/query/page")
    public Page<User> pageQueryUsers(@RequestParam("pageNum") Integer pageNum,
                                     @RequestParam("pageSize") Integer pageSize) {
        //注意：jpa分页的页码从0开始
        return userRepository.findAll(PageRequest.of(pageNum - 1, pageSize));
    }

    @GetMapping("/user/query/page/like/username")
    public Page<User> pageQueryUsersByUsernameLike(@RequestParam("username") String username,
                                                   @RequestParam("pageNum") Integer pageNum,
                                                   @RequestParam("pageSize") Integer pageSize) {
        return userRepository.queryByUsernameLike("%" + username + "%", PageRequest.of(pageNum - 1, pageSize));
    }

    @GetMapping("/user/query/sort/like/username")
    public List<User> sortQueryUsersByUsernameLike(@RequestParam("username") String username) {
        Sort sort = Sort.by(Sort.Order.asc("username"), Sort.Order.desc("id"));
        return userRepository.queryByUsernameLike("%" + username + "%", sort);
    }

}

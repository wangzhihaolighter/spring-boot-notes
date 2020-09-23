package com.example.springframework.boot.restful.web;

import com.example.springframework.boot.restful.entity.User;
import com.example.springframework.boot.restful.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SimpleController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String home() {
        return "Hello World";
    }

    @GetMapping("/user/all")
    public List<User> getUserAll() {
        return (List<User>) userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @PostMapping("/user/save")
    public Long saveUser(@RequestBody User user) {
        return userRepository.save(user).getId();
    }

    @PostMapping("/user/delete")
    public void deleteUser(@RequestParam("id") Long id) {
        userRepository.deleteById(id);
    }

}

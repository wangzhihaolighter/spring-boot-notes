package com.example.caffeine.web;

import com.example.caffeine.entity.User;
import com.example.caffeine.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/query/all")
    public ResponseEntity<List<User>> queryAll() {
        return ResponseEntity.ok(userService.queryAll());
    }

    @GetMapping("/query/{id}")
    public ResponseEntity<User> query(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.query(id));
    }

    @PostMapping("/save")
    public ResponseEntity<User> save(@RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @PutMapping("/update")
    public ResponseEntity<User> update(@RequestBody User user) {
        return ResponseEntity.ok(userService.update(user));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<Void> deleteAll() {
        userService.deleteAll();
        return ResponseEntity.ok().build();
    }
}

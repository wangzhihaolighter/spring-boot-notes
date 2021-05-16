package com.example.mongodb.controller;

import com.example.mongodb.dao.UserRepository;
import com.example.mongodb.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user/mr")
public class UserByMongoRepositoryController {

    private final UserRepository userRepository;

    public UserByMongoRepositoryController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /* insert */

    @PostMapping("/save")
    public String save(@RequestBody User user) {
        return userRepository.save(user).getId().toHexString();
    }

    @PostMapping("/save/batch")
    public List<String> save(@RequestBody List<User> users) {
        return userRepository.saveAll(users)
                .stream()
                .map(user -> user.getId().toHexString())
                .collect(Collectors.toList());
    }

    /* select */

    @GetMapping("/query/all")
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @GetMapping("/query/{id}")
    public User findById(@PathVariable("id") String id) {
        return userRepository.findById(new ObjectId(id)).orElse(null);
    }

    @GetMapping("/query/username")
    public List<User> findByUsername(@RequestParam("username") String username) {
        return userRepository.findAllByUsername(username);
    }

    @GetMapping("/query/page")
    public Page<User> pageQuery(@RequestParam("pageNum") Integer pageNum,
                                @RequestParam("pageSize") Integer pageSize) {
        //分页页码从0开始
        return userRepository.findAll(PageRequest.of(pageNum - 1, pageSize));
    }

    @GetMapping("/query/sort")
    public List<User> sortQuery() {
        return userRepository.findAll(Sort.by(Sort.Order.desc("id")));
    }

    /* update */

    @PutMapping("/update")
    public User update(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("/update/batch")
    public List<User> batchUpdate(@RequestBody List<User> users) {
        return userRepository.saveAll(users);
    }

    /* delete */

    @DeleteMapping("/delete")
    public void delete(@RequestParam("id") String id) {
        userRepository.deleteById(new ObjectId(id));
    }

    @DeleteMapping("/delete/batch")
    public void batchDelete(@RequestBody List<String> ids) {
        Iterable<User> users = userRepository.findAllById(ids.stream().map(ObjectId::new).collect(Collectors.toList()));
        userRepository.deleteAll(users);
    }

}

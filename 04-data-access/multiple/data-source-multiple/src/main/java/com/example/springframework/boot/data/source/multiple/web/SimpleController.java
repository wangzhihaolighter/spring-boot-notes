package com.example.springframework.boot.data.source.multiple.web;

import com.example.springframework.boot.data.source.multiple.entity.cluster.People;
import com.example.springframework.boot.data.source.multiple.entity.primary.User;
import com.example.springframework.boot.data.source.multiple.mapper.cluster.PeopleMapper;
import com.example.springframework.boot.data.source.multiple.mapper.primary.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SimpleController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PeopleMapper peopleMapper;

    @GetMapping("/")
    public String home(){
        return "hello dataSource multiple";
    }

    //primary

    @GetMapping("/user/query/all")
    public List<User> queryUserAll(){
        return userMapper.selectAll();
    }

    //cluster

    @GetMapping("/people/query/all")
    public List<People> queryPeopleAll(){
        return peopleMapper.selectAll();
    }

}

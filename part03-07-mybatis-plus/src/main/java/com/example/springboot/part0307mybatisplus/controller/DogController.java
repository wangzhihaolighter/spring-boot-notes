package com.example.springboot.part0307mybatisplus.controller;


import com.example.springboot.part0307mybatisplus.entity.Dog;
import com.example.springboot.part0307mybatisplus.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author K神带你飞123
 * @since 2018-01-19
 */
@RestController
@RequestMapping("/dog")
public class DogController {

    @Autowired
    private DogService dogService;

    @PostMapping("/insert")
    public Boolean insert(Dog dog){
        return dogService.insert(dog);
    }

    @GetMapping("/query/all")
    public List<Dog> queryAll(){
        return dogService.selectList(null);
    }

}


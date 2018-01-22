package com.example.springboot.part0307mybatisplus.controller;


import com.example.springboot.part0307mybatisplus.entity.People;
import com.example.springboot.part0307mybatisplus.service.PeopleService;
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
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private PeopleService peopleService;

    @PostMapping("/insert")
    private Boolean insert(People people){
        return  peopleService.insert(people);
    }

    @GetMapping("/query/all")
    private List<People> queryAll(){
        return peopleService.selectList(null);
    }

}


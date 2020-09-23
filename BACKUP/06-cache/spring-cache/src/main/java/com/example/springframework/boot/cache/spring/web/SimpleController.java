package com.example.springframework.boot.cache.spring.web;

import com.example.springframework.boot.cache.spring.entity.Idea;
import com.example.springframework.boot.cache.spring.service.SimpleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class SimpleController {

    @Autowired
    private SimpleService simpleService;

    @GetMapping("/")
    public String home() {
        return "hello spring cache";
    }

    @GetMapping("/all")
    public List<Idea> queryAll() {
        return simpleService.queryAll();
    }

    @DeleteMapping("/clean")
    public void clean() {
        simpleService.deleteAll();
    }

    @GetMapping("/query")
    public Idea query(@RequestParam("id") Long id) {
        return simpleService.query(id);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam("id") Long id) {
        simpleService.delete(id);
    }

    @PostMapping("/save")
    public Idea insert() {
        Idea idea = new Idea();
        idea.setName("今天也是高兴的一天");
        idea.setDescription("我有一只小毛驴我从来都不骑，有一天我心血来潮骑着去赶集");
        return simpleService.insert(idea);
    }

}

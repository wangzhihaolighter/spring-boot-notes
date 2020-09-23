package com.example.springframework.boot.people.web;

import com.example.springframework.boot.people.entity.People;
import com.example.springframework.boot.people.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SimpleController {

    @Autowired
    private PeopleService peopleService;

    @GetMapping("/")
    public String home() {
        return "hello people service";
    }

    /* read */

    @GetMapping("/people/all")
    public List<People> findAll() {
        return peopleService.findAll();
    }

    /* write */

    @PostMapping("/people/save/1")
    public void saveSuccess() {
        peopleService.saveSuccess();
    }

    @PostMapping("/people/save/3")
    public void saveSuccessTransactional() {
        peopleService.saveSuccessTransactional();
    }

    @DeleteMapping("/people/delete")
    void clean(){
        peopleService.clean();
    };

    /* exception handler */

    @ExceptionHandler(value = Exception.class)
    public String defaultErrorHandler(Exception e) {
        e.printStackTrace();
        return e.getMessage();
    }

}

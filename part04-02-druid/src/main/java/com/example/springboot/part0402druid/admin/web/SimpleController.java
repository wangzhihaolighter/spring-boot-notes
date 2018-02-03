package com.example.springboot.part0402druid.admin.web;

import com.example.springboot.part0402druid.admin.entity.Dog;
import com.example.springboot.part0402druid.admin.entity.People;
import com.example.springboot.part0402druid.admin.entity.User;
import com.example.springboot.part0402druid.admin.service.SimpleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SimpleController {

    @Autowired
    private SimpleService simpleService;

    @RequestMapping("/")
    public Object simple() {
        Map<String, Object> result = new HashMap<>();
        setData(result, "hi,my name is James");
        return result;
    }

    @GetMapping("query")
    public Object qury() {
        Map<String, Object> result = new HashMap<>();
        Map<String,Object> data = new HashMap<>();
        List<Dog> dogs = simpleService.queryDogs();
        List<People> peoples = simpleService.queryPeople();
        List<User> users = simpleService.queryUser();
        data.put("dogs",dogs);
        data.put("peoples",peoples);
        data.put("users",users);
        setData(result,data);
        return result;
    }

    private void setData(Map<String, Object> result, Object data) {
        result.put("message", "success");
        result.put("result", data);
    }

    private void setError(Map<String, Object> result, Object error) {
        result.put("message", "fail");
        result.put("result", error);
    }

}

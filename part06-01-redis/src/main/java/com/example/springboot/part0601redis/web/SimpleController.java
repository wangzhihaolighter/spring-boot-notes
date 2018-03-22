package com.example.springboot.part0601redis.web;

import com.example.springboot.part0601redis.dao.CityRepository;
import com.example.springboot.part0601redis.dao.UserRepository;
import com.example.springboot.part0601redis.entity.City;
import com.example.springboot.part0601redis.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CityRepository cityRepository;

    @PostMapping("/user/save")
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @PostMapping("/city/save")
    public City saveCity(City city) {
        return cityRepository.save(city);
    }

    @GetMapping("/user/query/name")
    public User findUserByName(String name) {
        return userRepository.findByUsername(name);
    }

    @GetMapping("/city/query/name")
    public City findCityByName(String name) {
        return cityRepository.findByName(name);
    }

    @PostMapping("/user/delete/{username}")
    public void deleteUserByUsername(@PathVariable("username") String username) {
        userRepository.deleteByUsername(username);
    }

    @PostMapping("/city/delete/{name}")
    public void deleteCityByName(@PathVariable("name") String name) {
        cityRepository.deleteByName(name);
    }
}

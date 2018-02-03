package com.example.springboot.part0402druid.admin.service;

import com.example.springboot.part0402druid.admin.entity.Dog;
import com.example.springboot.part0402druid.admin.entity.People;
import com.example.springboot.part0402druid.admin.entity.User;

import java.util.List;

public interface SimpleService {
    List<Dog> queryDogs();

    List<People> queryPeople();

    List<User> queryUser();
}

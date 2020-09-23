package com.example.springframework.boot.people.mapper;

import com.example.springframework.boot.people.entity.People;

import java.util.List;

public interface PeopleMapper {
    List<People> queryAll();

    void insert(People user);

    void clean();
}
package com.example.springframework.boot.people.service;

import com.example.springframework.boot.people.entity.People;
import com.example.springframework.boot.people.mapper.PeopleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class PeopleService {
    @Autowired
    private PeopleMapper peopleMapper;

    public List<People> findAll() {
        return peopleMapper.queryAll();
    }

    public void saveSuccess() {
        People people = new People();
        people.setName("华安");
        people.setPassword("我为秋香");
        peopleMapper.insert(people);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveSuccessTransactional() {
        People people = new People();
        people.setName("至尊宝");
        people.setPassword("紫霞");
        peopleMapper.insert(people);
    }

    public void clean() {
        peopleMapper.clean();
    }
}

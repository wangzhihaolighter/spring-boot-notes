//package com.example.springboot.part0308mybatismultipledatasources;
//
//import com.example.springboot.part0308mybatismultipledatasources.mapper.primary.DogMapper;
//import com.example.springboot.part0308mybatismultipledatasources.mapper.primary.PeopleMapper;
//import com.example.springboot.part0308mybatismultipledatasources.mapper.second.UserMapper;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class Part0308MybatisMultipleDatasourcesApplicationTests {
//
//    @Autowired
//    private UserMapper userMapper;
//
//    @Autowired
//    private PeopleMapper peopleMapper;
//
//    @Autowired
//    private DogMapper dogMapper;
//
//    @Test
//    public void test() {
//        System.out.println(userMapper.selectByPrimaryKey(1L));
//        System.out.println(peopleMapper.selectByPrimaryKey(1L));
//        System.out.println(dogMapper.selectByPrimaryKey(1L));
//    }
//
//}

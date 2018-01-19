package com.example.springboot.part0305mybatismapperpagehelper;

import com.example.springboot.part0305mybatismapperpagehelper.mapper.DogMapper;
import com.example.springboot.part0305mybatismapperpagehelper.mapper.PeopleMapper;
import com.example.springboot.part0305mybatismapperpagehelper.model.Dog;
import com.example.springboot.part0305mybatismapperpagehelper.model.People;
import com.github.pagehelper.PageHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Part0305MybatisMapperPagehelperApplicationTests {

    @Autowired
    private PeopleMapper peopleMapper;

    @Autowired
    private DogMapper dogMapper;

    @Test
    public void testPeople() {
        //插入10条数据
        List<People> peopleList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            People people = new People();
            people.setName("白菜" + i);
            people.setAge(i);
            people.setDescription("飞翔的大白菜");
            peopleList.add(people);
        }
        peopleMapper.insertList(peopleList);
        //分页插件配置分页属性
        PageHelper.startPage(1, 5);
        //除了分页，还可以设置其他,如：排序。当然，这些条件也可以在自己的sql语句中写
        PageHelper.orderBy("age DESC");
        //查询所有，但会被分页插件分页
        List<People> peoplePageQueryList = peopleMapper.selectAll();
        for (People people : peoplePageQueryList) {
            System.out.println(people);
        }
    }

    @Test
    public void testDog() {
        //新增数据
        List<Dog> dogList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Dog dog = new Dog();
            dog.setName("八公" + i);
            dog.setAge(i);
            dog.setDescription("忠犬八公");
            dogList.add(dog);
        }
        dogMapper.insertList(dogList);
        //分页查询
        PageHelper.startPage(1, 3);
        PageHelper.orderBy("id ASC");
        Dog conditionDog = new Dog();
        conditionDog.setDescription("忠犬八公");
        List<Dog> dogPageQueryList = dogMapper.select(conditionDog);
        for (Dog dog : dogPageQueryList) {
            System.out.println(dog);
        }
    }

}

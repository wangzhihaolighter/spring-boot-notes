package com.example.mybatis.part0302mybatis;

import com.example.mybatis.part0302mybatis.entity.Dog;
import com.example.mybatis.part0302mybatis.entity.People;
import com.example.mybatis.part0302mybatis.mapper.DogMapper;
import com.example.mybatis.part0302mybatis.mapper.PeopleMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Part0302MybatisApplicationTests {

	@Autowired
	private DogMapper dogMapper;

	@Autowired
	private PeopleMapper peopleMapper;

	//最基础的mybatis的xml方式

	@Test
	public void findDogByName() {
		Dog dog = new Dog();
		dog.setName("旺财");
		dog.setAge(2);
		dog.setDescription("聪明又可爱");
		dogMapper.insert(dog);
		System.out.println(dogMapper.findByName("旺财"));
	}

	//注解方式

	@Test
	public void findPeopleByName() {
		People people = new People();
		people.setName("007");
		people.setAge(33);
		people.setDescription("特工");
		peopleMapper.insert(people);
		System.out.println(peopleMapper.findByName("007"));
	}

	@Test
	public void findPeopleByid() {
		Long id = 1L;
		System.out.println(peopleMapper.findById(id));
	}

	@Test
	public void findAll() {
		System.out.println(peopleMapper.findAll());
	}

}

package com.example.springboot.part0303jdbctemplate;

import com.example.springboot.part0303jdbctemplate.dao.PeopleDao;
import com.example.springboot.part0303jdbctemplate.entity.People;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Part0303JdbctemplateApplicationTests {

	@Autowired
	private PeopleDao peopleDao;

	@Test
	public void test() {
		People people = new People();
		people.setName("黄飞鸿");
		people.setAge(22);
		people.setDescription("一代宗师");
		Long id = peopleDao.insert(people);
		System.out.println(peopleDao.findById(id));
	}

}

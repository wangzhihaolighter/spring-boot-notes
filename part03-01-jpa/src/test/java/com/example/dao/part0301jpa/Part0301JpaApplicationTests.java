package com.example.dao.part0301jpa;

import com.example.dao.part0301jpa.dao.DogRepository;
import com.example.dao.part0301jpa.entity.Dog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Part0301JpaApplicationTests {

	@Autowired
	private DogRepository dogRepository;

	@Test
	public void test() {
		Dog dog = new Dog();
		dog.setName("旺财");
		dog.setAge(2);
		dog.setDescription("忠诚、可爱");
		dogRepository.save(dog);
		System.out.println(dogRepository.findByName("旺财"));
	}

}

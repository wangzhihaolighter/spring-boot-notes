package com.example.springboot.part0404ldap;

import com.example.springboot.part0404ldap.entity.Person;
import com.example.springboot.part0404ldap.repository.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Part0404LdapApplicationTests {

	@Autowired
	private PersonRepository personRepository;

	@Test
	public void findAll() {
		personRepository.findAll().forEach(System.out::println);
	}

	@Test
	public void save(){
		Person person = new Person();
		person.setUid("uid:1");
		person.setSuerName("AAA");
		person.setCommonName("aaa");
		person.setUserPassword("123456");
		personRepository.save(person);
	}

}

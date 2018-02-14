package com.example.springboot.part0404ldap.repository;

import com.example.springboot.part0404ldap.entity.Person;
import org.springframework.data.repository.CrudRepository;

import javax.naming.Name;

public interface PersonRepository extends CrudRepository<Person, Name> {
}
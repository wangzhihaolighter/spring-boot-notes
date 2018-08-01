package com.example.springframework.boot.ldap;

import com.example.springframework.boot.ldap.entity.Person;
import com.example.springframework.boot.ldap.repository.PersonRepo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class LdapApplicationTests {

    /*
    LDAP简称对应:
        o：organization（组织-公司）
        ou：organization unit（组织单元-部门）
        c：countryName（国家）
        dc：domainComponent（域名）
        sn：surname（姓氏）
        cn：common name（常用名称）
     */

    @Autowired
    private LdapTemplate ldapTemplate;

    @Autowired
    private PersonRepo personRepo;

    @Test
    public void findAll() {
        personRepo.findAll().forEach(System.out::println);
    }

    @Test
    public void searchAllPersonAttr() {
        personRepo.searchAllPersonAttr("cn").forEach(System.out::println);
    }

    @Test
    public void findPersonByDn() {
        String dn = "uid=example_uid,ou=engineering,dc=example,dc=com";
        System.out.println(personRepo.findPersonByDn(dn));
    }

    @Test
    public void findPersonsByCn() {
        personRepo.findPersonsByCn("engineer_commonName").forEach(System.out::println);
    }

    @Test
    public void createUser() {
        Person person = new Person();
        //ou
        person.setCompany("engineering");
        person.setUid(UUID.randomUUID().toString());
        person.setCommonName("test_cn");
        person.setSurname("test_sn");
        person.setDescription("xxx");
        personRepo.createUser(person);
        findAll();
    }

    @Test
    public void updateUser() {
        Person person = new Person();
        //ou
        person.setCompany("engineering");
        person.setUid("example_uid");
        person.setCommonName("test_cn");
        person.setSurname("test_sn");
        person.setDescription("xxx");
        personRepo.updateUser(person);
        findAll();
    }

    @Test
    public void deleteUser() {
        Person person = new Person();
        //ou
        person.setCompany("engineering");
        person.setUid("example_uid");
        personRepo.deleteUser(person);
        findAll();
    }
}

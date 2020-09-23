package com.example.springframework.boot.ldap.entity;

import lombok.Data;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.DnAttribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;

@Data
@Entry(base = "dc=example,dc=com", objectClasses = "inetOrgPerson")
public final class Person {
    @Id
    private Name id;
    @DnAttribute(value = "ou", index = 2)
    private String company;
    /**
     * 必须为3，index是由定义决定的,index=1->dc index=2->ou
     */
    @DnAttribute(value = "uid", index = 3)
    private String uid;
    @Attribute(name = "cn")
    private String commonName;
    @Attribute(name = "sn")
    private String surname;
    private String description;
}
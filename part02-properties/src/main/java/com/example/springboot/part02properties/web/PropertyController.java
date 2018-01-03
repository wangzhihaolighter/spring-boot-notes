package com.example.springboot.part02properties.web;

import com.example.springboot.part02properties.domain.Dog;
import com.example.springboot.part02properties.domain.People;
import com.example.springboot.part02properties.property.DogProperties;
import com.example.springboot.part02properties.property.PeopleProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 *
 * @author zhihao.wang
 * @date 2018/1/3
 */
@RestController
@RequestMapping("/property")
public class PropertyController {
    @Autowired
    private PeopleProperties peopleProperties;

    @Autowired
    private DogProperties dogProperties;

    @GetMapping("people")
    public People getPeople(){
        People people = new People();
        people.setName(peopleProperties.getName());
        people.setAge(peopleProperties.getAge());
        people.setDescription(peopleProperties.getDescription());
        return people;
    }

    @GetMapping("dog")
    public Dog getDog(){
        Dog dog = new Dog();
        dog.setName(dogProperties.getName());
        dog.setAge(dogProperties.getAge());
        dog.setDescription(dogProperties.getDescription());
        return dog;
    }

}

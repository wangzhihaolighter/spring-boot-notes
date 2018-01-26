package com.example.springboot.part0402druid.admin.web;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.springboot.part0402druid.admin.entity.primary.Dog;
import com.example.springboot.part0402druid.admin.entity.primary.People;
import com.example.springboot.part0402druid.admin.entity.second.User;
import com.example.springboot.part0402druid.admin.service.DogService;
import com.example.springboot.part0402druid.admin.service.PeopleService;
import com.example.springboot.part0402druid.admin.service.UserService;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 *
 * @author zhihao.wang
 * @date 2018/1/26
 */
@RestController
public class SimpleController {

    @Autowired
    private UserService userService;

    @Autowired
    private PeopleService peopleService;

    @Autowired
    private DogService dogService;

    @PostMapping("add")
    public Map<String, Object> add() {
        Map<String, Object> result = new HashMap<>(2);
        try {
            //用户
            User user = new User();
            user.setName("吕小布");
            byte[] bytes = "123456".getBytes();
            user.setPassword(MD5Encoder.encode(bytes));
            user.setDescription("拒绝者");
            userService.insert(user);

            //人
            People people = new People();
            people.setName("张大炮");
            people.setAge(27);
            people.setDescription("尔康、snake、小龙虾");
            peopleService.insert(people);

            //狗
            Dog dog = new Dog();
            dog.setName("八公");
            dog.setAge(8);
            dog.setDescription("忠犬");
            dogService.insert(dog);

            setData(result, null);
        } catch (Exception e) {
            e.printStackTrace();
            setError(result, e.getMessage());
        }
        return result;
    }

    @GetMapping("query/user/page")
    public Map<String, Object> queryUser(Integer pageNum, Integer pageSize) {
        Map<String, Object> result = new HashMap<>(2);
        try {
            Page<User> page = new Page<>(pageNum, pageSize);
            Page<User> userPage = userService.selectPage(page);
            setData(result, userPage);
        } catch (Exception e) {
            e.printStackTrace();
            setError(result, e.getMessage());
        }
        return result;
    }

    @GetMapping("query/people/page")
    public Map<String, Object> queryPeople(Integer pageNum, Integer pageSize) {
        Map<String, Object> result = new HashMap<>(2);
        try {
            Page<People> page = new Page<>(pageNum, pageSize);
            Page<People> data = peopleService.selectPage(page);
            setData(result, data);
        } catch (Exception e) {
            e.printStackTrace();
            setError(result, e.getMessage());
        }
        return result;
    }

    @GetMapping("query/dog/page")
    public Map<String, Object> queryDog(Integer pageNum, Integer pageSize) {
        Map<String, Object> result = new HashMap<>(2);
        try {
            Page<Dog> page = new Page<>(pageNum, pageSize);
            Page<Dog> data = dogService.selectPage(page);
            setData(result, data);
        } catch (Exception e) {
            e.printStackTrace();
            setError(result, e.getMessage());
        }
        return result;
    }

    private void setData(Map<String, Object> result, Object data) {
        result.put("message", "success");
        result.put("result", data);
    }

    private void setError(Map<String, Object> result, Object error) {
        result.put("message", "fail");
        result.put("result", error);
    }

}

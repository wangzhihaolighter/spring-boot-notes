package com.example.springboot.part0502thymeleaf.web;

import com.example.springboot.part0502thymeleaf.entity.Dog;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Description:
 *
 * @author zhihao.wang
 * @date 2018/2/14
 */
@Controller
public class SimpleController {

    @GetMapping("/")
    @ResponseBody
    public String hello() {
        return "hello";
    }

    @GetMapping("/dog")
    public String dog(ModelMap modelMap) {
        Dog dog = new Dog();
        dog.setId(1L);
        dog.setName("旺财");
        dog.setDescription("狗年行大运，财源滚滚来");
        modelMap.put("dog", dog);
        return "dog";
    }

    @GetMapping("/error")
    public String error() throws Exception {
        throw new Exception("抱歉出错了");
    }

}

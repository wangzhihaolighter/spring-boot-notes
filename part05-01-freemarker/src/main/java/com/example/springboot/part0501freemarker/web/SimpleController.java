package com.example.springboot.part0501freemarker.web;

import com.example.springboot.part0501freemarker.entity.People;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public String hello() {
        return "hello";
    }

    @GetMapping("/people")
    public String people(ModelMap modelMap) {
        People people = new People();
        people.setId(9527L);
        people.setName("华安");
        people.setDescription("尘世迷途小书童");
        modelMap.put("people",people);
        return "people";
    }

    @GetMapping("/error")
    public String error() throws Exception {
        throw new Exception("抱歉出错了");
    }

}

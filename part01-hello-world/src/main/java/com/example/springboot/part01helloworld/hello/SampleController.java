package com.example.springboot.part01helloworld.hello;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@EnableAutoConfiguration
public class SampleController {

    @RequestMapping("/")
    @ResponseBody
    public String home() {
        return "Hello World!";
    }

    @GetMapping("/say")
    @ResponseBody
    public String say(@RequestParam String content) {
        return content;
    }

    @PostMapping("/change")
    @ResponseBody
    public Map<String,Object> change(@RequestBody List<String> list) {
        Map<String,Object> resultMap =  new HashMap<>();
        int i = 1;
        for (String str : list ){
            resultMap.put("part"+i++,str);
        }
        return resultMap;
    }
}
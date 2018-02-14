package com.example.springboot.part0501freemarker.web.restful;

import com.example.springboot.part0501freemarker.entity.People;
import com.example.springboot.part0501freemarker.response.SysResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 *
 * @author zhihao.wang
 * @date 2018/2/14
 */
@RestController
@RequestMapping("people")
public class PeopleController {

    @GetMapping("get/{flag}")
    public SysResult get(@PathVariable("flag") boolean flag){
        SysResult result = new SysResult();
        if(flag){
            People people = new People();
            people.setId(9527L);
            people.setName("华安");
            people.setDescription("尘世迷途小书童");
            result.success(result,people);
        }else{
            result.fail(result,"系统出错");
        }
        return result;
    }

}

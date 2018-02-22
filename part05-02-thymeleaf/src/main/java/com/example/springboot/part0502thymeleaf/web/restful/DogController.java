package com.example.springboot.part0502thymeleaf.web.restful;

import com.example.springboot.part0502thymeleaf.entity.Dog;
import com.example.springboot.part0502thymeleaf.response.SysResult;
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
@RequestMapping("dog")
public class DogController {

    @GetMapping("get/{flag}")
    public SysResult get(@PathVariable("flag") boolean flag) {
        SysResult result = new SysResult();
        if (flag) {
            Dog dog = new Dog();
            dog.setId(1L);
            dog.setName("旺财");
            dog.setDescription("狗年行大运，财源滚滚来");
            result.success(result, dog);
        } else {
            result.fail(result, "系统出错");
        }
        return result;
    }

}

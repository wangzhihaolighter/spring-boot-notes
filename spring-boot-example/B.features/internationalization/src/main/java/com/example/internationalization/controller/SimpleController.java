package com.example.internationalization.controller;

import com.example.internationalization.exception.BusinessException;
import com.example.internationalization.response.ApiResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SimpleController {

    @GetMapping("/")
    public String sayHello() {
        return "Hello,World!";
    }

    @GetMapping("/success")
    public ApiResult<String> success() {
        return ApiResult.success("success");
    }

    @GetMapping("/fail")
    public void fail(@RequestParam(value = "code", required = false, defaultValue = "99") String code,
                     @RequestParam(value = "content", required = false, defaultValue = "错了吧，说点啥吧") List<String> contentList) {
        BusinessException.throwMessage(code, contentList.toArray());
    }

}

package com.example.controller;

import com.example.config.exception.BusinessException;
import com.example.config.response.ApiResult;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

  @GetMapping("/")
  public String hello() {
    return "Hello,World!";
  }

  @GetMapping("/success")
  public ApiResult<String> success() {
    return ApiResult.success();
  }

  @GetMapping("/fail")
  public void fail(
      @RequestParam(value = "code", required = false, defaultValue = "99") String code,
      @RequestParam(value = "content", required = false, defaultValue = "系统出错")
          List<String> contentList) {
    BusinessException.throwMessage(code, contentList.toArray());
  }
}

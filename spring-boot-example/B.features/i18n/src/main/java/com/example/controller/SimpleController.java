package com.example.controller;

import com.example.core.exception.BusinessException;
import com.example.core.response.R;
import com.example.core.response.ResultCodeEnum;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@Validated
@RestController
public class SimpleController {

  @GetMapping("/")
  public String hello() {
    return "Hello,World!";
  }

  @GetMapping("/success")
  public R<Void> success() {
    return R.success();
  }

  @GetMapping("/fail")
  public void fail() {
    BusinessException.throwMessage(ResultCodeEnum.FAIL);
  }

  @GetMapping("/valid")
  public R<String> valid(@NotBlank String name) {
    return R.success(name);
  }
}

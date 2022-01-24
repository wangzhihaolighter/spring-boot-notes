package com.example.spring.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 不同请求方式示例控制器
 *
 * @author wangzhihao
 */
@Slf4j
@RestController
@RequestMapping("/method")
public class RequestMethodExampleController {
  /*
   HTTP请求方式：GET HEAD POST PUT PATCH DELETE OPTIONS TRACE
   GET：请求指定的页面信息，并返回实体主体。
   HEAD：只请求页面的首部。
   POST：请求服务器接受所指定的文档作为对所标识的URI的新的从属实体。
   PUT：从客户端向服务器传送的数据取代指定的文档的内容。
   PATCH：实体中包含一个表，表中说明与该URI所表示的原内容的区别。
   DELETE：请求服务器删除指定的页面。
   OPTIONS：允许客户端查看服务器的性能。
   TRACE：请求服务器在响应中的实体主体部分返回所得到的内容。
   */

  @GetMapping("/get")
  public String get() {
    return "get请求(简化写法)";
  }

  @PostMapping("/post")
  public String post() {
    return "post请求(简化写法)";
  }

  @PutMapping("/put")
  public String put() {
    return "put请求(简化写法)";
  }

  @DeleteMapping("/delete")
  public String delete() {
    return "delete请求(简化写法)";
  }

  @PatchMapping("/patch")
  public String patch() {
    return "patch请求(简化写法)";
  }

  @RequestMapping(value = "/head", method = RequestMethod.HEAD)
  public void head() {
    log.info("head请求");
  }

  @RequestMapping(value = "/options", method = RequestMethod.OPTIONS)
  public void options() {
    log.info("options请求");
  }

  @RequestMapping(value = "/trace", method = RequestMethod.TRACE)
  public void trace() {
    log.info("trace请求");
  }
}

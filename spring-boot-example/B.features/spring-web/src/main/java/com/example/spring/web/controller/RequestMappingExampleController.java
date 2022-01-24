package com.example.spring.web.controller;

import com.example.spring.web.domain.User;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 请求映射示例控制器
 *
 * @author wangzhihao
 */
@RestController
@RequestMapping("/mapping")
public class RequestMappingExampleController {
  /*
   * 1.标准URL应谁
   * 2.Ant风格映射(通配符：？，*，**)
   * 3.占位符
   * 4.限定请求方法的映射
   * 5.限定参数的映射
   */

  /** 1.标准URL映射 */
  @RequestMapping("/basic")
  public String basic() {
    return "basic";
  }

  /**
   * 2.Ant风格映射(通配符：？，*，**)
   *
   * <p>?：通配一个字符
   *
   * <p>*：通配0个或者多个字符
   *
   * <p>**：通配0个或者多个路径
   */
  @RequestMapping("/ant/?")
  public String ant1() {
    return "ant ?";
  }

  @RequestMapping("/ant/*")
  public String ant2() {
    return "ant *";
  }

  @RequestMapping("/ant/**")
  public String ant3() {
    return "ant **";
  }

  /** 3.占位符 通过@PathVariable获取占位符的值 */
  @RequestMapping("/placeholder/{name}")
  public String placeholder(@PathVariable("name") String name) {
    return String.format("占位符方式 - name:%s", name);
  }

  /** 4.限定请求方法的映射,指定method即限定 */
  @RequestMapping(value = "/restrict/method", method = RequestMethod.PUT)
  public String restrictRequestMethod() {
    return "限定请求方式";
  }

  /**
   * 5.限定参数的映射
   *
   * <p>@RequestParam获取请求参数
   *
   * <p>params=”name”：请求参数中必须带有name
   *
   * <p>params=”!name”：请求参数中不能包含name
   *
   * <p>params=”name=1”：请求参数中name必须为1
   *
   * <p>params=”name!=1”：请求参数中name必须不为1，参数中可以不包含name
   *
   * <p>params={“id”, ”name”}：请求参数中必须有id，name参数
   */
  @RequestMapping(
      value = "/restrict/param",
      params = {"id=1", "name", "name!=admin", "!age"})
  public String restrictRequestParam(
      @RequestParam("id") String id, @RequestParam("name") String name) {
    return "限定参数的映射：id=" + id + "，name=" + name;
  }

  /**
   * 5.限定请求头的映射
   *
   * <p>@RequestHeader获取请求头参数
   *
   * <p>headers=”name”：请求头中必须带有name
   *
   * <p>headers=”!name”：请求头中不能包含name
   *
   * <p>headers=”name=1”：请求头中name必须为1
   *
   * <p>headers=”name!=1”：请求头中name必须不为1，参数中可以不包含name
   *
   * <p>headers={“id”, ”name”}：请求头中必须有id，name参数
   */
  @RequestMapping(
      value = "/restrict/request/head",
      headers = {"ACCESS-TOKEN", "ACCESS-TOKEN!=0", "SCOPE=read"})
  public String restrictRequestHead(
      @RequestHeader("ACCESS-TOKEN") String token, @RequestHeader("SCOPE") String scope) {
    return String.format("限定请求头的映射，token=%s、scope=%s", token, scope);
  }

  /** 6.限制请求接收数据格式（Content-Type） */
  @RequestMapping(
      value = "/restrict/request/media/text",
      consumes = {MediaType.TEXT_PLAIN_VALUE})
  public String restrictRequestMediaTypeText() {
    return "限制请求接收数据格式：text/plain";
  }

  @RequestMapping(
      value = "/restrict/request/media/json",
      consumes = {MediaType.APPLICATION_JSON_VALUE})
  public String restrictRequestMediaTypeJson(@RequestBody User user) {
    return String.format("限制请求接收数据格式：json，user=%s", user);
  }

  /** 7.限制返回数据格式 */
  @RequestMapping(
      value = "/restrict/response/media/text",
      produces = {MediaType.TEXT_PLAIN_VALUE})
  public String restrictResponseMediaTypeText() {
    return "限制返回数据格式：text/plain";
  }

  @RequestMapping(
      value = "/restrict/response/media/json",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public String restrictResponseMediaTypeJson() {
    return "{\"message\":\"限制返回数据格式：application/json;charset=UTF-8\"}";
  }
}

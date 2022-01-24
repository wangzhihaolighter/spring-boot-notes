package com.example.spring.web.controller;

import com.example.spring.web.domain.User;
import java.util.Arrays;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** 接收数据及数据绑定示例控制器 */
@RestController
@RequestMapping("/data")
public class RequestDataExampleController {
  /*
   * 1. 接收servlet的内置对象 ：HttpServletRequest HttpServletResponse HttpSession
   * 2. 接收占位符请求路径中的参数 @PathVariable(“xxx”)
   * 3. 接收普通的请求参数 @RequestParam(value required defaultValue)
   * 4. 获取cookie参数 request.getCookies @CookieValue(“userName”)
   * 5. 基本数据类型的绑定
   * 6. Pojo对象的绑定
   * 7. 集合数据的绑定
   */

  /** 1. 接收servlet的内置对象 ：HttpServletRequest HttpServletResponse HttpSession */
  @RequestMapping("/servlet")
  public String servlet(
      HttpServletRequest request, HttpServletResponse response, HttpSession session) {
    return String.format(
        "接收servlet的内置对象：HttpServletRequest = %s，HttpServletResponse = %s，HttpSession = %s",
        request, response, session);
  }

  /** 2. 接收占位符请求路径中的参数 @PathVariable(“xxx”) */
  @RequestMapping("/{name}/BV{id}")
  public String placeholder(@PathVariable("name") String name, @PathVariable("id") String id) {
    return String.format("接收占位符请求路径中的参数，name=%s,id=%s,", id, name);
  }

  /** 3. 接收普通的请求参数 @RequestParam(value required defaultValue) */
  @RequestMapping("/request/param")
  public String requestParam(@RequestParam("id") String id) {
    return String.format("接收普通的请求参数，id=%s", id);
  }

  /** 4. 获取cookie参数 request.getCookies @CookieValue(“userName”) */
  @RequestMapping("/cookie")
  public String cookie(@CookieValue("JSESSIONID") String jSessionId) {
    return String.format("获取cookie参数，JSESSIONID=%s", jSessionId);
  }

  /** 5. 基本数据类型的绑定 */
  @RequestMapping("/basic")
  public String basic(
      @RequestParam("id") Long id,
      @RequestParam("name") String name,
      @RequestParam("age") Integer age,
      @RequestParam("score") Double score,
      @RequestParam("birthday") Date birthday,
      @RequestParam("flag") Boolean flag,
      @RequestParam("interests") String[] interests) {
    return String.format(
        "基本数据类型的绑定，id=%d、name=%s、age=%d、score=%s、birthday=%s、flag=%s、interests=%s",
        id, name, age, score, birthday, flag, Arrays.toString(interests));
  }

  /** 6.Pojo对象的绑定 */
  @RequestMapping("/pojo/param")
  public String pojoParam(User user) {
    return String.format("Pojo对象的绑定(param)，user=%s", user);
  }

  @RequestMapping("/pojo/body")
  public String pojoBody(@RequestBody(required = false) User user) {
    return String.format("Pojo对象的绑定(body)，user=%s", user);
  }

  /** 7. 集合数据的绑定 */
  @RequestMapping("/list/param")
  public String listParam(@RequestParam("id") String[] ids) {
    return "集合的绑定(param)，ids=" + Arrays.toString(ids);
  }

  @RequestMapping("/list/body")
  public String listBody(@RequestBody(required = false) String[] ids) {
    return "集合的绑定(body)，ids=" + Arrays.toString(ids);
  }
}

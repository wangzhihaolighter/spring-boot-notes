package com.example.spring.web.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 返回数据示例控制器
 *
 * @author wangzhihao
 */
@Controller
@RequestMapping("/response")
public class ResponseExampleController {

  /*
   * 返回数据：
   * 1.字符串
   * 2.页面
   * 3.转发
   * 4.重定向
   * 5.静态文件
   */

  /** 1.字符串 */
  @RequestMapping("/string")
  @ResponseBody
  public String string() {
    return "返回数据：字符串";
  }

  /**
   * 2.页面
   *
   * <p>页面默认存放地址为classpath:/templates
   */
  @RequestMapping("/page/1")
  public String page() {
    return "index";
  }

  @RequestMapping("/page/2")
  public ModelAndView page2() {
    return new ModelAndView("index");
  }

  @RequestMapping("/page/{name}")
  public String page3(@PathVariable("name") String name) {
    return name;
  }

  /** 3.转发 */
  @RequestMapping("/forward")
  public String forward() {
    return "forward:/response/string";
  }

  /** 4.重定向 */
  @RequestMapping("/redirect")
  public String redirect() {
    return "redirect:/response/string";
  }

  @RequestMapping("/redirect/2")
  public void redirect2(HttpServletResponse response) throws IOException {
    response.sendRedirect("https://www.baidu.com/");
  }

  /**
   * 5.访问静态文件
   *
   * <p>spring boot默认静态文件存放位置为static，可以直接访问 如：http://localhost:8080/test.txt
   */
  @RequestMapping("/static/test.txt")
  public String staticFile() {
    return "redirect:/test.txt";
  }
}

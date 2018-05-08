package com.example.spring.framework.boot.mvc.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 返回数据
 */
@Controller
@RequestMapping("/response")
public class responseController {

    /**
     * 返回数据：
     * 1.字符串
     * 2.页面
     * 3.转发
     * 4.重定向
     * 5.静态文件
     */

    /**
     * 1.字符串
     */
    @RequestMapping("/string")
    @ResponseBody
    public String string() {
        return "返回数据：字符串";
    }

    /**
     * 2.页面 页面默认存放地址为
     */
    @RequestMapping("/page")
    public String page() {
        return "index";
    }

    @RequestMapping("/page2")
    public ModelAndView page2() {
        return new ModelAndView("index");
    }

    @RequestMapping("/page/{pageName}")
    public String page3(@PathVariable("pageName") String pageName) {
        return pageName;
    }

    /**
     * 3.转发
     */
    @RequestMapping("/forward")
    public String forward() {
        return "forward:/response/string";
    }

    /**
     * 4.重定向
     */
    @RequestMapping("/redirect")
    public String redirect() {
        return "redirect:/response/string";
    }

    @RequestMapping("/redirect2")
    public void redirect2(HttpServletResponse response) throws IOException {
        response.sendRedirect("https://www.baidu.com/");
    }

    /**
     * 5.访问静态文件 spring boot默认静态文件存放位置为static，可以直接访问
     * 如：http://localhost:8080/demo.txt
     */

}

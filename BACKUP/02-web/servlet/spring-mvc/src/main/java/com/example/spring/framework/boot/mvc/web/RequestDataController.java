package com.example.spring.framework.boot.mvc.web;

import com.example.spring.framework.boot.mvc.domain.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;

/**
 * 接收数据及数据绑定
 */
@RestController
@RequestMapping("/data")
public class RequestDataController {

    /**
     * 1. 接收servlet的内置对象 ：HttpServletRequest HttpServletResponse HttpSession
     * 2. 接收占位符请求路径中的参数 @PathVariable(“xxx”)
     * 3. 接收普通的请求参数 @RequestParam(value required defaultValue)
     * 4. 获取cookie参数 request.getCookies @CookieValue(“userName”)
     * 5. 基本数据类型的绑定
     * 6. Pojo对象的绑定
     * 7. 集合的绑定
     */

    /**
     * 1. 接收servlet的内置对象 ：HttpServletRequest HttpServletResponse HttpSession
     */
    @RequestMapping("/servlet")
    public String servlet(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        return "接收servlet的内置对象：HttpServletRequest=" + request + ",HttpServletResponse=" + response + ",HttpSession=" + session;
    }

    /**
     * 2. 接收占位符请求路径中的参数 @PathVariable(“xxx”)
     */
    @RequestMapping("/{id}placeholder/{name}")
    public String placeholder(@PathVariable("id") String id,
                              @PathVariable("name") String name) {
        return "接收占位符请求路径中的参数,id=" + id + ",name=" + name;
    }

    /**
     * 3. 接收普通的请求参数 @RequestParam(value required defaultValue)
     */
    @RequestMapping("/request/param")
    public String requestParam(@RequestParam("id") String id,
                               @RequestParam("name") String name) {
        return "接收普通的请求参数,id=" + id + ",name=" + name;
    }

    /**
     * 4. 获取cookie参数 request.getCookies @CookieValue(“userName”)
     */
    @RequestMapping("/cookie")
    public String cookie(@CookieValue("JSESSIONID") String JSessionId) {
        return "获取cookie参数,JSESSIONID=" + JSessionId;
    }

    /**
     * 5. 基本数据类型的绑定
     */
    @RequestMapping("/basic")
    public String basic(@RequestParam("id") Long id,
                        @RequestParam("name") String name,
                        @RequestParam("age") Integer age,
                        @RequestParam("score") Double score,
                        @RequestParam("birthday") Date birthday,
                        @RequestParam("flag") Boolean flag,
                        @RequestParam("interests") String[] interests) {
        return "基本数据类型的绑定，id=" + id +
                ",name=" + name +
                ",age=" + age +
                ",score=" + score +
                ",birthday=" + birthday +
                ",flag=" + flag +
                ",interests=" + Arrays.toString(interests);
    }

    /**
     * 6.Pojo对象的绑定
     */
    @RequestMapping("/pojo/param")
    public String pojoBasic(User user) {
        return "Pojo对象的绑定(basic),user=" + user;
    }

    @RequestMapping("/pojo/json")
    public String pojoJson(@RequestBody(required = false) User user) {
        return "Pojo对象的绑定(json),user=" + user;
    }

    /**
     * 7. 集合的绑定
     */
    @RequestMapping("/list/basic")
    public String listbasic(@RequestParam("id") String[] ids) {
        return "集合的绑定(basic),ids=" + Arrays.toString(ids);
    }

    @RequestMapping("/list/json")
    public String listJson(@RequestBody(required = false) String[] ids) {
        return "集合的绑定(json),ids=" + Arrays.toString(ids);
    }

}

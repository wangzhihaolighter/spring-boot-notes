package com.example.spring.framework.boot.mvc.web;

import com.example.spring.framework.boot.mvc.domain.User;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * 映射请求
 */
@RestController
@RequestMapping("/mapping")
public class MappingController {
    /**
     * 1.标准URL应谁
     * 2.Ant风格映射(通配符：？，*，**)
     * 3.占位符
     * 4.限定请求方法的映射
     * 5.限定参数的映射
     */

    /**
     * 1.标准URL映射
     */
    @RequestMapping("/basic")
    public String basic() {
        return "basic";
    }

    /**
     * 2.Ant风格映射(通配符：？，*，**)
     * ?：通配一个字符
     * *：通配0个或者多个字符
     * **：通配0个或者多个路径
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

    /**
     * 3.占位符
     * 通过@PathVariable获取占位符的值
     */
    @RequestMapping("/{service}/placeholder/{name}")
    public String placeholder(@PathVariable("service") String service,
                              @PathVariable("name") String name) {
        return "占位符方式 - service:" + service + ",name:" + name;
    }

    /**
     * 4.限定请求方法的映射,指定method即限定
     */
    @RequestMapping(value = "/restrict/request/method", method = RequestMethod.PUT)
    public String restrictRequestMethod() {
        return "限定请求方式为put";
    }

    /**
     * 5.限定参数的映射，@RequestParam获取请求参数
     * params=”name”：请求参数中必须带有name
     * params=”!name”：请求参数中不能包含name
     * params=”name=1”：请求参数中name必须为1
     * params=”name!=1”：请求参数中name必须不为1，参数中可以不包含name
     * params={“id”, ”name”}：请求参数中必须有id，name参数
     */
    @RequestMapping(value = "/restrict/request/param", params = {"id=1", "name", "name!=admin", "!age"})
    public String restrictRequestParam(@RequestParam("id") String id,
                                       @RequestParam("name") String name) {
        return "限定参数的映射：id=" + id + ",name=" + name;
    }

    /**
     * 5.限定请求头的映射，@RequestHeader获取请求头参数
     * headers=”name”：请求头中必须带有name
     * headers=”!name”：请求头中不能包含name
     * headers=”name=1”：请求头中name必须为1
     * headers=”name!=1”：请求头中name必须不为1，参数中可以不包含name
     * headers={“id”, ”name”}：请求头中必须有id，name参数
     */
    @RequestMapping(value = "/restrict/request/head", headers = {"ACCESS-TOKEN", "ACCESS-TOKEN!=0", "SCOPE=read"})
    public String restrictRequestHead(@RequestHeader("ACCESS-TOKEN") String token,
                                      @RequestHeader("SCOPE") String scope) {
        return "限定请求头的映射,token=" + token + ",scope=" + scope;
    }

    /**
     * 6.限制请求接收数据格式:Content-Type
     */
    @RequestMapping(value = "/restrict/request/media/text", consumes = {MediaType.TEXT_PLAIN_VALUE})
    public String restrictRequestMediaTypeText() {
        return "限制请求接收数据格式:text/plain";
    }

    @RequestMapping(value = "/restrict/request/media/json", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public String restrictRequestMediaTypeJson(@RequestBody User user) {
        return "限制请求接收数据格式:json,user=" + user;
    }

    /**
     * 7.限制返回数据格式
     */
    @RequestMapping(value = "/restrict/response/media/text", produces = {MediaType.TEXT_PLAIN_VALUE})
    public String restrictResponseMediaTypeText() {
        return "限制返回数据格式:text/plain";
    }

    @RequestMapping(value = "/restrict/response/media/json", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public String restrictResponseMediaTypeJson() {
        return "{\"message\":\"限制返回数据格式:application/json;charset=UTF-8\"}";
    }
}

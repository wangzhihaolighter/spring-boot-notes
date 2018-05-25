package com.example.springframework.boot.filter.config;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ServletComponentScan
public class SpringFilterConfig {

    /*
    filter功能
        它使用户可以改变一个 request和修改一个response
        Filter 不是一个servlet,它不能产生一个response,它能够在一个request到达servlet之前预处理request,也可以在离开servlet时处理response.换种说法,filter其实是一个”servlet chaining”(servlet 链).

    一个Filter包括：
        1）、在servlet被调用之前截获;
        2）、在servlet被调用之前检查servlet request;
        3）、根据需要修改request头和request数据;
        4）、根据需要修改response头和response数据;
        5）、在servlet被调用之后截获.

    注解说明：
    @Component 这个注解的目的是将类交给容器来处理，成为一个bean
    @ServletComponentScan 这个使用来扫描@WebFilter 的让@WebFilter起作用。当然对于servlet线管注解也是可以的，需要配置在配置类上
    @WebFilter 这个用处显而易见，针对于什么链接做过滤，filter的名称是为什么。
    @Order 可指定执行顺序，spring会按照order值的大小，从小到大的顺序来依次过滤
     */

    /**
     * 注册过滤器的另一种方式
     */
    //@Bean
    //public FilterRegistrationBean<DemoFilter> someFilterRegistration() {
    //    FilterRegistrationBean<DemoFilter> registration = new FilterRegistrationBean<>();
    //    //demoFilter
    //    registration.setFilter(new DemoFilter());
    //    registration.addUrlPatterns("/*");
    //    registration.addInitParameter("paramName", "paramValue");
    //    registration.setName("demoFilter");
    //    registration.setOrder(1);
    //    return registration;
    //}

}

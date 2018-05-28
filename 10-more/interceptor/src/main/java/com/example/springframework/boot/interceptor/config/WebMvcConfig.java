package com.example.springframework.boot.interceptor.config;

import com.example.springframework.boot.interceptor.interceptor.LoginInterceptor;
import com.example.springframework.boot.interceptor.interceptor.WebRequestParamInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /*
    参考链接：https://blog.csdn.net/qq_35246620/article/details/68487904?1491374806898

    Java里的拦截器是动态拦截 action 调用的对象。它提供了一种机制可以使开发者可以定义在一个 action 执行的前后执行的代码，也可以在一个 action 执行前阻止其执行，同时也提供了一种可以提取 action 中可重用部分的方式。
    在AOP（Aspect-Oriented Programming，面向切面编程）中拦截器用于在某个方法或字段被访问之前进行拦截，然后在之前或之后加入某些操作。

    拦截器实现原理基于动态代理，分为：
        jdk动态代理：代理类必须实现一个或多个接口
        cglib动态代理：代理类不能被final修饰

    在Spring框架之中，要实现拦截器的功能，主要通过两种途径：
        第一种是实现HandlerInterceptor接口，定义了3个方法，分别为preHandle()、postHandle()和afterCompletion()
            preHandle(HttpServletRequest request, HttpServletResponse response, Object handle)方法，该方法在请求处理之前进行调用.SpringMVC 中的 Interceptor 是链式调用的，在一个应用中或者说是在一个请求中可以同时存在多个 Interceptor 。每个 Interceptor 的调用会依据它的声明顺序依次执行，而且最先执行的都是 Interceptor 中的 preHandle 方法，所以可以在这个方法中进行一些前置初始化操作或者是对当前请求做一个预处理，也可以在这个方法中进行一些判断来决定请求是否要继续进行下去。该方法的返回值是布尔值 Boolean 类型的，当它返回为 false 时，表示请求结束，后续的 Interceptor 和 Controller 都不会再执行；当返回值为 true 时，就会继续调用下一个 Interceptor 的 preHandle 方法，如果已经是最后一个 Interceptor 的时候，就会是调用当前请求的 Controller 中的方法。
            postHandle(HttpServletRequest request, HttpServletResponse response, Object handle, ModelAndView modelAndView)方法，需要当前对应的 Interceptor 的 preHandle 方法的返回值为 true 时才会执行。postHandle 方法在当前请求进行处理之后，也就是在 Controller 中的方法调用之后执行，但是它会在 DispatcherServlet 进行视图返回渲染之前被调用，所以咱们可以在这个方法中对 Controller 处理之后的 ModelAndView 对象进行操作。postHandle 方法被调用的方向跟 preHandle 是相反的，也就是说，先声明的 Interceptor 的 postHandle 方法反而会后执行。
            afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handle, Exception ex)方法，需要当前对应的 Interceptor 的 preHandle 方法的返回值为 true 时才会执行。该方法将在整个请求结束之后，也就是在 DispatcherServlet 渲染了对应的视图之后执行，这个方法的主要作用是用于进行资源清理的工作。
            拓展接口：AsyncHandlerInterceptor、HandlerInterceptorAdapter
        第二种是实现WebRequestInterceptor接口，定义了3个方法，分别为preHandle()、postHandle()和afterCompletion()
            preHandle(WebRequest request)方法，该方法在请求处理之前进行调用，也就是说，其会在 Controller 中的方法调用之前被调用。它没有返回值，因此主要用它来进行资源的准备工作
            postHandle(WebRequest request, ModelMap model)方法，该方法在请求处理之后，也就是在 Controller 中的方法调用之后被调用，但是会在视图返回被渲染之前被调用，所以可以在这个方法里面通过改变数据模型 ModelMap 来改变数据的展示。
            afterCompletion(WebRequest request, Exception ex)方法，该方法会在整个请求处理完成，也就是在视图返回并被渲染之后执行。因此可以在该方法中进行资源的释放操作。而 WebRequest 参数就可以把咱们在 preHandle 中准备的资源传递到这里进行释放。Exception 参数表示的是当前请求的异常对象，如果在 Controller 中抛出的异常已经被 Spring 的异常处理器给处理了的话，那么这个异常对象就是是 null.
            拓展接口：WebRequestInterceptorAdapter

    实现自定义拦截器的步骤：
        1.创建拦截器，实现对应的拦截器接口
        2.创建拦截器配置类，添加自定义拦截器至拦截器链中
    */

    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    @Bean
    public WebRequestParamInterceptor webRequestParamInterceptor() {
        return new WebRequestParamInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/do").order(1);
        registry.addWebRequestInterceptor(webRequestParamInterceptor()).addPathPatterns("/**").order(2);
    }
}
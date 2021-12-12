package com.example.config;

import com.example.config.interceptor.HandlerInterceptorImpl;
import com.example.config.interceptor.WebRequestInterceptorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

  /*
  在AOP（Aspect-Oriented Programming，面向切面编程）中拦截器用于在某个方法或字段被访问之前进行拦截，然后在之前或之后加入某些操作。

  拦截器实现原理基于动态代理
  - jdk动态代理：代理类必须实现一个或多个接口
  - cglib动态代理：代理类不能被final修饰

  在Spring框架中，要实现拦截器的功能，主要通过两种途径：
  1.实现HandlerInterceptor接口，定义了3个方法，分别为preHandle()、postHandle()和afterCompletion()
      preHandle(HttpServletRequest request, HttpServletResponse response, Object handle)
          该方法在请求处理之前进行调用。
          SpringMVC中的 Interceptor 是链式调用的，在一个应用中或者说是在一个请求中可以同时存在多个Interceptor。
          每个 Interceptor 的调用会依据它的声明顺序依次执行，而且最先执行的都是 Interceptor 中的 preHandle 方法，所以可以在这个方法中进行一些前置初始化操作或者是对当前请求做一个预处理，也可以在这个方法中进行一些判断来决定请求是否要继续进行下去。
          该方法的返回值是布尔值 Boolean 类型的，当它返回为 false 时，表示请求结束，后续的 Interceptor 和 Controller 都不会再执行；当返回值为 true 时，就会继续调用下一个 Interceptor 的 preHandle 方法，如果已经是最后一个 Interceptor 的时候，就会是调用当前请求的 Controller 中的方法。
      postHandle(HttpServletRequest request, HttpServletResponse response, Object handle, ModelAndView modelAndView)
          需要当前对应的 Interceptor 的 preHandle 方法的返回值为 true 时才会执行。
          postHandle 方法在当前请求进行处理之后，也就是在 Controller 中的方法调用之后执行，但是它会在 DispatcherServlet 进行视图返回渲染之前被调用，所以咱们可以在这个方法中对 Controller 处理之后的 ModelAndView 对象进行操作。
          postHandle 方法被调用的方向跟 preHandle 是相反的，也就是说，先声明的 Interceptor 的 postHandle 方法反而会后执行。
      afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handle, Exception ex)
          需要当前对应的 Interceptor 的 preHandle 方法的返回值为 true 时才会执行。
          该方法将在整个请求结束之后，也就是在 DispatcherServlet 渲染了对应的视图之后执行，这个方法的主要作用是用于进行资源清理的工作。
  2.实现WebRequestInterceptor接口，定义了3个方法，分别为preHandle()、postHandle()和afterCompletion()
      preHandle(WebRequest request)
          该方法在请求处理之前进行调用，也就是说，其会在 Controller 中的方法调用之前被调用。它没有返回值，因此主要用它来进行资源的准备工作
      postHandle(WebRequest request, ModelMap model)
          该方法在请求处理之后，也就是在 Controller 中的方法调用之后被调用，但是会在视图返回被渲染之前被调用，所以可以在这个方法里面通过改变数据模型 ModelMap 来改变数据的展示。
      afterCompletion(WebRequest request, Exception ex)
          该方法会在整个请求处理完成，也就是在视图返回并被渲染之后执行，因此可以在该方法中进行资源的释放操作。
          WebRequest参数可以把在 preHandle 中准备的资源传递到这里进行释放。
          Exception参数表示的是当前请求的异常对象，如果在 Controller 中抛出的异常已经被 Spring 的异常处理器给处理了的话，那么这个异常对象就是是null。

  在Spring框架中自定义拦截器的步骤：
  1. 创建拦截器，实现对应的拦截器接口
  2. WEB配置类中添加自定义拦截器至拦截器链中
  */

  @Bean
  public HandlerInterceptorImpl handlerInterceptor() {
    return new HandlerInterceptorImpl();
  }

  @Bean
  public WebRequestInterceptorImpl webRequestInterceptor() {
    return new WebRequestInterceptorImpl();
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(handlerInterceptor()).order(1);
    registry.addWebRequestInterceptor(webRequestInterceptor()).order(1);
  }
}

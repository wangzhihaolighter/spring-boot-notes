# 02-web
说明：web业务开发涉及到的概念和技术，通过一些demo了解原理和使用

## api：应用程序编程接口（Application Programming Interface）
- restful，构建简单的RESTFul API

## servlet：基于Servlet API的web应用程序
- spring mvc，spring的web mvc实现，构建在Servlet API上的原始Web框架

## reactive：Reactive Streams，响应流，为非阻塞背压提供异步流处理标准的计划
- webflux，使用一个非阻塞的Web堆栈来处理少量线程的并发性并使用较少的硬件资源进行扩展，构建非阻塞应用程序

## template：页面模板引擎
- jsp，使用JSP模板引擎渲染web视图
- freemarker，使用Freemarker模板引擎渲染web视图
- thymeleaf，使用Thymeleaf模板引擎渲染web视图

## docs：API文档
- swagger:使用Swagger2构建RESTFul API接口文档

## exception：统一异常处理
- exception-handler,常规的统一异常处理

## validation：使用校验框架validation校验
- request-parameter-validation，请求参数校验

## cryptographic：加解密
- HTTP Encryption，HTTP通信加解密
    - `RequestBodyAdvice`接口:实现后注入，可对请求参数预处理
    - `ResponseBodyAdvice`接口：实现后注入，可对响应参数进行预处理
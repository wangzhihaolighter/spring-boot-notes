# 说明

spring boot学习汇总。这些都是demo，用于记录spring boot的一些特性，实现一些小功能。
Spring Boot版本：2.X
JDK版本：8
开发工具：IntelliJ IDEA

## 01-hello:入门
- **hello**,一个简单的HelloWorld
- **properties**,获取文件中属性值得多种方式
- **test**,测试用例的多种写法
- **starter**,编写一个starter

## 02-web:web业务开发
- **restful**,构建简单的RESTFul API
- **mvc**,*spring mvc*的基本用法
- **webflux**,函数式web框架
- **freemarker**,使用*Freemarker*模板引擎渲染web视图
- **thymeleaf**,使用*Thymeleaf*模板引擎渲染web视图
- **jsp**,使用*JSP*模板引擎渲染web视图
- **swagger**,使用*Swagger2*构建RESTFul API接口文档
- **exception**,统一异常处理
- **validation**,使用校验框架*validation*校验
- **HTTP Encryption**,HTTP通信加解密

## 03-security:安全管理
- **security**,使用*Spring Security*进行基本安全控制
- **security-swagger**,使用*Spring Security*保护swagger文档
- **security-db**,数据库动态管理用户权限
- **security-db-html**,数据库动态管理用户权限/自定义登录页面
- **security-db-json**,数据库动态管理用户权限/认证流程返回json信息
- **security-db-swagger**,数据库动态管理用户权限/认证流程返回json信息/整合保护swagger接口文档
- *TODO* oauth2

## 04-data-access:数据访问
- **pool**,常用的数据库连接池框架
    - **HikariCP**
    - **druid**
- **ORM**,常用的ORM框架(orm:对象关系映射)
    - **JdbcTemplate**
    - **spring data jpa**
    - **mybatis**
- **multiple dataSources**,多数据源配置
    - 方式一：不同DAO层对应不同的数据源
    - 方式二：AOP方式，约定service方法名动态设置数据源
    - 方式三：AOP+注解方式,通过注解动态设置数据源
- **NoSQL**,常用的非关系型数据库
    - **redis**
    - **MongoDB**
- **tool**,一些常用的简化开发工具
    - **flyway**,使用Flyway来管理数据库版本
    - **mybatis增强库**,*通用mapper*,提供通用CRUD方法
    - **mybatis增强库**,*pageHelper*分页插件
    - **mybatis generator**,自动生成代码插件

## 05-transaction:事务管理
- **spring-annotation-transaction**:基于spring的注解式事务管理
- **aop-transaction**:基于aop的约定式事务管理

## 06-cache:缓存支持

## 07-log:日志管理

## 08-message-queue:消息服务

## 09-monitor:监控管理

## 10-more:更多功能
- **container** 容器
    - **embed-tomcat-optimize** Spring Boot内嵌tomcat调优
- **protocol** 协议
    - **spring ldap**:使用LDAP来统一管理用户信息（LDAP:轻量级目录访问协议）
- **AOP** 面向切面编程(Aspect Oriented Programming)
    - **spring-aop** spring的AOP实现
    - **filter** Servlet技术中过滤器filter
    - **interceptor** spring mvc中拦截器interceptor
    - **execution-order** filter、aop、interceptor执行顺序测试
- **schedule** 任务调度
    - **spring-annotation-schedule** 使用`@Scheduled`创建定时任务
    - **dynamic-spring-scheduler** 使用spring中的任务调度实现schedule，实现动态定时任务
    - **dynamic-quartz-scheduler** 整合*quartz*，实现动态定时任务
- **async** 异步
    - **spring async** 使用`@Async`实现异步调用
    - **async-transaction** 业务层事务提交后执行异步调用
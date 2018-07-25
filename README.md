# 说明

spring boot学习汇总，整理了一些demo，记录spring boot的特性和实现一些小功能，以便日后快速使用。
version:2.X
JDK:1.8


# 目录

[TOC]

## 1. hello:入门

1. **hello**,一个简单的HelloWorld
1. **properties**,获取文件中属性值得多种方式
1. **test**,测试用例的多种写法
1. **webflux**,函数式web框架
1. **starter**,编写一个starter

## 02-web:web业务开发

1. **restful**,构建简单的RESTFul API
1. **mvc**,*spring mvc*的基本用法
1. **freemarker**,使用*Freemarker*模板引擎渲染web视图
1. **thymeleaf**,使用*Thymeleaf*模板引擎渲染web视图
1. **jsp**,使用*JSP*模板引擎渲染web视图
1. **swagger**,使用*Swagger2*构建RESTFul API接口文档
1. **exception**,统一异常处理
1. **validation**,使用校验框架*validation*校验
1. **HTTP Encryption**,HTTP通信加解密

## 03-security:安全管理

1. **security**,使用*Spring Security*进行基本安全控制
1. **security-swagger**,使用*Spring Security*保护swagger文档
1. **security-db**,数据库动态管理用户权限
1. **security-db-html**,数据库动态管理用户权限/自定义登录页面
1. **security-db-json**,数据库动态管理用户权限/认证流程返回json信息
1. **security-db-swagger**,数据库动态管理用户权限/认证流程返回json信息/整合保护swagger接口文档
1. *TODO* oauth2

## 04-data-access:数据访问

1. **pool**,常用的数据库连接池框架
    1. **HikariCP**
    1. **druid**
1. **ORM**,常用的ORM框架(orm:对象关系映射)
    1. **JdbcTemplate**
    1. **spring data jpa**
    1. **mybatis**
1. **multiple dataSources**,多数据源配置
    1. 方式一：不同DAO层对应不同的数据源
    1. 方式二：AOP方式，约定service方法名动态设置数据源
    1. 方式三：AOP+注解方式,通过注解动态设置数据源
1. **NoSQL**,常用的非关系型数据库
    1. **redis**
    1. **MongoDB**
1. **tool**,一些常用的简化开发工具
    1. **flyway**,使用Flyway来管理数据库版本
    1. **mybatis增强库**,*通用mapper*,提供通用CRUD方法
    1. **mybatis增强库**,*pageHelper*分页插件
    1. **mybatis generator**,自动生成代码插件

## 05-transaction:事务管理

1. **spring-annotation-transaction**:基于spring的注解式事务管理
1. **aop-transaction**:基于aop的约定式事务管理

## 06-cache:缓存支持

## 07-log:日志管理

## 08-message-queue:消息服务

## 09-monitor:监控管理

## 10-more:更多功能

1. **demo-tomcat-optimize**:Spring Boot内嵌tomcat调优
1. **ldap**:使用LDAP来统一管理用户信息（LDAP:轻量级目录访问协议）
1. **aop**:AOP(Aspect Oriented Programming),面向切面编程
1. **filter**:配置过滤器filter
1. **interceptor**:配置拦截器interceptor
1. **aop-filter-interceptor**:aop、filter、interceptor执行顺序测试
1. **async**:使用`@Async`实现异步调用
1. **async-transaction**:异步方法在方法事务提交后执行
1. **schedule**:使用`@Scheduled`创建定时任务
1. **dynamic-quartz-scheduler**:整合*quartz*，实现可动态修改时间定时任务
1. **Bean**:spring中bean处理相关
1. 配置SSL,http转向https
1. 整合docker

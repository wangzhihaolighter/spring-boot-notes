## spring-boot-learn
这是springboot学习汇总，一些demo，用于记录用springboot简化开发和完成一些小功能<br/>
关于springboot用了一段时间，排除之前自己按照网上一些教学博客学习的demo，一直没有好好整理下，之前整理的其实也很杂乱，现在重新整理下。<br/>
原来整理目录结构条理不清晰，所以重新整理，并spring boot版本全部改为2.0，边总结边学习。<br/>

### 01-hello:入门
1. __hello__,一个简单的HelloWorld
1. __properties__,获取文件中属性值得多种方式
1. __test__,测试用例的多种写法
1. __webflux__,函数式web框架
1. __starter__,编写一个starter

### 02-web:web业务开发
1. __restful__,构建简单的RESTFul API
1. __mvc__,*spring mvc*的基本用法
1. __freemarker__,使用*Freemarker*模板引擎渲染web视图
1. __thymeleaf__,使用*Thymeleaf*模板引擎渲染web视图
1. __jsp__,使用*JSP*模板引擎渲染web视图
1. __swagger__,使用*Swagger2*构建RESTFul API接口文档
1. __exception__,统一异常处理
1. __validation__,使用校验框架*validation*校验
1. __HTTP Encryption__,HTTP通信加解密

### 03-security:安全管理
1. __security__,使用*Spring Security*进行基本安全控制
1. __security-swagger__,使用*Spring Security*保护swagger文档
1. __security-db__,数据库动态管理用户权限
1. __security-db-html__,数据库动态管理用户权限/自定义登录页面
1. __security-db-json__,数据库动态管理用户权限/认证流程返回json信息
1. __security-db-swagger__,数据库动态管理用户权限/认证流程返回json信息/整合保护swagger接口文档
1. *TODO* oauth2

### 04-data-access:数据访问
1. __pool__,常用的数据库连接池框架
    1. __HikariCP__
    1. __druid__
1. __ORM__,常用的ORM框架(orm:对象关系映射)
    1. __JdbcTemplate__
    1. __spring data jpa__
    1. __mybatis__
1. __multiple dataSources__,多数据源配置
    1. 方式一：不同DAO层对应不同的数据源
    1. 方式二：AOP方式，约定service方法名动态设置数据源
    1. 方式三：AOP+注解方式,通过注解动态设置数据源
1. __NoSQL__,常用的非关系型数据库
    1. __redis__
    1. __MongoDB__
1. __tool__,一些常用的简化开发工具
    1. __flyway__,使用Flyway来管理数据库版本
    1. __mybatis增强库__,*通用mapper*,提供通用CRUD方法
    1. __mybatis增强库__,*pageHelper*分页插件
    1. __mybatis generator__,自动生成代码插件

### 05-transaction:事务管理
1. __spring-annotation-transaction__:基于spring的注解式事务管理
1. __aop-transaction__:基于aop的约定式事务管理

### 06-cache:缓存支持

### 07-log:日志管理

### 08-message-queue:消息服务

### 09-monitor:监控管理

### 10-more:更多功能
1. __demo-tomcat-optimize__:Spring Boot内嵌tomcat调优
1. __ldap__:使用LDAP来统一管理用户信息（LDAP:轻量级目录访问协议）
1. __aop__:AOP(Aspect Oriented Programming),面向切面编程
1. __filter__:配置过滤器filter
1. __interceptor__:配置拦截器interceptor
1. __aop-filter-interceptor__:aop、filter、interceptor执行顺序测试
1. __async__:使用`@Async`实现异步调用
1. __async-transaction__:异步方法在方法事务提交后执行
1. __schedule__:使用`@Scheduled`创建定时任务
1. __dynamic-quartz-scheduler__:整合*quartz*，实现可动态修改时间定时任务
1. __Bean__:spring中bean处理相关
1. 配置SSL,http转向https
1. 整合docker

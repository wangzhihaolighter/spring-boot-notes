# 说明

项目用于记录学习、使用 Spring Boot 框架的一些知识。

内容主要是 Spring Boot 框架的一些特性、功能的实现、常用框架的整合等。

Spring Boot版本：2.X

JDK版本：8

开发工具：[IntelliJ IDEA](https://www.jetbrains.com/idea/)

代码风格：[google-java-format](https://github.com/google/google-java-format)

## A.using

Spring Boot的一些使用。

- [**hello-world**](DOC/A.using/hello-world/Hello-World.md)：一个简单 Spring Boot Hello World 示例
- [**devtools**](spring-boot-example/A.using/devtools)：热部署插件整合示例
- [**package image**](spring-boot-example/A.using/package-image)：构建部署镜像示例
- [**package war**](spring-boot-example/A.using/package-war)：构建war格式部署包示例
- [**package zip**](spring-boot-example/A.using/package-zip)：构建zip格式部署包示例，zip包中包含jar和各个环境配置文件

## B.features

Spring Boot的一些特性。

- **aop**：面向切面编程
  - [**aspect**](spring-boot-example/B.features/aop/aspect)：面向切面
  - [**filter**](spring-boot-example/B.features/aop/filter)：过滤器
  - [**interceptor**](spring-boot-example/B.features/aop/interceptor)：拦截器
- [**async**](spring-boot-example/B.features/async)：异步调用
- [**auto-configuration**](DOC/B.feature/auto-configuration/Creating-Your-Own-Auto-Configuration.md)：自动配置
- [**bean-validation**](DOC/B.feature/bean-validation/bean-validation.md)：Bean 数据验证(常用注解、校验模式配置、国际化、自定义验证器)
- **datasource**：数据源
  - [**dynamic-datasource**](spring-boot-example/B.features/datasource/dynamic-datasource)：动态切换数据源
  - [**multi-datasource-jpa**](spring-boot-example/B.features/datasource/multi-datasource-jpa)：多数据源 - Spring Data JPA 实现
  - [**multi-datasource-mybatis**](spring-boot-example/B.features/datasource/multi-datasource-mybatis)：多数据源 - mybatis 实现
- [**event-listener**](spring-boot-example/B.features/event-listener)：事件监听
- [**externalized-configuration**](DOC/B.feature/externalized-configuration/Externalized-Configuration.md)：外部化配置
- [**i18n**](DOC/B.feature/i18n/i18n.md)：国际化消息提示
- **JDBC**：Java 连接、操作数据库
  - [**spring-jdbc**](spring-boot-example/B.features/jdbc/spring-jdbc)：通过Spring JdbcTemplate 操作数据库
  - [**spring-data-jdbc**](spring-boot-example/B.features/jdbc/spring-data-jdbc)：通过 Spring Data JDBC 操作数据库
  - [**spring-data-jpa**](spring-boot-example/B.features/jdbc/spring-data-jpa)：通过 Spring Data JPA 操作数据库
- [**logging**](DOC/B.feature/logging/logging.md)：日志
  - [**logging-logback**](spring-boot-example/B.features/logging/logging-logback)：整合 logback
  - [**logging-log4j2**](spring-boot-example/B.features/logging/logging-log4j2)：整合 log4j2
  - [**tomcat access log**](DOC/B.feature/logging/logging-tomcat-access.md)：tomcat 访问日志
- [**retry**](spring-boot-example/B.features/retry)：重试
- **scheduling**：定时任务调度
  - [**spring-task**](spring-boot-example/B.features/scheduling/spring-task)：基于 spring task 实现定时任务调度
  - [**quartz-scheduler**](spring-boot-example/B.features/scheduling/quartz-scheduler)：基于 quartz 实现定时任务调度
- [**security**](spring-boot-example/B.features/security)：安全、认证、授权
- [**sending-email**](DOC/B.feature/mail/Sending-Email.md)：发送邮件
- [**spring-web**](spring-boot-example/B.features/spring-web)：同步阻塞的 Web 框架
- [**spring-webflux**](spring-boot-example/B.features/spring-webflux)：响应式、异步非阻塞的 Web 框架
- **template**：模板引擎
  - [**freemarker**](spring-boot-example/B.features/template/freemarker)：一个模板引擎，一个基于模板生成文本输出的通用工具
  - [**thymeleaf**](spring-boot-example/B.features/template/thymeleaf)：一个现代的服务器端 Java 模板引擎，适用于 web 和独立环境
- [**testing**](DOC/B.feature/testing/testing.md)：测试
- **transaction**：事务
  - [**transaction-spring-annotation**](spring-boot-example/B.features/transaction/transaction-spring-annotation)：注解式事务
  - [**transaction-spring-interceptor**](spring-boot-example/B.features/transaction/transaction-spring-interceptor)：拦截器统一管理事务
  - [**transaction-multi-datasource-jpa**](spring-boot-example/B.features/transaction/transaction-multi-datasource-jpa)：多数据源事务 - spring data jpa 实现
  - [**transaction-multi-datasource-mybatis**](spring-boot-example/B.features/transaction/transaction-multi-datasource-mybatis)：多数据源事务 - mybatis 实现
- [**web-services**](spring-boot-example/B.features/web-services)：面向服务
- [**websocket**](spring-boot-example/B.features/websocket)：一种在单个 TCP 连接上进行全双工通信的协议

## C.starters

一些框架或功能整合后的 Spring Boot Starter。

- [**kaptcha-spring-boot-starter**](spring-boot-example/C.starters/kaptcha-spring-boot-starter)：整合kaptcha快速实现验证码功能
- [**okhttp-spring-boot-starter**](spring-boot-example/C.starters/okhttp-spring-boot-starter)：整合okhttp快速实现http功能

## D.integration

一些常用框架的集成。

- [**derby**](spring-boot-example/D.integration/derby)：一个完全用java编写的数据库
- [**elastic-search**](spring-boot-example/D.integration/elastic-search)：一个分布式、RESTful 风格的搜索和数据分析引擎
- [**h2database**](spring-boot-example/D.integration/h2database)：一个用 Java 开发的嵌入式数据库
- [**hsqldb**](spring-boot-example/D.integration/hsqldb)：用 Java 编写的一款 SQL 关系数据库引擎
- [**jetty**](spring-boot-example/D.integration/jetty)：一个开源的 servlet 容器
- [**kafka**](spring-boot-example/D.integration/kafka)：由 Apache 软件基金会开发的一个开源流处理平台
- [**mongodb**：一个基于分布式文件存储的数据库](DOC/D.integration/mongodb/mongodb.md)
- [**mysql**](spring-boot-example/D.integration/mysql)：mysql 数据库集成示例
- [**oracle**](spring-boot-example/D.integration/oracle)：oracle 数据库集成示例
- **prometheus**：一个开源的系统监控和报警系统
  - [**prometheus-simple**](spring-boot-example/D.integration/prometheus/prometheus-simple)：spring boot actuator 开放 prometheus 指标端口示例，用于 prometheus 采集指标
  - [**prometheus-pushgateway**](spring-boot-example/D.integration/prometheus/prometheus-pushgateway)：spring boot actuator 开放 prometheus 指标端口示例，推送 prometheus 指标到 PushGateway
- [**rabbitmq**](spring-boot-example/D.integration/rabbitmq)：一套开源（MPL）的消息队列服务软件
  - **rabbitmq-consumer-demo**：rabbitmq消费者示例
  - **rabbitmq-provider-demo**：rabbitmq生产者示例
- [**redis**：一个开源的使用ANSI C语言编写、支持网络、可基于内存亦可持久化的日志型、Key-Value数据库](DOC/D.integration/redis/integrate-redis.md)
- [**undertow**](spring-boot-example/D.integration/undertow)：Undertow 是红帽公司开发的一款基于NIO 的高性能 Web 嵌入式服务器
- [**zookeeper**：一个分布式的，开放源码的分布式应用程序协调服务](DOC/D.integration/zookeeper/integrate-zookeeper.md)

## E.tools

一些常用功能框架的使用示例。

- [**alibaba-druid**](spring-boot-example/E.tools/alibaba-druid)：阿里巴巴计算平台事业部出品，为监控而生的数据库连接池
- [**alibaba-easyexcel**](spring-boot-example/E.tools/alibaba-easyexcel)：EasyExcel是一个基于Java的简单、省内存的读写Excel的开源项目
- [**alibaba-sentinel**](spring-boot-example/E.tools/alibaba-sentinel)：面向分布式服务框架的轻量级流量控制框架，主要以流量为切入点，从流量控制，熔断降级，系统负载保护等多个维度来维护系统的稳定性
- [**apache-dubbo**](spring-boot-example/E.tools/apache-dubbo)：一款高性能、轻量级的开源服务框架
  - **dubbo-common**：dubbo 示例 - common 模块
  - **dubbo-consumer**：dubbo 示例 - 服务消费者
  - **dubbo-provider**：dubbo 示例 - 服务提供者
- [**apache-poi**](DOC/E.tool/apache-poi/apache-poi.md)：一个 Java 实现的操作 Microsoft Office格式文档的工具库
- [**browscap-java**](DOC/E.tool/browscap-java/browscap-java.md)：用于解析useragent头，以提取有关使用的浏览器、浏览器版本、平台、平台版本和设备类型的信息
- [**caffeine**](DOC/E.tool/caffeine/caffeine.md)：一个基于Java8开发的提供了近乎最佳命中率的高性能的缓存库
- [**easy-captcha**](spring-boot-example/E.tools/easy-captcha)：Java图形验证码，支持gif、中文、算术等类型，可用于Java Web、JavaSE等项目
- [**flyway**](DOC/E.tool/flyway/flyway.md)：一个开源数据库迁移工具
- [**google-zxing**](DOC/E.tool/google-zxing/google-zxing.md)：Google开源的多维码生成工具
- [**hikaricp**](spring-boot-example/E.tools/hikaricp)：一个高性能的 JDBC 连接池组件
- [**ip2region**](DOC/E.tool/ip2region/ip2region.md)：离线IP地址定位库
- [**jasypt**](spring-boot-example/E.tools/jasypt)：提供一种简单的方式来为项目增加加密功能
- [**jjwt**](DOC/E.tool/jjwt/JSON-Web-Tokens.md)：用于在JVM和Android上创建和验证JSON Web Token（JWT）
- [**jsch**](DOC/E.tool/jsch/JSch.md)：SSH2的一个纯Java实现
- [**kaptcha**](spring-boot-example/E.tools/kaptcha)：一个可高度配置的实用验证码生成工具
- [**liquibase**](spring-boot-example/E.tools/liquibase)：一个用于跟踪,管理和应用数据库变化的开源的数据库重构工具
- [**macro-benchmarks**](spring-boot-example/E.tools/macro-benchmarks)：Java性能测试工具，主要是对工程中一些方法进行一些基准测试，支持的时间单位为:nano / micro / milli / macro
- [**mapstruct**](spring-boot-example/E.tools/mapstruct)：一个 Java 实体映射工具
- **mybatis**：MyBatis 是一款优秀的持久层框架,它支持自定义 SQL、存储过程以及高级映射
  - [**mybatis-simple**](spring-boot-example/E.tools/mybatis/mybatis-simple)：mybatis 整合示例
  - [**mybatis-plus**](spring-boot-example/E.tools/mybatis/mybatis-plus)：MyBatis-Plus 是一个 MyBatis 的增强工具，在 MyBatis 的基础上只做增强不做改变，为简化开发、提高效率而生。
  - [**mybatis-mapper**](spring-boot-example/E.tools/mybatis/mybatis-mapper)：mybatis 通用 Mapper
  - [**mybatis-pagehelper**](spring-boot-example/E.tools/mybatis/mybatis-pagehelper)：mybatis 分页插件
- [**netty**](spring-boot-example/E.tools/netty)：Netty 提供异步的、事件驱动的网络应用程序框架和工具，用以快速开发高性能、高可靠性的网络服务器和客户端程序。
  - **netty-client**：netty 客户端
  - **netty-server**：netty 服务端
- **open-api**：Open API ，开放 API 文档规范格式
  - [**springdoc-openapi**](DOC/E.tool/springdoc-openapi/springdoc-openapi.md)：一个开源的 API doc 框架，可以将 Controller 的方法以文档的形式展现
  - [**springfox-swagger**](DOC/E.tool/springfox-swagger/springfox-swagger.md)：一个开源的 API doc 框架，可以将 Controller 的方法以文档的形式展现
  - [**redoc**](spring-boot-example/E.tools/open-api/redoc)：an openapi-powered documentation ui
- [**oshi-core**](DOC/E.tool/oshi/oshi.md)：Java的免费基于JNA的（本机）操作系统和硬件信息库，跨平台查看服务器信息
- [**p6spy**](DOC/E.tool/p6spy/P6Spy.md)：记录任何Java应用程序的所有JDBC事务
- [**redisson**](spring-boot-example/E.tools/redisson)：基于redis的扩展库，使得redis除了应用于缓存以外，还能做队列等数据结构，直接使用的分布式锁，以及人物调度器等。
- [**resilience4j**](DOC/E.tool/resilience4j/resilience4j.md)：Resilience4j是一个轻量级、易于使用的容错库，其灵感来自Netflix Hystrix，但专为Java 8和函数式编程设计。
- [**rsocket**](spring-boot-example/E.tools/rsocket)：RSocket是一种二进制的点对点通信协议,是一种新的网络通信第七层协议。
  - **rsocket-client**：rsocket 客户端
  - **rsocket-server**：rsocket 服务端
- [**spring-boot-admin**](spring-boot-example/E.tools/spring-boot-admin)：Admin UI for administration of spring boot applications
  - **spring-boot-admin-client**：spring-boot-admin 客户端
  - **spring-boot-admin-server**：spring-boot-admin 服务端
- [**UserAgentUtils**](DOC/E.tool/user-agent-utils/UserAgentUtils.md)：实时解析HTTP请求或分析日志文件并收集有关用户代理的信息

## F.services

一些实现相应功能的服务示例。

- [**api-data-codec**](spring-boot-example/F.services/api-data-codec)：api 接口请求/响应数据编解码
- [**simple-distributed-scheduling-service**](spring-boot-example/F.services/simple-distributed-scheduling-service)：简单的分布式定时任务调度服务

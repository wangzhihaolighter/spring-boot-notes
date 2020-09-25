# 说明

spring-boot-notes 用于记录学习、使用 Spring Boot 框架。

主要内容是 Spring Boot 框架的一些特性、小功能的实现、常用框架的整合等。

Spring Boot版本：2.X

JDK版本：8

开发工具：IntelliJ IDEA

## A.hello

你好，Spring Boot。

- [**hello**：一个简单Spring Boot的HelloWorld示例](MD/feature/Hello-World.md)

## B.features

Spring Boot的一些特性。

- [**auto-configuration**：自动配置](MD/feature/Creating-Your-Own-Auto-Configuration.md)
- [**bean-validation**：Bean数据验证(常用注解、校验模式配置、国际化、自定义验证器)](MD/feature/bean-validation.md)
- [**externalized-configuration**：外部化配置](MD/feature/Externalized-Configuration.md)
- [**internationalization**：国际化消息提示](MD/feature/Internationalization.md)
- [**logging**：日志](MD/feature/Logging.md)
- [**sending-email**：发送邮件](MD/feature/Sending-Email.md)
- [**testing**：测试](MD/feature/Testing.md)
- [**web-server-optimize**：Spring Boot Web 服务优化](MD/feature/web-server-optimize.md)

## C.starters

一些整合的spring boot starter。

- **okhttp-spring-boot-starter**：整合okhttp

## D.integration

一些常用框架的集成。

- **rabbitmq**：一套开源（MPL）的消息队列服务软件
  - **rabbitmq-consumer-demo**：rabbitmq消费者示例
  - **rabbitmq-provider-demo**：rabbitmq生产者示例
- [**redis**：一个开源的使用ANSI C语言编写、支持网络、可基于内存亦可持久化的日志型、Key-Value数据库](MD/integration/integrate-redis.md)
- [**mongo**：一个基于分布式文件存储的数据库](MD/integration/integrate-mongodb.md)
- [**zookeeper**：一个分布式的，开放源码的分布式应用程序协调服务](MD/integration/integrate-zookeeper.md)

## E.tools

一些常用功能框架的使用示例。

- [**apache-poi**：一个 Java 实现的操作 Microsoft Office格式文档的工具库](MD/tool/apache-poi.md)
- [**browscap-java**：用于解析useragent头，以提取有关使用的浏览器、浏览器版本、平台、平台版本和设备类型的信息](MD/tool/browscap-java.md)
- [**google-zxing**：Google开源的多维码生成工具](MD/tool/google-zxing.md)
- [**flyway**：一个开源数据库迁移工具](MD/tool/flyway.md)
- [**h2database**：一个开源的嵌入式（非嵌入式设备）数据库引擎](MD/tool/h2database.md)
- [**ip2region**：离线IP地址定位库](MD/tool/ip2region.md)
- [**jjwt**：用于在JVM和Android上创建和验证JSON Web Token（JWT）](MD/tool/JSON-Web-Tokens.md)
- [**jsch**：SSH2的一个纯Java实现](MD/tool/JSch.md)
- [**oshi-core**：Java的免费基于JNA的（本机）操作系统和硬件信息库，跨平台查看服务器信息](MD/tool/oshi.md)
- [**p6spy**：记录任何Java应用程序的所有JDBC事务](MD/tool/P6Spy.md)
- [**springfox-swagger**：一个开源的 API doc 框架，可以将 Controller 的方法以文档的形式展现](MD/tool/springfox-swagger.md)
- [**springdoc-openapi**：一个开源的 API doc 框架，可以将 Controller 的方法以文档的形式展现](MD/tool/springdoc-openapi.md)
- [**UserAgentUtils**：实时解析HTTP请求或分析日志文件并收集有关用户代理的信息](MD/tool/UserAgentUtils.md)

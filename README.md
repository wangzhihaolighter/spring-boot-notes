# 说明

spring-boot-notes 用于记录学习、使用 Spring Boot 框架。

主要内容是 Spring Boot 框架的一些特性、小功能的实现、常用框架的整合等。

Spring Boot版本：2.X

JDK版本：8

开发工具：IntelliJ IDEA

代码风格：[google-java-format](https://github.com/google/google-java-format)

## A.using

Spring Boot的一些使用。

- [**hello-world**：一个简单Spring Boot的HelloWorld示例](DOC/A.using/hello-world/Hello-World.md)
- **package war**：构建war格式部署包示例

## B.features

Spring Boot的一些特性。

- [**auto-configuration**：自动配置](DOC/B.feature/auto-configuration/Creating-Your-Own-Auto-Configuration.md)
- [**bean-validation**：Bean数据验证(常用注解、校验模式配置、国际化、自定义验证器)](DOC/B.feature/bean-validation/bean-validation.md)
- [**externalized-configuration**：外部化配置](DOC/B.feature/externalized-configuration/Externalized-Configuration.md)
- [**internationalization**：国际化消息提示](DOC/B.feature/i18n/Internationalization.md)
- [**logging**：日志](DOC/B.feature/logging/Logging.md)
- [**sending-email**：发送邮件](DOC/B.feature/mail/Sending-Email.md)
- [**testing**：测试](DOC/B.feature/testing/Testing.md)

## C.starters

一些整合的spring boot starter。

- **okhttp-spring-boot-starter**：整合okhttp

## D.integration

一些常用框架的集成。

- **rabbitmq**：一套开源（MPL）的消息队列服务软件
  - **rabbitmq-consumer-demo**：rabbitmq消费者示例
  - **rabbitmq-provider-demo**：rabbitmq生产者示例
- [**redis**：一个开源的使用ANSI C语言编写、支持网络、可基于内存亦可持久化的日志型、Key-Value数据库](DOC/D.integration/redis/integrate-redis.md)
- [**mongo**：一个基于分布式文件存储的数据库](DOC/D.integration/mongodb/integrate-mongodb.md)
- [**zookeeper**：一个分布式的，开放源码的分布式应用程序协调服务](DOC/D.integration/zookeeper/integrate-zookeeper.md)

## E.tools

一些常用功能框架的使用示例。

- [**apache-poi**：一个 Java 实现的操作 Microsoft Office格式文档的工具库](DOC/E.tool/apache-poi/apache-poi.md)
- [**browscap-java**：用于解析useragent头，以提取有关使用的浏览器、浏览器版本、平台、平台版本和设备类型的信息](DOC/E.tool/browscap-java/browscap-java.md)
- [**Caffeine**：一个基于Java8开发的提供了近乎最佳命中率的高性能的缓存库](DOC/E.tool/caffeine/Caffeine.md)
- [**flyway**：一个开源数据库迁移工具](DOC/E.tool/flyway/flyway.md)
- [**google-zxing**：Google开源的多维码生成工具](DOC/E.tool/google-zxing/google-zxing.md)
- [**h2database**：一个开源的嵌入式（非嵌入式设备）数据库引擎](DOC/E.tool/h2database/h2database.md)
- [**ip2region**：离线IP地址定位库](DOC/E.tool/ip2region/ip2region.md)
- [**jjwt**：用于在JVM和Android上创建和验证JSON Web Token（JWT）](DOC/E.tool/jwt/JSON-Web-Tokens.md)
- [**jsch**：SSH2的一个纯Java实现](DOC/E.tool/jsch/JSch.md)
- [**oshi-core**：Java的免费基于JNA的（本机）操作系统和硬件信息库，跨平台查看服务器信息](DOC/E.tool/oshi/oshi.md)
- [**p6spy**：记录任何Java应用程序的所有JDBC事务](DOC/E.tool/p6spy/P6Spy.md)
- [**springdoc-openapi**：一个开源的 API doc 框架，可以将 Controller 的方法以文档的形式展现](DOC/E.tool/springdoc-openapi/springdoc-openapi.md)
- [**springfox-swagger**：一个开源的 API doc 框架，可以将 Controller 的方法以文档的形式展现](DOC/E.tool/springfox-swagger/springfox-swagger.md)
- [**UserAgentUtils**：实时解析HTTP请求或分析日志文件并收集有关用户代理的信息](DOC/E.tool/user-agent-utils/UserAgentUtils.md)

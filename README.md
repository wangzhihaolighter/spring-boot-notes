# 说明

spring-boot-notes用于记录学习、使用Spring Boot的过程。

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
- [**externalized-configuration**：外部化配置](MD/feature/Externalized-Configuration.md)
- [**logging**：日志](MD/feature/Logging.md)
- [**sending-email**：发送邮件](MD/feature/Sending-Email.md)
- [**testing**：测试](MD/feature/Testing.md)

## C.starters

一些整合的spring boot starter。

- **okhttp-spring-boot-starter**：整合okhttp

## D.integration

一些常用框架的集成。

- **rabbitmq**
  - **rabbitmq-consumer-demo**：rabbitmq消费者示例
  - **rabbitmq-provider-demo**：rabbitmq生产者示例

## E.tools

一些常用功能框架的使用示例。

- **google-zxing**：Google开源的多维码生成工具
- [**p6spy**：记录任何Java应用程序的所有JDBC事务](MD/tool/P6Spy.md)
- [**ip2region**：离线IP地址定位库](MD/tool/ip2region.md)
- [**UserAgentUtils**：实时解析HTTP请求或分析日志文件并收集有关用户代理的信息](MD/tool/UserAgentUtils.md)
- [**browscap-java**：用于解析useragent头，以提取有关使用的浏览器、浏览器版本、平台、平台版本和设备类型的信息](MD/tool/browscap-java.md)
- [**oshi-core**：Java的免费基于JNA的（本机）操作系统和硬件信息库，跨平台查看服务器信息](MD/tool/oshi.md)
- [**jjwt**：用于在JVM和Android上创建和验证JSON Web Token（JWT）](MD/tool/JSON-Web-Tokens.md)

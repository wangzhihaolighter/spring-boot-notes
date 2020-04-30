# 说明

spring-boot-notes用于记录学习、使用Spring Boot的过程。

主要内容是 Spring Boot 框架的一些特性、小功能的实现、常用框架的整合等。

Spring Boot版本：2.X

JDK版本：8

开发工具：IntelliJ IDEA

## A.hello

你好，Spring Boot。

- [**hello**：一个简单Spring Boot的HelloWorld示例](MD/Hello World.md)

## B.features

Spring Boot的一些特性。

- [**auto-configuration**：自动配置](MD/Creating Your Own Auto-configuration.md)
- [**externalized-configuration**：外部化配置](MD/Externalized Configuration.md)
- [**logging**：日志](MD/Logging.md)
- [**sending-email**：发送邮件](MD/Sending Email.md)
- [**testing**：测试](MD/Testing.md)

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
- [**p6spy**：记录任何Java应用程序的所有JDBC事务](MD/P6Spy use.md)
- [**ip2region**：离线IP地址定位库](MD/ip2region use.md)
- [**UserAgentUtils**：实时解析HTTP请求或分析日志文件并收集有关用户代理的信息](MD/UserAgentUtils use.md)

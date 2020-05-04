# UserAgentUtils使用

官网：[User-agent-utils | bitwalker.eu](https://www.bitwalker.eu/software/user-agent-utils)

Maven仓库：[Home » eu.bitwalker » UserAgentUtils](https://mvnrepository.com/artifact/eu.bitwalker/UserAgentUtils)

## 介绍

user-agent-utils Java库可用于实时解析HTTP请求或分析日志文件并收集有关用户代理的信息。

**官方告示**：该项目的生命周期已结束，将不再定期进行更新。作为替代方案，请查看 [Browscap](https://github.com/browscap/browscap/wiki/Using-Browscap) 项目及其实现。

虽然这里官方提示不再维护了，但还是整合使用下😂。

## 特征

快速检测：

- 超过150种不同的浏览器
- 7种不同的浏览器类型
- 超过60种不同的操作系统
- 6种不同的设备类型
- 9种不同的渲染引擎
- 9种不同的Web应用程序

## 整合使用

Maven项目中引入：

```xml
<!-- https://mvnrepository.com/artifact/eu.bitwalker/UserAgentUtils -->
<dependency>
    <groupId>eu.bitwalker</groupId>
    <artifactId>UserAgentUtils</artifactId>
    <version>1.21</version>
</dependency>
```

Java代码使用：

```java
UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
```

`UserAgent`中包含的信息：

- operatingSystem：请求的操作系统名称
- browser：请求的浏览器名称
- browserVersion：请求的浏览器版本号

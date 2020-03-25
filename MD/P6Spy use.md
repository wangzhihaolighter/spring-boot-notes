# P6Spy使用

Github仓库地址：[p6spy/p6spy](https://github.com/p6spy/p6spy)

Maven仓库地址：[Home » p6spy » p6spy](https://mvnrepository.com/artifact/p6spy/p6spy)

## 介绍

P6Spy是一个框架，它可以无缝拦截和记录数据库数据，而无需更改现有应用程序的代码。P6Spy发行版包括P6Log，该应用程序记录任何Java应用程序的所有JDBC事务。

它包括`P6Log`和`P6Outage`两个模块：

- P6Log ：用来拦截和记录任务应用程序的 JDBC 语句
- P6Outage ：专门用来检测和记录超过配置条件里时间的 SQL 语句

## 使用步骤

Maven项目中引入：

```xml
<dependency>
    <groupId>p6spy</groupId>
    <artifactId>p6spy</artifactId>
    <version>${p6spy.version}</version>
</dependency>
```

spring boot项目中应用P6Spy需要：
            
- 替换你的JDBC Driver为`com.p6spy.engine.spy.P6SpyDriver`
- 修改JDBC Url从 `jdbc:xxxx` 为 `jdbc:p6spy:xxxx`
- 配置`spy.properties`

配置`spy.properties`：

```properties
module.log=com.p6spy.engine.logging.P6LogFactory,com.p6spy.engine.outage.P6OutageFactory
# 自定义日志打印
logMessageFormat=com.example.p6spy.format.MyFormat
# 使用日志系统记录sql
appender=com.p6spy.engine.spy.appender.Slf4JLogger
## 配置记录Log例外
excludecategories=info,debug,result,batc,resultset
# 设置使用p6spy driver来做代理
deregisterdrivers=true
# 日期格式
dateformat=yyyy-MM-dd HH:mm:ss
# 实际驱动
driverlist=org.h2.Driver
# 是否开启慢SQL记录
outagedetection=true
# 慢SQL记录标准 秒
outagedetectioninterval=2
```

## 自定义日志打印

这里有两种方式。

一、实现 `MessageFormattingStrategy` 接口

```java
public class MyFormat implements MessageFormattingStrategy {

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        return !"".equals(sql.trim()) ?
                String.format(
                        "[ 当前时间：%s ] --- | SQL耗时：%s ms | 连接信息：%s - %s | 执行语句 : %s"
                        , LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        , elapsed
                        , category
                        , connectionId
                        , sql
                )
                :
                String.format(
                        "[ 当前时间：%s ] --- | 耗时：%s ms | 连接信息：%s - %s"
                        , LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        , elapsed
                        , category
                        , connectionId
                );
    }
}
```

二、在 `spy.properties` 中指定：

```properties
# 自定义日志打印
logMessageFormat=com.p6spy.engine.spy.appender.CustomLineFormat
customLogMessageFormat=当前时间：%(currentTime) | SQL耗时： %(executionTime) ms | 连接信息： %(category)-%(connectionId) | 执行语句： %(sql)
```

# 日志

Spring Boot 使用 [Commons Logging](https://commons.apache.org/proper/commons-logging/) 进行所有内部日志记录，但保留底层日志实现。

Spring Boot
为 [Java Util Logging](https://docs.oracle.com/javase/8/docs/api//java/util/logging/package-summary.html)，[Log4J2](https://logging.apache.org/log4j/2.x/)
和 [Logback](https://logback.qos.ch/) 提供了默认配置。 在每种情况下，记录器都预先配置为使用控制台输出，同时还提供可选的文件输出。

默认情况下，如果使用 `Starters`，则使用Logback进行日志记录。还包括适当的Logback路由，以确保使用`Java Util Logging`，`Commons Logging`，`Log4J`或`SLF4J`
的依赖库都能正常工作。

## 日志格式

Spring Boot 的默认日志输出类似于以下示例：

```log
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.1.7.RELEASE)

2019-09-04 15:57:13.329  INFO 17148 --- [           main] com.example.LoggingApplication           : Starting LoggingApplication on xxx with PID 17148 (<work path>)
2019-09-04 15:57:13.333  INFO 17148 --- [           main] com.example.LoggingApplication           : No active profile set, falling back to default profiles: default
2019-09-04 15:57:15.427  INFO 17148 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2019-09-04 15:57:15.490  INFO 17148 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2019-09-04 15:57:15.490  INFO 17148 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.22]
2019-09-04 15:57:15.911  INFO 17148 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2019-09-04 15:57:15.911  INFO 17148 --- [           main] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 2511 ms
2019-09-04 15:57:17.289  INFO 17148 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2019-09-04 15:57:17.738  INFO 17148 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2019-09-04 15:57:17.747  INFO 17148 --- [           main] com.example.LoggingApplication           : Started LoggingApplication in 5.238 seconds (JVM running for 9.214)
```

日志输出包含以下内容：

- 日期时间：精度为毫秒
- 日志级别：`ERROR`、`WARN`、`INFO`、`DEBUG`、`TRACE`
- 进程ID(Process ID)
- 一个 `---` 分离器来区分实际日志消息的开始
- 线程名称：用方括号括起来（可能会截断控制台输出）
- 记录器名称：这通常是源类名称（通常缩写）。
- 日志消息。

> Logback没有 `FATAL` 级别。它映射到 `ERROR`。

## 控制台输出

默认日志配置会在写入时将消息回显到控制台。

默认情况下，会记录 `ERROR-level`，`WARN-level` 和 `INFO-level` 消息。

可以通过使用 `--debug` 标志启动应用程序来启用`调试`模式。

```bash
>$ java -jar myapp.jar --debug
```

也可以在 `application.properties` 中指定 `debug=true`，启用调试模式。

同理，也可以通过使用 `--trace` 标志（或在 `application.properties` 中指定 `trace=true`）启动应用程序来启用“跟踪”模式。

## 彩色编码输出

如果终端支持ANSI，则可以使用颜色输出来提高可读性。也设置 `spring.output.ansi.enabled` 为支持的值以覆盖自动检测。

- NEVER：禁用ANSI-colored输出（默认项）
- DETECT：会检查终端是否支持ANSI，是的话就采用彩色输出（推荐项）
- ALWAYS：总是使用ANSI-colored格式输出，若终端不支持的时候，会有很多干扰信息，不推荐使用

日志配置文件中可以通过使用%clr转换字配置颜色编码。

在最简单的形式中，转换器根据日志级别为输出着色，如以下示例所示：

> %clr(%5p)

可以通过将其作为转换选项指定应使用的颜色或样式。例如，要使文本变为黄色，请使用以下设置：

> %clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){yellow}

支持以下颜色和样式：

- blue
- cyan
- faint
- green
- magenta
- red
- yellow

## 文件输出

默认情况下，Spring Boot仅记录到控制台，不会写入日志文件。

如果除了控制台输出之外还要写入日志文件，则需要在 `application.properties` 配置文件中设置 `logging.file` 或 `logging.path` 属性。

logging.file：写入指定的日志文件。名称可以是精确位置或相对于当前目录。

logging.path：写入指定的目录，名称可以是精确位置或相对于当前目录，日志文件名为 `spring.log`。

配置属性优先级：logging.file > logging.path。

日志文件在达到 10 MB 时会轮换，与控制台输出一样，默认情况下会记录ERROR-level， WARN-level和INFO-level消息。可以使用 `logging.file.max-size`
属性更改大小限制。除非 `logging.file.max-history` 已设置属性，否则以前轮换的文件将无限期归档。

## 日志级别

在 Spring 配置环境中（例如：application.properties）可以通过 `logging.level.<logger-name>=<level>` 指定日志级别，其中 level 为
TRACE，DEBUG，INFO，WARN，ERROR，FATAL或OFF之一。`logging.level.root` 可以配置root日志级别。

示例：

```properties
logging.level.root=WARN
logging.level.org.springframework.web=DEBUG
logging.level.org.hibernate=ERROR
```

## 日志组

Spring Boot 允许在 Spring 中定义日志记录组。

示例，将 `Tomcat` 组添加到 `application.properties` 中：

```properties
logging.group.tomcat = org.apache.catalina，org.apache.coyote，org.apache.tomcat
```

定义后，您可以使用一行更改组中所有记录器的级别：

```properties
logging.level.tomcat = TRACE
```

Spring Boot包含以下预定义的日志记录组，可以直接使用：

| Name | Loggers                                                                                 |
| ---- | --------------------------------------------------------------------------------------- |
| web  | `org.springframework.core.codec`, `org.springframework.http`, `org.springframework.web` |
| sql  | `org.springframework.jdbc.core`, `org.hibernate.SQL`                                    |

## 自定义日志配置

通过在类路径中包含适当的库来激活各种日志记录系统，并且可以通过在类路径的根目录中或在 Spring 配置环境中使用 `logging.config` 指定日志配置文件。

根据不同的日志记录系统，将加载以下文件：

| Logging System          | Customization                                                             |
| ----------------------- | ------------------------------------------------------------------------- |
| Logback                 | logback-spring.xml, logback-spring.groovy, logback.xml, or logback.groovy |
| Log4j2                  | log4j2-spring.xml or log4j2.xml                                           |
| JDK (Java Util Logging) | logging.properties                                                        |

额外信息：

- Spring 官方建议使用 `-spring` 变量进行日志记录配置（例如，logback-spring.xml而不是logback.xml）。如果使用标准配置位置，Spring无法完全控制日志初始化。
- Java Util Logging存在已知的类加载问题，从“executable jar”运行时会导致问题。如果可能的话，建议在从“executable jar”运行时避免使用它。

为了帮助进行自定义，一些其他属性从 Spring 转移 `Environment` 到 System 属性，如下表所述：

| Spring Environment                | System Property               | Comments                                                                        |
| --------------------------------- | ----------------------------- | ------------------------------------------------------------------------------- |
| logging.exception-conversion-word | LOG_EXCEPTION_CONVERSION_WORD | 记录异常时使用的转换字。                                                        |
| logging.file                      | LOG_FILE                      | 如果已定义，则在默认日志配置中使用它。                                          |
| logging.file.max-size             | LOG_FILE_MAX_SIZE             | 最大日志文件大小（如果启用了LOG_FILE）。（仅支持默认的Logback设置。）           |
| logging.file.max-history          | LOG_FILE_MAX_HISTORY          | 要保留的最大归档日志文件数（如果启用了LOG_FILE）。（仅支持默认的Logback设置。） |
| logging.path                      | LOG_PATH                      | 如果已定义，则在默认日志配置中使用它。                                          |
| logging.pattern.console           | CONSOLE_LOG_PATTERN           | 要在控制台上使用的日志模式（stdout）。（仅支持默认的Logback设置。）             |
| logging.pattern.dateformat        | LOG_DATEFORMAT_PATTERN        | 日志日期格式的Appender模式。（仅支持默认的Logback设置。）                       |
| logging.pattern.file              | FILE_LOG_PATTERN              | 要在文件中使用的日志模式（如果LOG_FILE已启用）。（仅支持默认的Logback设置。）   |
| logging.pattern.level             | LOG_LEVEL_PATTERN             | 呈现日志级别时使用的格式（默认%5p）。（仅支持默认的Logback设置。）              |
| PID                               | PID                           | 当前进程ID（如果可能，则在未定义为OS环境变量时发现）。                          |

所有受支持的日志记录系统在分析其配置文件时都可以查阅系统属性。有关 `spring-boot.jar` 示例，请参阅默认配置：

- [Logback](https://github.com/spring-projects/spring-boot/blob/master/spring-boot-project/spring-boot/src/main/resources/org/springframework/boot/logging/logback/defaults.xml)
- [Log4j 2](https://github.com/spring-projects/spring-boot/blob/master/spring-boot-project/spring-boot/src/main/resources/org/springframework/boot/logging/log4j2/log4j2.xml)
- [Java Util logging](https://github.com/spring-projects/spring-boot/blob/master/spring-boot-project/spring-boot/src/main/resources/org/springframework/boot/logging/java/logging-file.properties)

注意事项：

- 如果要在日志记录属性中使用占位符，则应使用 Spring Boot 的语法而不是底层框架的语法。值得注意的是，如果使用Logback，则应将其 `: (冒号空格)` 用作属性名称与其默认值之间的分隔符，而不是使用 `:-`。
- 您可以通过仅覆盖LOG_LEVEL_PATTERN（或logging.pattern.level使用Logback）将MDC和其他临时内容添加到日志行 。例如，如果使用 logging.pattern.level=user:
  %X{user} %5p，则默认日志格式包含“user”的MDC条目（如果存在）

## MDC

[MDC](https://logback.qos.ch/manual/mdc.html)：Mapped Diagnostic Context，简单来说就是日志的增强功能，如果配置了MDC，并添加了相应的key
value，就会在打日志的时候把key对应的value打印出来。

内部是用ThreadLocal来实现的，可以携带当前线程的context信息。

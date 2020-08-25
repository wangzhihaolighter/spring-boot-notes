# 日志

Spring Boot 使用 [Commons Logging](https://commons.apache.org/proper/commons-logging/) 进行所有内部日志记录，但保留底层日志实现。

Spring Boot 为 [Java Util Logging](https://docs.oracle.com/javase/8/docs/api//java/util/logging/package-summary.html)，[Log4J2](https://logging.apache.org/log4j/2.x/) 和 [Logback](https://logback.qos.ch/) 提供了默认配置。 在每种情况下，记录器都预先配置为使用控制台输出，同时还提供可选的文件输出。

默认情况下，如果使用 `Starters`，则使用Logback进行日志记录。还包括适当的Logback路由，以确保使用`Java Util Logging`，`Commons Logging`，`Log4J`或`SLF4J`的依赖库都能正常工作。

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

日志文件在达到 10 MB 时会轮换，与控制台输出一样，默认情况下会记录ERROR-level， WARN-level和INFO-level消息。可以使用 `logging.file.max-size` 属性更改大小限制。除非 `logging.file.max-history` 已设置属性，否则以前轮换的文件将无限期归档。

## 日志级别

在 Spring 配置环境中（例如：application.properties）可以通过 `logging.level.<logger-name>=<level>` 指定日志级别，其中 level 为 TRACE，DEBUG，INFO，WARN，ERROR，FATAL或OFF之一。`logging.level.root` 可以配置root日志级别。

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

Name | Loggers
-|-
web | `org.springframework.core.codec`, `org.springframework.http`, `org.springframework.web`
sql | `org.springframework.jdbc.core`, `org.hibernate.SQL`

## 自定义日志配置

通过在类路径中包含适当的库来激活各种日志记录系统，并且可以通过在类路径的根目录中或在 Spring 配置环境中使用 `logging.config` 指定日志配置文件。

根据不同的日志记录系统，将加载以下文件：

Logging System | Customization
-|-
Logback | logback-spring.xml, logback-spring.groovy, logback.xml, or logback.groovy
Log4j2 | log4j2-spring.xml or log4j2.xml
JDK (Java Util Logging) | logging.properties

额外信息：

- Spring 官方建议使用 `-spring` 变量进行日志记录配置（例如，logback-spring.xml而不是logback.xml）。如果使用标准配置位置，Spring无法完全控制日志初始化。
- Java Util Logging存在已知的类加载问题，从“executable jar”运行时会导致问题。如果可能的话，建议在从“executable jar”运行时避免使用它。

为了帮助进行自定义，一些其他属性从 Spring 转移 `Environment` 到 System 属性，如下表所述：

Spring Environment | System Property | Comments
--|--|--
logging.exception-conversion-word | LOG_EXCEPTION_CONVERSION_WORD | 记录异常时使用的转换字。
logging.file | LOG_FILE | 如果已定义，则在默认日志配置中使用它。
logging.file.max-size | LOG_FILE_MAX_SIZE | 最大日志文件大小（如果启用了LOG_FILE）。（仅支持默认的Logback设置。）
logging.file.max-history | LOG_FILE_MAX_HISTORY | 要保留的最大归档日志文件数（如果启用了LOG_FILE）。（仅支持默认的Logback设置。）
logging.path | LOG_PATH | 如果已定义，则在默认日志配置中使用它。
logging.pattern.console | CONSOLE_LOG_PATTERN | 要在控制台上使用的日志模式（stdout）。（仅支持默认的Logback设置。）
logging.pattern.dateformat | LOG_DATEFORMAT_PATTERN | 日志日期格式的Appender模式。（仅支持默认的Logback设置。）
logging.pattern.file | FILE_LOG_PATTERN | 要在文件中使用的日志模式（如果LOG_FILE已启用）。（仅支持默认的Logback设置。）
logging.pattern.level | LOG_LEVEL_PATTERN | 呈现日志级别时使用的格式（默认%5p）。（仅支持默认的Logback设置。）
PID | PID | 当前进程ID（如果可能，则在未定义为OS环境变量时发现）。

所有受支持的日志记录系统在分析其配置文件时都可以查阅系统属性。有关 `spring-boot.jar` 示例，请参阅默认配置：

- [Logback](https://github.com/spring-projects/spring-boot/blob/master/spring-boot-project/spring-boot/src/main/resources/org/springframework/boot/logging/logback/defaults.xml)
- [Log4j 2](https://github.com/spring-projects/spring-boot/blob/master/spring-boot-project/spring-boot/src/main/resources/org/springframework/boot/logging/log4j2/log4j2.xml)
- [Java Util logging](https://github.com/spring-projects/spring-boot/blob/master/spring-boot-project/spring-boot/src/main/resources/org/springframework/boot/logging/java/logging-file.properties)

注意事项：

- 如果要在日志记录属性中使用占位符，则应使用 Spring Boot 的语法而不是底层框架的语法。值得注意的是，如果使用Logback，则应将其 `: ` 用作属性名称与其默认值之间的分隔符，而不是使用 `:-`。
- 您可以通过仅覆盖LOG_LEVEL_PATTERN（或logging.pattern.level使用Logback）将MDC和其他临时内容添加到日志行 。例如，如果使用 logging.pattern.level=user:%X{user} %5p，则默认日志格式包含“user”的MDC条目（如果存在）

> [MDC](https://logback.qos.ch/manual/mdc.html)：Mapped Diagnostic Context，简单来说就是日志的增强功能，如果配置了MDC，并添加了相应的key value，就会在打日志的时候把key对应的value打印出来。
内部是用ThreadLocal来实现的，可以携带当前线程的context信息。

## Logback Extensions

Spring Boot包含许多Logback扩展，可以帮助进行高级配置。可以在 `logback-spring.xml` 配置文件中使用这些扩展名。

注意事项：

- 由于标准 `logback.xml` 配置文件加载过早，因此无法在其中使用扩展。您需要使用 `logback-spring.xml` 或定义 `logging.config` 属性。
- 扩展不能与 Logback 的[配置扫描](https://logback.qos.ch/manual/configuration.html#autoScan)一起使用。

## Profile-specific Configuration

`<springProfile>`标签可以选择性地包括或排除基于 Spring 对应环境部分的配置。

在 `<configuration>` 元素内的任何位置都支持这种 `Profile sections`。使用 `name` 属性指定接受哪个环境的配置。

`<springProfile>` 标记可包含一个 profile 的名称（例如 `staging`）或 `profile expression`表达式。例如，`A profile expression` 允许表达更复杂的 profile 逻辑：`production & (eu-central | eu-west)`。更详细的信息，请查阅[参考指南](https://docs.spring.io/spring/docs/5.1.9.RELEASE/spring-framework-reference/core.html#beans-definition-profiles-java)。

示例如下：

```xml
<springProfile name="staging">
  <!-- configuration to be enabled when the "staging" profile is active -->
</springProfile>

<springProfile name="dev | staging">
  <!-- configuration to be enabled when the "dev" or "staging" profiles are active -->
</springProfile>

<springProfile name="!production">
  <!-- configuration to be enabled when the "production" profile is not active -->
</springProfile>
```

## Environment Properties

`<springProperty>`标签允许在日志配置中声明 Spring 中的属性 Environment 以便在 Logback 中使用。可以通过这种方式在 Logback 配置中访问 `application.properties` 文件中的值。

`<springProperty>` 标签的工作方式与 Logback 的标准 `<property>` 标签类似。但是，value 可以指定 `source` 属性（来自Environment），而不是指定直接属性。

如果需要将属性存储在 `local` 范围之外的其他位置，则可以使用 `scope` 属性。

如果需要默认值（如果未在中设置属性 Environment），则可以使用该 `defaultValue` 属性。

以下示例显示如何使用在Logback中定义的属性：

```xml
<springProperty scope="context" name="fluentHost" source="myapp.fluentd.host" defaultValue="localhost"/>
<appender name="FLUENT" class="ch.qos.logback.more.appenders.DataFluentAppender">
  <remoteHost>${fluentHost}</remoteHost>
  ...
</appender>
```

注意事项：

- source的值必须满足短横线隔开式(The source must be specified in kebab case (such as my.property-name). However, properties can be added to the Environment by using the relaxed rules.)

## Spring Boot中使用Logback

Logback官方网站：[Logback](https://logback.qos.ch/)。

一个在webapp程序中设置SLF4J和LOGBack的教程：[教程](https://wiki.base22.com/btg/how-to-setup-slf4j-and-logback-in-a-web-app-fast-35488048.html)。

在 `spring-boot-starter` 依赖中包含 `spring-boot-starter-logging` 依赖，以下是 `spring-boot-starter-logging` pom文件中的依赖：

```xml
<dependencies>
  <dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.2.3</version>
    <scope>compile</scope>
  </dependency>
  <dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-to-slf4j</artifactId>
    <version>2.11.2</version>
    <scope>compile</scope>
  </dependency>
  <dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>jul-to-slf4j</artifactId>
    <version>1.7.26</version>
    <scope>compile</scope>
  </dependency>
</dependencies>
```

可以看到，只要引入任意一个包含`spring-boot-starter`的依赖，就已经包含了 slf4j 和 logback 依赖，不需要自己去引入。

### 一个logback的示例配置

Spring 官方推荐日志框架的配置文件使用 xxx-spring.xml 这种形式，因为这种形式可以使用 `<springProperty/>` 这个标签获取 Spring 环境配置。

这里贴一个 logback-spring.xml 的示例配置文件：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--
    日志级别从低到高分为TRACE < DEBUG < INFO < WARN < ERROR < FATAL，如果设置为WARN，则低于WARN的信息都不会输出
    scan:当此属性设置为true时，配置文件如果发生改变，将会被重新加载，默认值为true
    scanPeriod:设置监测配置文件是否有修改的时间间隔，如果没有给出时间单位，默认单位是毫秒。当scan为true时，此属性生效。默认的时间间隔为1分钟。
    debug:当此属性设置为true时，将打印出logback内部日志信息，实时查看logback运行状态。默认值为false。
-->
<configuration  scan="true" scanPeriod="10 seconds">

    <!-- 引入默认的logback配置文件 -->
    <include resource="org/springframework/boot/logging/logback/defaults.xml"/>

    <!--
        Environment Properties
        通过定义的值会被插入到logger上下文中，定义变量后，可以使“${}”来使用变量。
    -->
    <springProperty scope="context" name="springAppName" source="spring.application.name"/>
    <springProperty scope="context" name="LOG_HOME" source="logging.file.path"/>
    <property name="LOG_FILE" value="${springAppName}"/>

    <!-- 彩色日志 -->
    <!-- 彩色日志依赖的渲染类 -->
    <conversionRule conversionWord="clr"
                    converterClass="org.springframework.boot.logging.logback.ColorConverter"/>
    <conversionRule conversionWord="wex"
                    converterClass="org.springframework.boot.logging.logback.WhitespaceThrowableProxyConverter"/>
    <conversionRule conversionWord="wEx"
                    converterClass="org.springframework.boot.logging.logback.ExtendedWhitespaceThrowableProxyConverter"/>

    <!--
        %p:输出优先级，即DEBUG,INFO,WARN,ERROR,FATAL
        %r:输出自应用启动到输出该日志讯息所耗费的毫秒数
        %t:输出产生该日志事件的线程名
        %f:输出日志讯息所属的类别的类别名
        %c:输出日志讯息所属的类的全名
        %d:输出日志时间点的日期或时间，指定格式的方式： %d{yyyy-MM-dd HH:mm:ss}
        %l:输出日志事件的发生位置，即输出日志讯息的语句在他所在类别的第几行。
        %m:输出代码中指定的讯息，如log(message)中的message
        %n:输出一个换行符号
    -->

    <!-- 彩色日志格式 -->
    <property name="CONSOLE_LOG_PATTERN"
              value="${CONSOLE_LOG_PATTERN:-%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- }){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}}"/>

    <!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度 %msg：日志消息，%n是换行符-->
    <property name="pattern"
              value="%d{yyyyMMdd:HH:mm:ss.SSS} [%thread] %-5level  %msg%n"/>

    <!--
        Appender: 设置日志信息的去向,常用的有以下几个
            ch.qos.logback.core.ConsoleAppender (控制台)
            ch.qos.logback.core.rolling.RollingFileAppender (文件大小到达指定尺寸的时候产生一个新文件)
            ch.qos.logback.core.FileAppender (文件)
    -->

    <!-- Appender to log to console -->
    <appender name="console" class="ch.qos.logback.core.ConsoleAppender">
        <!-- ThresholdFilter:阀值过滤器，过滤阈值水平以下的事件。 -->
        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <!-- Minimum logging level to be presented in the console logs-->
            <level>DEBUG</level>
        </filter>
        <!-- 对记录事件进行格式化 -->
        <encoder>
            <pattern>${CONSOLE_LOG_PATTERN}</pattern>
            <charset>UTF-8</charset>
        </encoder>
    </appender>

    <!-- Appender to log to file -->
    <appender name="flatFile" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 被写入的文件名，可以是相对目录，也可以是绝对目录，如果上级目录不存在会自动创建 -->
        <file>${LOG_HOME}/${LOG_FILE}.log</file>
        <!-- 当发生滚动时，决定RollingFileAppender的行为，涉及文件移动和重命名。属性class定义具体的滚动策略类 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!-- 必要节点，包含文件名及"%d"转换符，"%d"可以包含一个java.text.SimpleDateFormat指定的时间格式，默认格式是 yyyy-MM-dd -->
            <fileNamePattern>${LOG_HOME}/${LOG_FILE}.log.%i.%d{yyyy-MM-dd}.gz</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>20MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
            <!-- 可选节点，控制保留的归档文件的最大数量，超出数量就删除旧文件。假设设置每个月滚动，如果是6，则只保存最近6个月的文件，删除之前的旧文件 -->
            <maxHistory>7</maxHistory>
        </rollingPolicy>
        <encoder>
            <pattern>${pattern}</pattern>
            <charset>UTF-8</charset>
        </encoder>
        <!-- LevelFilter： 级别过滤器，根据日志级别进行过滤 -->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>INFO</level>
            <!-- 用于配置符合过滤条件的操作 ACCEPT：日志会被立即处理，不再经过剩余过滤器 -->
            <onMatch>ACCEPT</onMatch>
            <!-- 用于配置不符合过滤条件的操作 DENY：日志将立即被抛弃不再经过其他过滤器 -->
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>

    <!-- Appender to log to file in a JSON format -->
    <appender name="logstash" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${LOG_HOME}/${LOG_FILE}.json</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${LOG_HOME}/${LOG_FILE}.json.%d{yyyy-MM-dd}.gz</fileNamePattern>
            <maxHistory>7</maxHistory>
        </rollingPolicy>
        <encoder class="net.logstash.logback.encoder.LoggingEventCompositeJsonEncoder">
            <providers>
                <timestamp>
                    <timeZone>GMT+8</timeZone>
                </timestamp>
                <pattern>
                    <pattern>
                        {
                        "severity": "%level",
                        "service": "${springAppName:-}",

                        <!--sleuth自动填充参数，用于日志追踪-->
                        "trace": "%X{X-B3-TraceId:-}",
                        "span": "%X{X-B3-SpanId:-}",
                        "parent": "%X{X-B3-ParentSpanId:-}",
                        "exportable": "%X{X-Span-Export:-}",
                        <!--sleuth自动填充参数，用于日志追踪-->

                        <!--自定义参数，过滤器拦截请求填充-->
                        "url": "%X{url:-}",
                        "ip": "%X{ip:-}",
                        "userId": "%X{userId:-}",
                        <!--自定义参数，过滤器拦截请求填充-->

                        "pid": "${PID:-}",
                        "thread": "%thread",
                        "class": "%logger{40}",
                        "column": "%F:%L",
                        "rest": "%message"
                        }
                    </pattern>
                </pattern>
            </providers>
        </encoder>
    </appender>

    <!--
        用来设置某一个包或者具体的某一个类的日志打印级别、以及指定<appender>。
        <logger>仅有一个name属性，一个可选的level和一个可选的additivity属性
        name:
            用来指定受此logger约束的某一个包或者具体的某一个类。
        level:
            用来设置打印级别，大小写无关：TRACE, DEBUG, INFO, WARN, ERROR, ALL 和 OFF，
        additivity:
            默认是true，子Logger是否继承父Logger的输出源（appender）的标志位
        <logger>可以包含零个或多个<appender-ref>元素，标识这个appender将会添加到这个logger
    -->
    <logger name="java.sql" level="info" additivity="false">
        <level value="info" />
        <appender-ref ref="console"/>
        <appender-ref ref="flatFile"/>
        <appender-ref ref="logstash"/>
    </logger>

    <!--
        也是<logger>元素，但是它是根logger。默认debug
        level:用来设置打印级别，大小写无关：TRACE, DEBUG, INFO, WARN, ERROR, ALL 和 OFF，
        <root>可以包含零个或多个<appender-ref>元素，标识这个appender将会添加到这个logger。
    -->
    <root level="INFO">
        <appender-ref ref="console"/>
        <appender-ref ref="flatFile"/>
        <appender-ref ref="logstash"/>
    </root>
</configuration>
```

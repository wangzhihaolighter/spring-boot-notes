# h2database使用

## 资料

- h2数据库官网：[h2database.com/html/main.html](https://www.h2database.com/html/main.html)

- h2数据库 Maven 仓库：[Home » com.h2database » h2](https://mvnrepository.com/artifact/com.h2database/h2)

## H2数据库介绍

H2是一个开源的嵌入式（非嵌入式设备）数据库引擎，它是一个用Java开发的类库，可直接嵌入到应用程序中，与应用程序一起打包发布，不受平台限制。

H2不仅提供了JDBC、ODBC访问接口，还提供了非常友好的基于web的数据库管理界面。

## 对比其他开源数据库

![示例](/IMG/h2database/001.png)

- Pure Java : 纯Java
- Memory Mode : 支持内存模式
- Encrypted Database : 支持数据库加密
- ODBC Driver : 支持ODBC驱动
- Fulltext Search : 支持全文检索
- Multi Version Concurrency : 支持多版本并发控制
- Footprint (embedded) : 嵌入式占用空间
- Footprint (client) : 客户端占用空间

## H2数据库连接方式

H2数据库支持如下三种连接方式：

- 嵌入式模式：本地JDBC连接
- 服务器模式：JDBC或基于tcp/ip的ODBC远程连接
- 混合模式：本地或远程同时连接

注：三种模式都支持内存、持久化到文件两种数据存储方式。三种模式对同时开启的数据库数量和数据库连接数量没有限制。

### 嵌入式模式

在嵌入式模式下，应用程序使用JDBC从同一JVM中打开数据库。这是最快和最简单的连接模式。缺点是数据库只能随时在一个虚拟机（和类加载器）中打开。与所有模式一样，持久性数据库和内存数据库均受支持。并发打开的数据库数或打开的连接数没有限制。

在嵌入式模式下，I / O操作可以由执行SQL命令的应用程序线程执行。应用程序可能不会中断这些线程，它可能导致数据库损坏，因为JVM在线程中断期间会关闭I / O句柄。考虑其他方法来控制应用程序的执行。当可能发生中断时，可以将 `async:` 文件系统用作解决方法，但不能保证完全安全。建议改用客户端-服务器模型，客户端可能会中断自己的线程。

![example](/IMG/h2database/002.png)

### 服务器模式

当使用服务器模式（有时称为远程模式或客户端/服务器模式）时，应用程序使用JDBC或ODBC API远程打开数据库。服务器需要在同一台或另一台虚拟机中或在另一台计算机上启动。通过连接到该服务器，许多应用程序可以同时连接到同一数据库。在内部，服务器进程以嵌入式模式打开数据库。

服务器模式比嵌入式模式慢，因为所有数据都是通过TCP / IP传输的。与所有模式一样，持久性数据库和内存数据库均受支持。每个服务器并发打开的数据库数或打开的连接数没有限制。

![example](/IMG/h2database/003.png)

### 混合模式

混合模式是嵌入式和服务器模式的组合。连接到数据库的第一个应用程序以嵌入式模式执行此操作，但是还启动服务器，以便其他应用程序（在不同进程或虚拟机中运行）可以同时访问相同的数据。本地连接的速度就好像仅在嵌入式模式下使用数据库一样快，而远程连接要慢一些。

可以从应用程序内部（使用服务器API）启动或停止服务器，也可以自动（自动混合模式）启动和停止服务器。使用自动混合模式时，所有要连接到数据库的客户端（无论是本地连接还是远程连接）都可以使用完全相同的数据库URL进行连接。

![example](/IMG/h2database/004.png)

### H2数据库JDBC URL格式

H2数据库支持多种连接方式和连接设置，连接URL格式如下，URL中的设置大小写不敏感。

![example](/IMG/h2database/007.png)

## H2连接字符串参数

连接字符串参数:

- DB_CLOSE_DELAY：延迟关闭数据库。值-1表示数据库不会自动关闭。值0是默认值，表示在最后一个连接关闭时数据库已关闭。
- DB_CLOSE_ON_EXIT：VM退出时不要关闭数据库
- MODE=MySQL：兼容模式，H2兼容多种数据库，该值可以为：DB2、Derby、HSQLDB、MSSQLServer、MySQL、Oracle、PostgreSQL
- AUTO_RECONNECT=TRUE：连接丢失后自动重新连接
- AUTO_SERVER=TRUE：启动自动混合模式，允许开启多个连接，该参数不支持在内存中运行模式
- TRACE_LEVEL_SYSTEM_OUT、TRACE_LEVEL_FILE：输出跟踪日志到控制台或文件， 取值0为OFF，1为ERROR（默认值），2为INFO，3为DEBUG
- SET TRACE_MAX_FILE_SIZE mb：设置跟踪文件的最大大小，默认为16M

连接示例：

> jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;MODE=MYSQL

## 整合使用

项目中引入Maven依赖，嵌入式模式

```xml
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```

application.properties配置文件中添加H2配置：

```properties
# h2
## MODE=MySQL：兼容模式，H2兼容多种数据库，该值可以为：DB2、Derby、HSQLDB、MSSQLServer、MySQL、Oracle、PostgreSQL
spring.h2.console.enabled=true
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.url=jdbc:h2:mem:test;MODE=MYSQL
spring.datasource.username=sa
spring.datasource.password=
```

h2 web数据库管理页面默认路径为`/h2-console`，通过 <http://localhost:8080/h2-console> 可以访问。

注意修改`JDBC URL`为配置的url连接后连接h2数据库。

![example](/IMG/h2database/005.png)

![example](/IMG/h2database/006.png)

## 参考

- [内存数据库－H2简介与实践](https://blog.csdn.net/xktxoo/article/details/78014739)

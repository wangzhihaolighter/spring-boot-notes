# tomcat access log

## 常用配置

- enabled，是否启用access log
- directory，access文件输出路径
- pattern，日志输出格式
- rotate，是否启用日志轮转。默认为true。这个参数决定是否需要切换切换日志文件，如果被设置为false，则日志文件不会切换，即所有文件打到同一个日志文件中，并且file-date-format参数也会被忽略

## pattern 配置

**配置说明**

- %a - Remote IP address，远程ip地址，注意不一定是原始ip地址，中间可能经过nginx等的转发
- %A - Local IP address，本地ip
- %b - Bytes sent, excluding HTTP headers, or '-' if no bytes were sent
- %B - Bytes sent, excluding HTTP headers
- %h - Remote host name (or IP address if enableLookups for the connector is false)，远程主机名称(如果resolveHosts为false则展示IP)
- %H - Request protocol，请求协议
- %l - Remote logical username from identity (always returns '-')
- %m - Request method，请求方法（GET，POST）
- %p - Local port，接受请求的本地端口
- %q - Query string (prepended with a '?' if it exists, otherwise an empty string
- %r - First line of the request，HTTP请求的第一行（包括请求方法，请求的URI）
- %s - HTTP status code of the response，HTTP的响应代码，如：200,404
- %S - User session ID
- %t - Date and time, in Common Log Format format，日期和时间，Common Log Format格式
- %u - Remote user that was authenticated
- %U - Requested URL path
- %v - Local server name
- %D - Time taken to process the request, in millis，处理请求的时间，单位毫秒
- %T - Time taken to process the request, in seconds，处理请求的时间，单位秒
- %I - current Request thread name (can compare later with stack traces)，当前请求的线程名，可以和打印的log对比查找问题

Access log 也支持将cookie、header、session或者其他在ServletRequest中的对象信息打印到日志中。

其配置遵循Apache配置的格式（{xxx}指值的名称）

- %{xxx}i for incoming headers，request header信息
- %{xxx}o for outgoing response headers，response header信息
- %{xxx}c for a specific cookie
- %{xxx}r xxx is an attribute in the ServletRequest
- %{xxx}s xxx is an attribute in the HttpSession
- %{xxx}t xxx is an enhanced SimpleDateFormat pattern (see Configuration Reference document for details on supported time patterns)

Access log内置了两个日志格式模板，可以直接指定pattern为模板名称

- common - '%h %l %u %t "%r" %s %b' 依次为：远程主机名称，远程用户名，被认证的远程用户，日期和时间，请求的第一行，response code，发送的字节数
- combined - '%h %l %u %t "%r" %s %b "%{Referer}i" "%{User-Agent}i"' 依次为：远程主机名称，远程用户名，被认证的远程用户，日期和时间，请求的第一行，response code，发送的字节数，request header 的 Referer 信息，request header 的 User-Agent 信息。

除了内置的模板，常用的配置如下

- '%t %a "%r" %s (%D ms)'，依次为：日期和时间，请求来自的IP（不一定是原始IP），请求第一行，response code，响应时间（毫秒）
- '%t [%I] %{X-Forwarded-For}i %a %r %s (%D ms)' 日期和时间，线程名，原始IP，请求来自的IP（不一定是原始IP），请求第一行，response code，响应时间（毫秒）

HTTP请求头X-Forwarded-For，它是一个 HTTP 扩展头部，用来表示 HTTP 请求端真实 IP，其格式为：X-Forwarded-For: client, proxy1, proxy2，其中的值通过一个逗号+空格把多个IP地址区分开，最左边（client）是最原始客户端的IP地址，代理服务器每成功收到一个请求，就把请求来源IP地址添加到右边。

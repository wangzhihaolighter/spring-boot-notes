## 10-more
#### demo-tomcat-optimize:spring-boot内嵌tomcat调优
1. Tomcat启动命令行中的优化参数即JVM优化
2. Tomcat容器自身参数的优化

#### ldap:使用LDAP来统一管理用户信息（LDAP:轻量级目录访问协议）
1. 使用嵌入式ldap服务端测试
1. 使用ldap完成常规的CRUD操作

#### aop:面向切面编程(Aspect Oriented Programming)
1. 基本注解说明
1. 实现一个切面的步骤
1. spring aop支持的切入点表达式
1. spring aop支持的通知
1. 具体实现切面完成功能：1.web请求日志记录 2.token权限拦截 3.service方法执行时间记录

#### filter,配置过滤器filter
1. filter过滤器的概念
1. 如何写一个filter
1. 配置filter的方式及如何调整加载顺序

#### interceptor,配置拦截器interceptor
1. 拦截器原理 - 动态代理,jdk实现和cglib实现差异
1. 如何写一个拦截器
1. 配置filter的方式及如何调整加载顺序

#### 测试aspect、filter、interceptor的执行顺序
1. 执行顺序: filter(do filter start) -> interceptor(preHandle) -> aspect start -> aspect end -> interceptor(postHandle) -> interceptor(afterCompletion) -> filter(do filter end)
1. filter原理基于servlet
1. interceptor基于动态代理
1. aop，基于AspectJ，修改切面方法前后的源代码
    1. AspectJ是一个代码生成工具（Code Generator）。
    1. AspectJ语法就是用来定义代码生成规则的语法。

#### async:使用@Async实现异步调用
1. 配置@EnableAsync,开启异步注解
1. spring线程池配置
1. 如何实现异步调用：bean中方法上使用@Async，并指定线程池
1. 异步方法返回结果：Future接口方法说明
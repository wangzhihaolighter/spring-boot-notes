# 10-more
说明：没有具体的分类，用于记录一些原理性的东西，或者用spring boot实现一些其他功能。

## container：容器
- embed-tomcat-optimize，Spring Boot内嵌tomcat调优
    - Tomcat启动命令行中的参数优化，即JVM优化
    - Tomcat容器自身参数的优化

## protocol：协议
- ldap，使用LDAP来统一管理用户信息（LDAP:轻量级目录访问协议）
    - 使用嵌入式ldap数据库测试
    - 使用spring ldap完成常规的CRUD操作

## aop：面向切面编程(Aspect Oriented Programming)
- spring aop，spring的aop实现
    - 基本注解说明
    - 实现一个切面的步骤
    - spring aop支持的切入点表达式
    - spring aop支持的通知
    - 具体实现切面完成功能：-web请求日志记录 2.token权限拦截 3.service方法执行时间记录
- interceptor,spring的拦截器实现interceptor
    - 拦截器原理 - 动态代理,jdk实现和cglib实现差异
    - 如何写一个拦截器
- filter,配置过滤器filter
    - filter过滤器的概念
    - 如何写一个filter
    - 配置filter的方式及如何调整加载顺序
- execution-order，测试spring aop、filter、spring interceptor的执行顺序
    - order均设为1，interceptor先注册HandlerInterceptor再注册WebRequestInterceptor，默认执行顺序: filter(do filter start) -> HandlerInterceptor(preHandle) -> WebRequestInterceptor(preHandle) -> aspect start -> aspect end -> WebRequestInterceptor(postHandle) -> HandlerInterceptor(postHandle) -> WebRequestInterceptor(afterCompletion) -> HandlerInterceptor(afterCompletion) -> filter(do filter end)
    - filter原理基于servlet
    - interceptor基于动态代理
    - aop，基于AspectJ，修改切面方法前后的源代码
        - AspectJ是一个代码生成工具（Code Generator）。
        - AspectJ语法就是用来定义代码生成规则的语法。
    - 其他测试：
        - 调整order后， 执行顺序依旧为 filter-> interceptor -> aspect
        - interceptor的HandlerInterceptor，WebRequestInterceptor的顺序注册时的order决定的，WebRequestInterceptor本质是由适配器WebRequestHandlerInterceptorAdapter转换为HandlerInterceptor添加进拦截器链中，若order一致，由添加顺序决定，本质是list中对象排序

## async：异步调用
- spring-async，使用`@Async`实现异步调用
    - 配置`@EnableAsync`,开启异步注解
    - spring线程池配置
    - 如何实现异步调用：bean中方法上使用`@Async`，并指定线程池
    - 异步方法返回结果：Future接口方法说明
- async-transaction，业务层事务提交后执行异步调用

## schedule：任务调度
- spring-annotation-schedule，使用`@Scheduled`创建定时任务
    - 启用定时任务的配置
    - 注解说明
    - 创建定时任务
    - 默认配置下所有任务由同一线程执行造成挤占如何解决？ - 提供自定义的任务线程池
- dynamic-spring-scheduler，使用spring定时任务实现动态定时任务
    - 创建定时任务表，从表中获取定时任务信息
    - 定时查询定时任务表中数据，更新定时任务配置，实现动态管理
- dynamic quartz scheduler:整合quartz，实现可动态修改时间定时任务
    - spring tasks不支持年定位，创建更完整的定时任务，需整合Quartz
    - 动态配置定时任务,基于http访问
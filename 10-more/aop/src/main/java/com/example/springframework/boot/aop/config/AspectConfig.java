package com.example.springframework.boot.aop.config;

import com.example.springframework.boot.aop.aspect.LogAspect;
import com.example.springframework.boot.aop.aspect.ServiceCostTimeAspect;
import com.example.springframework.boot.aop.aspect.TokenAuthAspect;
import com.example.springframework.boot.aop.interceptor.ClassifiedInterceptor;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AspectConfig {
    /*
    注解说明：
        @EnableAspectJAutoProxy:表示开启AOP代理自动配置

    配置一个切面的步骤：
        1.定义一个切入点
        2.通过连接点切入

    实现一个切面的方式：
    1.类上添加@Aspect，配置切入点，通过连接点切入
    2.类继承对应的接口
        MethodBeforeAdvice 调用方法前执行
        AfterReturningAdvice 调用方法后执行
        MethodInterceptor 调用方法前后都执行，实现环绕结果
        ...其他

    Spring AOP支持在切入点表达式中使用如下的切入点指示符：　　　　
        execution - 匹配方法执行的连接点，这是你将会用到的Spring的最主要的切入点指示符。
        within - 限定匹配特定类型的连接点（在使用Spring AOP的时候，在匹配的类型中定义的方法的执行）。
        this - 限定匹配特定的连接点（使用Spring AOP的时候方法的执行），其中bean reference（Spring AOP 代理）是指定类型的实例。
        target - 限定匹配特定的连接点（使用Spring AOP的时候方法的执行），其中目标对象（被代理的应用对象）是指定类型的实例。
        args - 限定匹配特定的连接点（使用Spring AOP的时候方法的执行），其中参数是指定类型的实例。
        @target - 限定匹配特定的连接点（使用Spring AOP的时候方法的执行），其中正执行对象的类持有指定类型的注解。
        @args - 限定匹配特定的连接点（使用Spring AOP的时候方法的执行），其中实际传入参数的运行时类型持有指定类型的注解。
        @within - 限定匹配特定的连接点，其中连接点所在类型已指定注解（在使用Spring AOP的时候，所执行的方法所在类型已指定注解）。
        @annotation - 限定匹配特定的连接点（使用Spring AOP的时候方法的执行），其中连接点的主题持有指定的注解。
        其中execution使用最频繁，即某方法执行时进行切入。定义切入点中有一个重要的知识，即切入点表达式，我们一会在解释怎么写切入点表达式。

     spring aop支持的通知：
        @Before：前置通知：在某连接点之前执行的通知，但这个通知不能阻止连接点之前的执行流程（除非它抛出一个异常）。
        @AfterReturning ：后置通知：在某连接点正常完成后执行的通知，通常在一个匹配的方法返回的时候执行(可以在后置通知中绑定返回值)
        @AfterThrowing:异常通知：在方法抛出异常退出时执行的通知。
        @After 最终通知。当某连接点退出的时候执行的通知（不论是正常返回还是异常退出）。
        @Around：环绕通知：包围一个连接点的通知，如方法调用。这是最强大的一种通知类型。环绕通知可以在方法调用前后完成自定义的行为。它也会选择是否继续执行连接点或直接返回它自己的返回值或抛出异常来结束执行。

        通知参数：
            任何通知方法可以将第一个参数定义为org.aspectj.lang.JoinPoint类型
            环绕通知需要定义第一个参数为ProceedingJoinPoint类型，它是 JoinPoint 的一个子类
            JoinPoint接口提供了一系列有用的方法，比如:
                getArgs()（返回方法参数）
                getThis()（返回代理对象）
                getTarget()（返回目标）
                getSignature()（返回正在被通知的方法相关信息)
                toString()（打印出正在被通知的方法的有用信息）

     切入点表达式：
        切入点表达式的格式：execution([可见性] 返回类型 [声明类型].方法名(参数) [异常])
        其中[]中的为可选，其他的还支持通配符的使用:
            *：匹配所有字符
            ..：一般用于匹配多个包，多个参数
            +：表示类及其子类
            运算符有：&&、||、!

     切入点表达式关键词：
        execution：用于匹配子表达式。
        within：用于匹配连接点所在的Java类或者包。
        this：用于向通知方法中传入代理对象的引用。
        target：用于向通知方法中传入目标对象的引用。
        args：用于将参数传入到通知方法中。
        @within ：用于匹配在类一级使用了参数确定的注解的类，其所有方法都将被匹配。
        @target ：和@within的功能类似，但必须要指定注解接口的保留策略为RUNTIME。
        @annotation ：匹配连接点被它参数指定的Annotation注解的方法。也就是说，所有被指定注解标注的方法都将匹配。
        bean：通过受管Bean的名字来限定连接点所在的Bean。该关键词是Spring2.5新增的。

    另一种实现方式:实现aop interceptor,通常实现的是下面两种:
        MethodInterceptor:方法拦截器
        ConstructorInterceptor:构造拦截器，不常用（还没用过。。。）
     */

    @Bean
    public LogAspect logAspect() {
        return new LogAspect();
    }

    @Bean
    public TokenAuthAspect tokenAuthAspect() {
        return new TokenAuthAspect();
    }

    @Bean
    public ServiceCostTimeAspect serviceCostTimeAspect() {
        return new ServiceCostTimeAspect();
    }

    /*
    另一种实现方式:实现interceptor
     */

    @Autowired
    private ClassifiedInterceptor classifiedInterceptor;

    @Bean
    public Advisor classifiedAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        //两种表达式的写法均可
        pointcut.setExpression("execution(public * com.example.springframework.boot.aop.web..*.*(..))");
        pointcut.setExpression("com.example.springframework.boot.aop.interceptor.ClassifiedInterceptor.pointcut()");
        DefaultPointcutAdvisor classifiedAdvisor = new DefaultPointcutAdvisor(pointcut, classifiedInterceptor);
        classifiedAdvisor.setOrder(4);
        return classifiedAdvisor;
    }
}

package com.example.springframework.boot.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * token切面，auth接口请求头中需要携带指定token
 */
@Slf4j
@Order(2)
@Aspect
public class TokenAuthAspect {

    //假设这是当前访问所需的密钥
    private static final String SECRET_KEY = "123456";

    /**
     * 切入点 - web层
     */
    @Pointcut("execution(public * com.example.springframework.boot.aop.web..*.*(..))")
    public void tokenAuth() {
    }

    /**
     * 通过连接点切入 - before，记录请求
     */
    @Before("tokenAuth()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //获取相对访问url路径
        String requestURL = request.getRequestURL().toString();
        int index = requestURL.indexOf("/", 7);
        String url = requestURL.substring(index);
        log.info("[TOKEN AUTH] : url ->" + url);

        //获取token
        String token = request.getHeader("TOKEN");
        log.info("[TOKEN AUTH] : request token -> ->" + token);
        if (isAuthUrl(url) && !authToken(token)) {
            //token认证不通过
            throw new RuntimeException("[TOKEN AUTH] : access denied");
        }
        log.info("[TOKEN AUTH] : access success");
    }

    /**
     * 校验url是否需要认证
     *
     * @param url 相对访问路径
     * @return 是否需要认证
     */
    private boolean isAuthUrl(String url) {
        return url.startsWith("/auth");
    }

    /**
     * 校验认证token
     *
     * @param token 令牌
     * @return 是否认证成功
     */
    private boolean authToken(String token) {
        return SECRET_KEY.equals(token);
    }


}

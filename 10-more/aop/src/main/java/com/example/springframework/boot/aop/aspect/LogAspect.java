package com.example.springframework.boot.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * 日志切面，记录web层访问日志
 */
@Slf4j
@Order(1)
@Aspect
public class LogAspect {

    /**
     * 切入点 - web层
     */
    @Pointcut("execution(public * com.example.springframework.boot.aop.web..*.*(..))")
    public void webLog() {
    }

    /**
     * 通过连接点切入 - before，记录请求
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        log.info("[WEB LOG] URL : " + request.getRequestURL().toString());
        log.info("[WEB LOG] HTTP_METHOD : " + request.getMethod());
        log.info("[WEB LOG] IP : " + getIpAddr(request));
        log.info("[WEB LOG] CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("[WEB LOG] ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * 通过连接点切入 - AfterReturning，记录响应
     */
    @AfterReturning(pointcut = "webLog()", returning = "response")
    public void doAfterReturning(Object response) throws Throwable {
        // 收集返回结果
        log.info("[WEB LOG] RESPONSE : " + response);
    }

    /**
     * 通过连接点切入 - Around，记录用时
     */
    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        //start time
        long startTime = System.currentTimeMillis();
        Object proceed = point.proceed();
        long endTime = System.currentTimeMillis();
        log.info("[WEB LOG] SPEND TIME (ms) : " + (endTime - startTime));
        return proceed;
    }

    /**
     * 获取当前网络ip
     *
     * @param request 请求
     * @return ip
     */
    private String getIpAddr(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if ("127.0.0.1".equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress)) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                    ipAddress = inet.getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        //"***.***.***.***".length() = 15
        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

}

package com.example.redis.config.aspect;

import com.example.redis.config.annotation.IpLimit;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/** IP限流 */
@Slf4j
@Aspect
@Component
public class IpLimitAspect {

  private final RedisTemplate<String, Object> redisTemplate;

  public IpLimitAspect(RedisTemplate<String, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  @Pointcut("@annotation(com.example.redis.config.annotation.IpLimit)")
  public void pointcut() {}

  @Around("pointcut()")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    ServletRequestAttributes attributes =
        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method signatureMethod = signature.getMethod();
    IpLimit ipLimit = signatureMethod.getAnnotation(IpLimit.class);
    String key = ipLimit.key() + "_" + getIp(request);

    // 执行 lua 脚本
    DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
    // 指定 lua 脚本
    redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("redis/limit.lua")));
    // 指定返回类型
    redisScript.setResultType(Long.class);
    // 参数一：redisScript，参数二：key列表，参数三：arg（可多个）
    Long result =
        redisTemplate.execute(redisScript, Collections.singletonList(key), ipLimit.duration());

    // 访问次数受限制
    if (result != null && result > ipLimit.maxRate()) {
      throw new RuntimeException("访问次数受限制");
    } else {
      log.info("第{}次访问key为 {}，描述为 [{}] 的接口", result, key, ipLimit.name());
      return joinPoint.proceed();
    }
  }

  /** 获取ip地址 */
  public static String getIp(HttpServletRequest request) {
    String ip = request.getHeader("x-forwarded-for");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    String comma = ",";
    String localhost = "127.0.0.1";
    if (ip.contains(comma)) {
      ip = ip.split(",")[0];
    }
    if (localhost.equals(ip)) {
      // 获取本机真正的ip地址
      try {
        ip = InetAddress.getLocalHost().getHostAddress();
      } catch (UnknownHostException e) {
        log.error(e.getMessage(), e);
      }
    }
    return ip;
  }
}

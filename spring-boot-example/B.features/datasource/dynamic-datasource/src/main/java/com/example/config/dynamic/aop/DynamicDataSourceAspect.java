package com.example.config.dynamic.aop;

import com.example.config.dynamic.RoutingDataSource;
import com.example.config.dynamic.annotation.DS;
import java.lang.reflect.Method;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/** 动态数据源切面 */
@Slf4j
@Aspect
@Component
public class DynamicDataSourceAspect {

  private final RoutingDataSource routingDataSource;

  @PersistenceContext private EntityManager entityManager;

  public DynamicDataSourceAspect(RoutingDataSource routingDataSource) {
    this.routingDataSource = routingDataSource;
  }

  @Pointcut("@annotation(com.example.config.dynamic.annotation.DS)")
  public void pointcut() {
    // pointcut
  }

  @Around("pointcut()")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    // 获取动态数据源注解
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method signatureMethod = signature.getMethod();
    Method realMethod =
        joinPoint
            .getTarget()
            .getClass()
            .getDeclaredMethod(signature.getName(), signatureMethod.getParameterTypes());
    DS ds = AnnotationUtils.getAnnotation(realMethod, DS.class);

    // 切换数据源
    if (Objects.nonNull(ds) && StringUtils.hasText(ds.value())) {
      routingDataSource.change(ds.value());
      log.info("----- set dynamic datasource key: {}-----", ds.value());
    }

    Object proceed = joinPoint.proceed();

    // 销毁当前数据源信息（必要）
    entityManager.clear();

    return proceed;
  }
}

package com.example.springframework.boot.data.source.aop.annotation.config.ds;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;
import java.util.Objects;

@Slf4j
public class MyDatasourceAop implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) {
        log.info("----------拦截方法，动态设置数据源----------");
        final DynamicDataSourceKey dynamicDataSourceKey = method.getAnnotation(DynamicDataSourceKey.class);
        if (Objects.isNull(dynamicDataSourceKey)) {
            //注解为空,默认主数据源
            DynamicDataSource.setDataSourceKey(DataSourceKeyConstant.MASTER);
            return;
        }

        //设置注解上的数据源key为数据源
        DynamicDataSource.setDataSourceKey(dynamicDataSourceKey.value());
    }

}
package com.example.springboot.part0402druid.common.config.datasource;

import com.example.springboot.part0402druid.common.constant.GlobalDatasourceConstant;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;
import java.util.Objects;

public class MyDatasourceAop implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("*****拦截器设置数据源*****");
        final DynamicDataSource dynamicDataSource = method.getAnnotation(DynamicDataSource.class);
        if (Objects.isNull(dynamicDataSource)) {
            //默认主数据源
            DynamicMultipleDataSource.setDataSourceKey(GlobalDatasourceConstant.PRIMARY_DATA_SOURCE_KEY);
            return;
        }
        DynamicMultipleDataSource.setDataSourceKey(dynamicDataSource.value());
    }
}

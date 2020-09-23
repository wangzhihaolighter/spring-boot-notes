package com.example.springframework.boot.data.source.aop.config.ds;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Aspect
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DataSourceAspect {

    @Pointcut("execution(public * com.example.springframework.boot.data.source.aop.service..*.*(..))")
    public void service() {
    }

    /**
     * 在进入web方法之前执行
     *
     * @param point 切面对象
     */
    @Before("service()")
    public void doBefore(JoinPoint point) {
        // 获取到当前执行的方法名
        String methodName = point.getSignature().getName();
        if (isSlave(methodName)) {
            // 标记为从库 - 读库
            DynamicDataSource.setDataSourceKey(DataSourceKeyConstant.SLAVE);
        } else {
            // 标记为主库 - 写库
            DynamicDataSource.setDataSourceKey(DataSourceKeyConstant.MASTER);
        }
    }

    /**
     * 判断是否为从库
     *
     * @param methodName 方法名
     * @return true/false
     */
    private Boolean isSlave(String methodName) {
        //方法名以query、find、get开头的方法名走从库
        return methodName.startsWith("query")
                || methodName.startsWith("find")
                || methodName.startsWith("get");
    }

}

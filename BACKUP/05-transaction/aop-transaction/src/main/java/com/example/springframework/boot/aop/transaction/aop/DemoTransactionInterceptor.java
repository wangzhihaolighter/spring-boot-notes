package com.example.springframework.boot.aop.transaction.aop;

import com.example.springframework.boot.aop.transaction.config.TransactionConfig;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.Serializable;
import java.util.Properties;

public class DemoTransactionInterceptor extends TransactionAspectSupport implements MethodInterceptor, Serializable {

    /*
    参考：org.springframework.transaction.interceptor.TransactionInterceptor实现
    由org.springframework.transaction.interceptor.TransactionAttributeEditor可知，可配置属性有：
    PROPAGATION:
    ISOLATION:
    timeout:
    readOnly:注意，当存在这个配置相当于readOnly=true，不存在相当于readOnly=false
    -:RollbackRuleAttribute,对应配置rollbackForClassName，注意配置名和值之间不需要加下划线
    +:NoRollbackRuleAttribute,对应配置noRollbackForClassName，注意配置名和值之间不需要加下划线
     */

    public DemoTransactionInterceptor() {
        //指定事务管理器
        setTransactionManagerBeanName(TransactionConfig.DEFAULT_TRANSACTION_MANAGER_NAME);

        //指定事务参数配置
        setTransactionAttributes(getAttrs());
    }

    private Properties getAttrs() {
        Properties attributes = new Properties();
        //查询
        String queryAttributes = "" +
                "readOnly" + "," +
                "PROPAGATION" + "_" + "REQUIRED" + "," +
                "ISOLATION" + "_" + "DEFAULT";
        attributes.setProperty("query*", queryAttributes);

        // 新增
        String createAttributes = "" +
                "-" + "com.example.springframework.boot.aop.transaction.exception.OtherException" + "," +
                "PROPAGATION" + "_" + "REQUIRED" + "," +
                "ISOLATION" + "_" + "DEFAULT";
        attributes.setProperty("insert*", createAttributes);

        // 修改
        String updateAttributes = "" +
                "+" + "com.example.springframework.boot.aop.transaction.exception.UpdateRuntimeException" + "," +
                "PROPAGATION" + "_" + "REQUIRED" + "," +
                "ISOLATION" + "_" + "DEFAULT";
        attributes.setProperty("update*", updateAttributes);

        // 删除
        String deleteAttributes = "" +
                "+" + "DeleteRuntimeException" + "," +
                "PROPAGATION" + "_" + "REQUIRED" + "," +
                "ISOLATION" + "_" + "DEFAULT";
        attributes.setProperty("delete*", deleteAttributes);
        return attributes;
    }

    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        // Work out the target class: may be {@code null}.
        // The TransactionAttributeSource should be passed the target class
        // as well as the method, which may be from an interface.
        Class<?> targetClass = (invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null);

        // Adapt to TransactionAspectSupport's invokeWithinTransaction...
        assert targetClass != null;
        return invokeWithinTransaction(invocation.getMethod(), targetClass, invocation::proceed);
    }
}
package com.example.transaction.interceptor.config.transaction;

import java.io.Serializable;
import java.util.Properties;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.lang.Nullable;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/** 事务拦截器 */
public class TransactionInterceptor extends TransactionAspectSupport
    implements MethodInterceptor, Serializable {

  /*
  参考：org.springframework.transaction.interceptor.TransactionInterceptor实现
  由org.springframework.transaction.interceptor.TransactionAttributeEditor可知可配置属性
  由org.springframework.transaction.TransactionDefinition可知配置属性参数值
    PROPAGATION_NAME：传播级别
      PROPAGATION_REQUIRED：支持当前事务；如果不存在，创建一个新的。通常是默认。
      PROPAGATION_SUPPORTS：支持当前事务；如果不存在事务，则以非事务方式执行。
      PROPAGATION_MANDATORY：支持当前事务；如果当前事务不存在，则引发异常。
      PROPAGATION_REQUIRES_NEW：创建一个新事务，如果存在当前事务，则暂停当前事务。
      PROPAGATION_NOT_SUPPORTED：不支持当前事务，而应始终以非事务方式执行。
      PROPAGATION_NEVER：不支持当前事务；如果当前事务存在，则引发异常。
      PROPAGATION_NESTED：如果当前事务存在，则在嵌套事务中执行。
    ISOLATION_NAME：隔离级别
      ISOLATION_DEFAULT：使用底层数据存储的默认隔离级别。所有其他级别对应于 JDBC 隔离级别。通常是默认。
      ISOLATION_READ_UNCOMMITTED：可能发生脏读、不可重复读和幻像读。这个级别允许被一个事务更改的行在该行的任何更改被提交之前被另一个事务读取(“脏读”)。如果回滚任何更改，则第二个事务将检索到无效的行。
      ISOLATION_READ_COMMITTED：防止脏读取；可能发生不可重复读取和幻象读取。此级别仅禁止事务读取包含未提交更改的行。
      ISOLATION_REPEATABLE_READ：防止脏读取和不可重复读取；可以进行幻象读取。这个级别禁止事务读取包含未提交更改的行，还禁止以下情况: 一个事务读取一行，第二个事务修改该行，第一个事务重新读取该行，第二次获取不同的值(“不可重复读取”)。
      ISOLATION_SERIALIZABLE：防止脏读取、不可重复读取和幻象读取。这个级别包括 ISOLATION_REPEATABLE_READ 中的禁止，并进一步禁止这样的情况: 一个事务读取满足 WHERE 条件的所有行，第二个事务插入满足 WHERE 条件的行，第一个事务重新读取相同条件的行，在第二个读中检索附加的“phantom”行。
    readOnly：是否作为只读事务进行优化。当存在这个配置相当于readOnly=true，不存在相当于readOnly=false。
    timeout_NNNN：
      TIMEOUT_DEFAULT：使用基础事务系统的默认超时，如果不支持超时，则不使用超时。通常是默认。
    +Exception1：
    -Exception2：
      在异常名称子字符串之前的"+"表示即使抛出了该异常，事务也应该提交；一个“-”，他们应该回滚。
      +：PREFIX_COMMIT_RULE，NoRollbackRuleAttribute，对应配置noRollbackForClassName，注意配置名和值之间不需要加下划线。
      -：PREFIX_ROLLBACK_RULE，RollbackRuleAttribute，对应配置rollbackForClassName，注意配置名和值之间不需要加下划线。
      默认捕获RuntimeException及其子类异常时，回滚
   */

  public TransactionInterceptor() {
    // 指定事务管理器
    setTransactionManagerBeanName("transactionManager");

    // 指定事务配置
    setTransactionAttributes(getAttrs());
  }

  /** 根据方法名前缀配置CRUD的事务配置 */
  private Properties getAttrs() {
    Properties attributes = new Properties();

    //默认传播级别
    String defaultPropagation = "PROPAGATION_REQUIRED";
    //默认隔离级别
    String defaultIsolation = "ISOLATION_DEFAULT";

    // 查询 - 只读
    String queryAttributes = String.join(",", "readOnly", defaultPropagation, defaultIsolation);
    attributes.setProperty("query*", queryAttributes);

    // 新增 - 指定非运行时异常回滚，其他非运行时异常不回滚
    String createAttributes =
        String.join(",", "-InsertException", defaultPropagation, defaultIsolation);
    attributes.setProperty("save*", createAttributes);

    // 修改 - 指定异常不回滚，其他异常回滚
    String updateAttributes =
        String.join(",", "+UpdateRuntimeException", defaultPropagation, defaultIsolation);
    attributes.setProperty("update*", updateAttributes);

    // 删除 - 指定异常不回滚，其他异常回滚
    String deleteAttributes =
        String.join(",", "+DeleteRuntimeException", defaultPropagation, defaultIsolation);
    attributes.setProperty("delete*", deleteAttributes);
    return attributes;
  }

  /**
   * 原事务拦截器复制而来，无变更
   *
   * @see org.springframework.transaction.interceptor.TransactionInterceptor
   * @param invocation /
   * @return /
   * @throws Throwable /
   */
  @Override
  @Nullable
  public Object invoke(MethodInvocation invocation) throws Throwable {
    // Work out the target class: may be {@code null}.
    // The TransactionAttributeSource should be passed the target class
    // as well as the method, which may be from an interface.
    Class<?> targetClass =
        (invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null);

    // Adapt to TransactionAspectSupport's invokeWithinTransaction...
    return invokeWithinTransaction(
        invocation.getMethod(),
        targetClass,
        new CoroutinesInvocationCallback() {
          @Override
          @Nullable
          public Object proceedWithInvocation() throws Throwable {
            return invocation.proceed();
          }

          @Override
          public Object getTarget() {
            return invocation.getThis();
          }

          @Override
          public Object[] getArguments() {
            return invocation.getArguments();
          }
        });
  }
}

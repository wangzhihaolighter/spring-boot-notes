package com.example.springframework.boot.interceptor.principle;

import java.lang.reflect.Proxy;

/**
 * jdk动态代理
 */
public class ProxyJdkTest {

    public static void main(String[] args) {
        //注意：jdk的proxy要求代理对象必须实现一个或多个接口

        //测试代理实现接口的类
        DemoService demoService = new DemoServiceImpl();
        DemoService demoServiceProxy = (DemoService) proxyObject(demoService);
        demoServiceProxy.doSomething();

        //测试代理未实现接口的类
        DemoServiceClass demoServiceClass = new DemoServiceClass();
        DemoServiceClass demoServiceClassProxy = (DemoServiceClass) proxyObject(demoServiceClass);
        demoServiceClassProxy.doSomething();
    }

    /**
     * jdk代理对象
     *
     * @param object 被代理类对象
     * @return 代理实例
     */
    private static Object proxyObject(Object object) {
        //模拟拦截器
        DemoInterceptor interceptor = new DemoInterceptor();
        return Proxy.newProxyInstance(
                object.getClass().getClassLoader(),
                object.getClass().getInterfaces(),
                (proxy, method, args1) -> {
                    interceptor.before();
                    Object result = method.invoke(object, args1);
                    interceptor.after();
                    return result;
                });
    }

    interface DemoService {
        void doSomething();
    }

    static class DemoServiceImpl implements DemoService {
        @Override
        public void doSomething() {
            System.out.println(this.getClass().getSimpleName() + " do something");
        }
    }

    static class DemoServiceClass {
        void doSomething() {
            System.out.println(this.getClass().getSimpleName() + " do something");
        }
    }

}

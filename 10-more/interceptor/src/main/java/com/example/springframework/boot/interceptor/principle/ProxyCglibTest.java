package com.example.springframework.boot.interceptor.principle;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

/**
 * cglib动态代理
 */
public class ProxyCglibTest {

    public static void main(String[] args) {
        //注意：cglib的proxy要求代理对象不能被final修饰，因为其原理是继承代理对象生成子类

        //测试代理未被final的类
        DemoServiceClass demoService = new DemoServiceClass();
        DemoServiceClass demoServiceProxy = (DemoServiceClass) proxyObject(demoService);
        demoServiceProxy.doSomething();

        //测试代理被final修饰的类
        FinalDemoServiceClass finalDemoService = new FinalDemoServiceClass();
        FinalDemoServiceClass finalDemoServiceProxy = (FinalDemoServiceClass) proxyObject(finalDemoService);
        finalDemoServiceProxy.doSomething();
    }

    /**
     * cglib代理对象
     *
     * @param object 被代理类对象
     * @return 代理实例
     */
    private static Object proxyObject(Object object) {
        //模拟拦截器
        DemoInterceptor interceptor = new DemoInterceptor();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(object.getClass());
        enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
            interceptor.before();
            Object result = method.invoke(object, objects);
            interceptor.after();
            return result;
        });
        return enhancer.create();
    }

    static class DemoServiceClass {
        void doSomething() {
            System.out.println(this.getClass().getSimpleName() + " do something");
        }
    }

    static final class FinalDemoServiceClass {
        void doSomething() {
            System.out.println(this.getClass().getSimpleName() + " do something");
        }
    }

}

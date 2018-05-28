package com.example.springframework.boot.interceptor.principle;

public class DemoInterceptor {

    public void before() {
        System.out.println("-----" + this.getClass().getSimpleName() + "do before" + "-----");
    }

    public void after() {
        System.out.println("-----" + this.getClass().getSimpleName() + "do after" + "-----");
    }

}

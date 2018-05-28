package com.example.springframework.boot.interceptor.service;

public interface SimpleService {
    void login(String username,String password);

    void logout();

    void doSomething();
}

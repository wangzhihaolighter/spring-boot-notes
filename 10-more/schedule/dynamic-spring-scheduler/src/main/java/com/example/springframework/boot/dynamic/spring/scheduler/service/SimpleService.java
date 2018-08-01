package com.example.springframework.boot.dynamic.spring.scheduler.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Service
public class SimpleService {

    public void test(){
        log.info("===== 测试,{} =====",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

    public void doSomething(){
        log.info("<<<<< 做些事,{} <<<<<",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

}

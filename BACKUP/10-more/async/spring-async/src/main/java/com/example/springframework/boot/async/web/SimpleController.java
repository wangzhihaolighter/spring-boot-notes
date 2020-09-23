package com.example.springframework.boot.async.web;

import com.example.springframework.boot.async.service.SimpleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    @Autowired
    private SimpleService simpleService;

    @GetMapping("/")
    public String home() {
        return "hello async";
    }

    /* 测试同/异步执行 */

    @GetMapping("/sync")
    public void sync() {
        simpleService.syncDo();
    }

    @GetMapping("/async")
    public void async() {
        simpleService.asyncDo();
    }

    @GetMapping("/annotation/async")
    public void springAsync() {
        simpleService.springAsyncDo();
    }

    /*
    测试shutdown影响异步线程方法执行情况:
    1.task不指定执行后销毁
    2.scheduler指定执行后销毁
    */

    @GetMapping("/annotation/async/test/showdown/task")
    public void testShutDownTask() {
        simpleService.testShutDownTaskDo();
    }

    @GetMapping("/annotation/async/test/showdown/scheduler")
    public void testShutDownScheduler() {
        simpleService.testShutDownSchedulerDo();
    }

    /*
    使用Future以及定义超时
     */

    @GetMapping("/async/future/result")
    public void asyncFutureResult(){
        simpleService.asyncFutureResult();
    }

    /*
    其他测试
    test:测试异步任务取消时若该任务线程休眠并捕获异常，取消不生效，任务会继续执行，不会中断
     */

    @GetMapping("/test")
    public void test(){
        simpleService.test();
    }

}

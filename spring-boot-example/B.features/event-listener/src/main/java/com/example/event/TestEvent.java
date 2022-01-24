package com.example.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author wangzhihao
 */
public class TestEvent extends ApplicationEvent {
    public TestEvent(TestDTO source) {
        super(source);
    }
}

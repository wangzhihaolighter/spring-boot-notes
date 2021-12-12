package com.example.event;

import org.springframework.context.ApplicationEvent;

/** 测试事件 */
public class TestEvent extends ApplicationEvent {
  public TestEvent(TestDTO source) {
    super(source);
  }
}

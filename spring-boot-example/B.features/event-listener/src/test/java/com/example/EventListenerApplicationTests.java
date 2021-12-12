package com.example;

import com.example.event.TestDTO;
import com.example.event.TestEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

@SpringBootTest
class EventListenerApplicationTests {
  @Autowired ApplicationEventPublisher applicationEventPublisher;

  @Test
  void testEventListener() {
    TestDTO testDTO = new TestDTO();
    testDTO.setMsg("测试事件监听");
    applicationEventPublisher.publishEvent(new TestEvent(testDTO));
  }
}

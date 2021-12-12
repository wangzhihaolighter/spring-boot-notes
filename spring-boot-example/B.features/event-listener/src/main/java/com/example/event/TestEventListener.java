package com.example.event;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestEventListener {

  @SneakyThrows
  @EventListener(TestEvent.class)
  public void test(TestEvent event) {
    TestDTO dto = (TestDTO) event.getSource();
    log.info("监听到TestEvent，事件消息：{}", dto.getMsg());
  }
}

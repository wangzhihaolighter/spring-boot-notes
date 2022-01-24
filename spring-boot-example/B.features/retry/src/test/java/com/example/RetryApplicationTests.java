package com.example;

import com.example.service.RetryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RetryApplicationTests {
  @Autowired RetryService retryService;

  @Test
  void test() throws IllegalAccessException {
    retryService.service();
  }
}

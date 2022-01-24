package com.example.sentinel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class SentinelRuleTests {

  @Autowired RestTemplate restTemplate;

  /* 测试前，sentinel dashboard 簇点链路添加规则 */

  @RepeatedTest(50) // 表示重复执行n次
  @DisplayName("hello")
  public void hello() {
    // 这里添加InterceptorConfig中设置的S-user，表示来源
    HttpHeaders headers = new HttpHeaders();
    headers.add("S-user", "127.0.0.1");
    HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(headers);

    ResponseEntity<String> responseEntity =
        restTemplate.exchange(
            "http://127.0.0.1:10000/hello", HttpMethod.GET, httpEntity, String.class);
    System.out.println(responseEntity.getBody());
  }
}

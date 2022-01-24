package com.example.spring.web.controller;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 数据格式化示例控制器
 *
 * @author wangzhihao
 */
@Controller
@RequestMapping("/format")
public class FormatExampleController {

  // Date
  // curl http://127.0.0.1:8080/format/datetime?date=2021-11-01%2000:00:00
  // curl http://127.0.0.1:8080/format/datetime/json?date=2021-11-01%2000:00:00

  @GetMapping("/datetime")
  public ResponseEntity<Date> datetimeTest(
      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date date) {
    return ResponseEntity.ok(date);
  }

  @GetMapping("/datetime/json")
  @ResponseBody
  public Map<String, Date> datetimeJsonTest(
      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date date) {
    Map<String, Date> map = new HashMap<>(1);
    map.put("datetime", date);
    return map;
  }

  // LocalDateTime
  // curl http://127.0.0.1:8080/format/localDateTime?localDateTime=2021-11-01%2000:00:00
  // curl http://127.0.0.1:8080/format/localDateTime/json?localDateTime=2021-11-01%2000:00:00

  @GetMapping("/localDateTime")
  public ResponseEntity<LocalDateTime> localDateTimeTest(LocalDateTime localDateTime) {
    return ResponseEntity.ok(localDateTime);
  }

  @GetMapping("/localDateTime/json")
  @ResponseBody
  public Map<String, LocalDateTime> localDateTimeJsonTest(LocalDateTime localDateTime) {
    Map<String, LocalDateTime> map = new HashMap<>(1);
    map.put("localDateTime", localDateTime);
    return map;
  }
}

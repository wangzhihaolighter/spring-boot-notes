package com.example.controller;

import com.example.core.logger.constant.LoggerConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/log")
public class LoggerController {

  @GetMapping("/test")
  public String test() {
    log.info("常规info日志输出");
    log.error("常规error日志输出");
    LoggerConstant.BIZ.info("指定业务日志输出");
    return "ok";
  }
}

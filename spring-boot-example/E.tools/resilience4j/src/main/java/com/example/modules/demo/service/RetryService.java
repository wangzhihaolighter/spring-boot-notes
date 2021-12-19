package com.example.modules.demo.service;

import com.example.core.exception.BusinessIgnoreException;
import com.example.core.exception.BusinessRecordException;
import com.example.core.exception.OtherRuntimeException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 重试
 *
 * @author wangzhihao
 */
@Slf4j
@Service
public class RetryService {
  private final ObjectMapper mapper;

  public RetryService(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @Retry(name = "backendA", fallbackMethod = "fallback")
  public JsonNode backendA() {
    log.error("backendA error");
    throw new BusinessRecordException("error");
  }

  @Retry(name = "backendB", fallbackMethod = "fallback")
  public JsonNode backendB() {
    log.error("backendB error");
    throw new BusinessIgnoreException("error");
  }

  @Retry(name = "backendC", fallbackMethod = "fallback")
  public JsonNode backendC() {
    log.error("backendC error");
    throw new OtherRuntimeException("error");
  }

  @Retry(name = "backendD", fallbackMethod = "fallback")
  public JsonNode backendD() {
    ObjectNode res = mapper.createObjectNode();
    res.put("msg", "success");
    log.info("backendD: {}", res);
    return res;
  }

  @Retry(name = "backendE", fallbackMethod = "fallback")
  public JsonNode backendE() {
    log.error("backendE error");
    throw new BusinessRecordException("error");
  }

  @Retry(name = "backendF", fallbackMethod = "fallback")
  public JsonNode backendF() {
    log.error("backendF error");
    throw new BusinessRecordException("error");
  }

  @Retry(name = "backendG", fallbackMethod = "fallback")
  public JsonNode backendG() {
    log.error("backendG error");
    throw new BusinessRecordException("error");
  }

  private JsonNode fallback(Throwable exception) {
    ObjectNode res = mapper.createObjectNode();
    res.put("msg", exception.getLocalizedMessage());
    log.error("fallback: {}", res);
    return res;
  }
}

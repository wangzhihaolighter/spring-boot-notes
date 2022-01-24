package com.example.config.exception.handler;

import com.example.config.exception.BusinessException;
import com.example.config.locale.LocaleMessage;
import com.example.config.response.ApiResult;
import com.example.config.response.ResultCodeEnum;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.support.RequestContextUtils;

@Slf4j
@RestControllerAdvice
class GlobalExceptionHandler {
  @Resource(name = "resultCodeLocaleMessage")
  LocaleMessage resultCodeLocaleMessage;

  @ExceptionHandler(BindException.class)
  public ApiResult<Void> handlerBindExceptionHandler(
      HttpServletRequest req, final BindException e) {
    log.error(req.getServletPath() + " Bind Exception", e);
    String message =
        e.getBindingResult().getAllErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.joining(";"));
    return ApiResult.fail(ResultCodeEnum.REQUEST_PARAM_ERROR.getCode(), message);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ApiResult<Void> handlerConstraintViolationException(
      HttpServletRequest req, final ConstraintViolationException e) {
    log.error(req.getServletPath() + " ConstraintViolation Exception", e);
    String message =
        e.getConstraintViolations().stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.joining(";"));
    return ApiResult.fail(ResultCodeEnum.REQUEST_PARAM_ERROR.getCode(), message);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ApiResult<Void> handlerMethodArgumentNotValidException(
      HttpServletRequest req, final MethodArgumentNotValidException e) {
    log.error(req.getServletPath() + " MethodArgumentNotValid Exception", e);
    String message =
        e.getBindingResult().getAllErrors().stream()
            .map(ObjectError::getDefaultMessage)
            .collect(Collectors.joining(";"));
    return ApiResult.fail(ResultCodeEnum.REQUEST_PARAM_ERROR.getCode(), message);
  }

  @ExceptionHandler(BusinessException.class)
  public ApiResult<Void> handlerBusinessException(
      HttpServletRequest req, final BusinessException e) {
    log.error(req.getServletPath() + " Business Exception", e);
    return ApiResult.fail(
        e.getResultCode(),
        resultCodeLocaleMessage.getMessage(
            e.getResultCode(), e.getArgs(), RequestContextUtils.getLocale(req)));
  }
}

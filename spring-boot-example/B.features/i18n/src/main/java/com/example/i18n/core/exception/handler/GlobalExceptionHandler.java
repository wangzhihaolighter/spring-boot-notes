package com.example.i18n.core.exception.handler;

import com.example.i18n.core.exception.BusinessException;
import com.example.i18n.core.response.R;
import com.example.i18n.core.response.ResultCodeEnum;
import com.example.i18n.core.util.IdUtils;
import java.util.stream.Collectors;
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

@Slf4j
@RestControllerAdvice
class GlobalExceptionHandler {

  @ExceptionHandler(ConstraintViolationException.class)
  public R<Object> handlerConstraintViolationException(
      HttpServletRequest req, final ConstraintViolationException e) {
    final String uuid = IdUtils.uuid();
    log.error(
        String.format(
            "[%s] Path : %s, ConstraintViolation Exception Message: %s",
            uuid, req.getServletPath(), e.getMessage()),
        e);
    String message =
        e.getConstraintViolations().stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.joining(";"));
    return R.result(ResultCodeEnum.REQUEST_PARAM_ERROR.getCode(), message, uuid);
  }

  @ExceptionHandler(BindException.class)
  public R<Object> handlerBindException(HttpServletRequest req, final BindException e) {
    final String uuid = IdUtils.uuid();
    log.error(
        String.format(
            "[%s] Path : %s, Bind Exception Message: %s",
            uuid, req.getServletPath(), e.getMessage()),
        e);
    String message =
        e.getBindingResult().getAllErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.joining(";"));
    return R.result(ResultCodeEnum.REQUEST_PARAM_ERROR.getCode(), message, uuid);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public R<Object> handlerMethodArgumentNotValidException(
      HttpServletRequest req, final MethodArgumentNotValidException e) {
    final String uuid = IdUtils.uuid();
    log.error(
        String.format(
            "[%s] Path : %s, MethodArgumentNotValid Exception Message: %s",
            uuid, req.getServletPath(), e.getMessage()),
        e);
    String message =
        e.getBindingResult().getAllErrors().stream()
            .map(ObjectError::getDefaultMessage)
            .collect(Collectors.joining(";"));
    return R.result(ResultCodeEnum.REQUEST_PARAM_ERROR.getCode(), message, uuid);
  }

  @ExceptionHandler(BusinessException.class)
  public R<Object> handlerBusinessException(HttpServletRequest req, final BusinessException e) {
    final String uuid = IdUtils.uuid();
    log.error(
        String.format(
            "[%s] Path : %s, Business Exception Message: %s",
            uuid, req.getServletPath(), e.getMessage()),
        e);
    return R.fail(ResultCodeEnum.getEnum(e.getCode()), e.getArgs(), uuid);
  }

  @ExceptionHandler(Exception.class)
  public R<Object> handlerException(HttpServletRequest req, final Exception e) {
    final String uuid = IdUtils.uuid();
    log.error(
        String.format(
            "[%s] Path : %s, Exception Message: %s", uuid, req.getServletPath(), e.getMessage()),
        e);
    return R.fail(ResultCodeEnum.FAIL, uuid);
  }
}

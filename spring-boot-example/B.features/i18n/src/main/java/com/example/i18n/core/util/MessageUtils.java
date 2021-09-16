package com.example.i18n.core.util;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

public class MessageUtils {

  private static final MessageSource MESSAGE_SOURCE =
      SpringContextUtils.getBean(MessageSource.class);

  /**
   * 根据响应码获取本地化消息
   *
   * @param code 响应码
   * @param args 消息参数
   * @return 消息
   */
  public static String getMessage(String code, Object[] args) {
    return getMessage(code, args, LocaleContextHolder.getLocale());
  }

  /**
   * 根据响应吗获取本地化消息
   *
   * @param code 响应码
   * @param args 消息参数
   * @param locale 本地化参数
   * @return 消息
   */
  public static String getMessage(String code, Object[] args, Locale locale) {
    return getMessage(code, args, null, locale);
  }

  /**
   * 获取本地化消息
   *
   * @param code 响应码
   * @param args 消息参数
   * @param defaultMessage 默认消息
   * @param locale 本地化参数
   * @return 消息
   */
  public static String getMessage(
      String code, Object[] args, String defaultMessage, Locale locale) {
    // 无本地化参数时，默认：根据应用部署的本地化获取本地化消息
    if (locale == null) {
      locale = LocaleContextHolder.getLocale();
    }
    // 无默认消息时，默认：""
    if (defaultMessage == null) {
      defaultMessage = "";
    }
    return MESSAGE_SOURCE.getMessage(
        code, args == null ? new Object[] {} : args, defaultMessage, locale);
  }
}

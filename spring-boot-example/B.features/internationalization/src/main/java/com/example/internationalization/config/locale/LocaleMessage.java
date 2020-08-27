package com.example.internationalization.config.locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * 国际化资源工具类
 */
public class LocaleMessage {

    private final MessageSource messageSource;

    public LocaleMessage(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String code) {
        return getMessage(code, new Object[]{});
    }

    public String getMessage(String code, String defaultMessage) {
        return getMessage(code, new Object[]{}, defaultMessage);
    }

    public String getMessage(String code, String defaultMessage, Locale locale) {
        return getMessage(code, new Object[]{}, defaultMessage, locale);
    }

    public String getMessage(String code, Locale locale) {
        return getMessage(code, new Object[]{}, "", locale);
    }

    public String getMessage(String code, Object[] args) {
        return getMessage(code, args, "");
    }

    public String getMessage(String code, Object[] args, Locale locale) {
        return getMessage(code, args, "", locale);
    }

    public String getMessage(String code, Object[] args, String defaultMessage) {
        //根据应用部署的服务器系统来决定国际化
        return getMessage(code, args, defaultMessage, LocaleContextHolder.getLocale());
    }

    public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        return messageSource.getMessage(code, args == null ? new Object[]{} : args, defaultMessage, locale);
    }

}

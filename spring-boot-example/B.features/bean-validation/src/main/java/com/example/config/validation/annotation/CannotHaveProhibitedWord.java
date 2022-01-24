package com.example.config.validation.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.example.config.validation.validator.CannotHaveProhibitedWordValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 自定义校验器：校验字符串是否包含违禁词
 *
 * @author wangzhihao
 */
@Documented
@Constraint(validatedBy = {CannotHaveProhibitedWordValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Repeatable(CannotHaveProhibitedWord.List.class)
public @interface CannotHaveProhibitedWord {

  // 默认错误消息
  String message() default "{system.validation.CannotHaveProhibitedWord.message}";

  // 分组
  Class<?>[] groups() default {};

  // 负载
  Class<? extends Payload>[] payload() default {};

  // 指定多个时使用
  @Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
  @Retention(RUNTIME)
  @Documented
  @interface List {
    CannotHaveProhibitedWord[] value();
  }
}

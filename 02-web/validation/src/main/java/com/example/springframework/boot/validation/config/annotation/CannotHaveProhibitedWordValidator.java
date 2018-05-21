package com.example.springframework.boot.validation.config.annotation;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 字符串中不能包含违禁词
 */
@Slf4j
public class CannotHaveProhibitedWordValidator implements ConstraintValidator<CannotHaveProhibitedWord, String> {

    @Override
    public void initialize(CannotHaveProhibitedWord constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //null时不进行校验
        if (value != null && haveProhibitedWord(value)) {
            //获取默认提示信息
            String defaultConstraintMessageTemplate = context.getDefaultConstraintMessageTemplate();
            log.info("default message :" + defaultConstraintMessageTemplate);
            //禁用默认提示信息
            context.disableDefaultConstraintViolation();
            //设置提示语
            context.buildConstraintViolationWithTemplate(defaultConstraintMessageTemplate).addConstraintViolation();
            return false;
        }
        return true;
    }

    /**
     * 是否包含违禁词
     *
     * @param value 校验字符串
     * @return 是否包含
     */
    private boolean haveProhibitedWord(String value) {
        return value.contains("违禁词")
                || value.contains("违禁词1")
                || value.contains("违禁词2")
                || value.contains("违禁词3")
                || value.contains("违禁词n")
                ;
    }
}

package com.example.annotation;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 规则：字符串中不能包含违禁词
 */
@Slf4j
public class CannotHaveProhibitedWordValidator implements ConstraintValidator<CannotHaveProhibitedWord, String> {

    @Override
    public void initialize(CannotHaveProhibitedWord constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //null时不进行校验
        if (value == null) {
            return true;
        }
        return !haveProhibitedWord(value);
    }

    /**
     * 是否包含违禁词
     *
     * @param value 校验字符串
     * @return 是否包含
     */
    private boolean haveProhibitedWord(String value) {
        String[] prohibitedWordArr = {"违禁词1", "违禁词2", "违禁词3"};
        for (String prohibitedWord : prohibitedWordArr) {
            if (value.contains(prohibitedWord)) {
                return true;
            }
        }
        return false;
    }
}

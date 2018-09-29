package com.example.springframework.boot.utils.pojo;

import com.google.common.collect.ImmutableMap;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import java.util.Set;

/**
 * JavaBean参数校验工具类
 */
public final class ValidationUtils {

    private static final javax.validation.Validator VALIDATOR;

    private ValidationUtils() {
    }

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        VALIDATOR = factory.getValidator();
    }


    /**
     * 校验bean参数，返回约束违规消息
     *
     * @param object object to validate
     * @param groups the group or list of groups targeted for validation (defaults to
     *               {@link Default})
     * @return 约束违规消息
     */
    public static <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups) {
        return VALIDATOR.validate(object, groups);
    }

    /**
     * @throws IllegalArgumentException 当校验有错误的时候抛出异常
     */
    public static <T> void throwsIfInvalid(T object, Class<?>... groups) {
        final Set<ConstraintViolation<T>> constraintViolations = validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            final ImmutableMap.Builder<String, String> errorBuilder = ImmutableMap.builder();
            for (ConstraintViolation<T> violation : constraintViolations) {
                errorBuilder.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            throw new IllegalArgumentException(errorBuilder.build().toString());
        }
    }
}

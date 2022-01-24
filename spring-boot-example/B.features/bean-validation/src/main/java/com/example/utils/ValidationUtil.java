package com.example.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;

/** JavaBean参数校验工具类 */
public final class ValidationUtil {

  /**
   * 校验bean参数，返回约束违规消息
   *
   * @param validator Validates bean instances
   * @param object object to validate
   * @param groups the group or list of groups targeted for validation (defaults to {@link Default})
   * @return 约束违规消息
   */
  public static <T> Set<ConstraintViolation<T>> validate(
      Validator validator, T object, Class<?>... groups) {
    return validator.validate(object, groups);
  }

  /** @throws IllegalArgumentException 当校验有错误的时候抛出异常 */
  public static <T> void throwsIfInvalid(Validator validator, T object, Class<?>... groups) {
    final Set<ConstraintViolation<T>> constraintViolations = validate(validator, object, groups);
    if (!constraintViolations.isEmpty()) {
      final Map<String, String> errorMap = new HashMap<>(constraintViolations.size());
      for (ConstraintViolation<T> violation : constraintViolations) {
        errorMap.put(violation.getPropertyPath().toString(), violation.getMessage());
      }
      throw new IllegalArgumentException(errorMap.toString());
    }
  }
}

package com.nervenets.general.aspect.validate;

import com.nervenets.general.aspect.validate.validator.UniqueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数组数据重复判断
 */
@Constraint(validatedBy = {UniqueValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Unique {
    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

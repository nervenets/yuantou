package com.nervenets.general.aspect.validate;

import com.nervenets.general.aspect.validate.validator.UrlValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {UrlValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Url {
    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean require() default false;
}

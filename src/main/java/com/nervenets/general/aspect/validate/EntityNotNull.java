package com.nervenets.general.aspect.validate;

import com.nervenets.general.aspect.validate.validator.EntityNotNullValidator;
import com.nervenets.general.hibernate.DomainObject;
import com.nervenets.general.service.BaseService;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {EntityNotNullValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityNotNull {
    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends BaseService<? extends DomainObject>> entityService();

    String[] excludeIds() default {"0"};
}

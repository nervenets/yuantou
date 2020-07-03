package com.nervenets.general.aspect.validate.validator;

import com.nervenets.general.aspect.validate.EntityNotNull;
import com.nervenets.general.hibernate.DomainObject;
import com.nervenets.general.service.BaseService;
import com.nervenets.general.utils.SpringContextHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class EntityNotNullValidator implements ConstraintValidator<EntityNotNull, Long> {
    private BaseService<? extends DomainObject> service;
    private List<String> excludeIds;

    @Override
    public void initialize(EntityNotNull entityNotNull) {
        this.service = SpringContextHolder.getBean(entityNotNull.entityService());
        this.excludeIds = Arrays.asList(entityNotNull.excludeIds());
    }

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext context) {
        if (excludeIds.contains(String.valueOf(id))) return true;
        if (null == service || null == id || id <= 0) return false;
        return service.findById(id).isPresent();
    }
}

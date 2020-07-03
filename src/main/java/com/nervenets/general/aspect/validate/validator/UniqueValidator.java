package com.nervenets.general.aspect.validate.validator;

import com.nervenets.general.aspect.validate.Unique;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class UniqueValidator implements ConstraintValidator<Unique, Iterable> {

    @Override
    public boolean isValid(Iterable iterable, ConstraintValidatorContext constraintValidatorContext) {
        List<Object> objects = new ArrayList<>();
        for (Object o : iterable) {
            if (objects.contains(o)) return false;
            objects.add(o);
        }
        return true;
    }
}

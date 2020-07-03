package com.nervenets.general.aspect.validate.validator;


import com.nervenets.general.aspect.validate.Url;
import com.nervenets.general.utils.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UrlValidator implements ConstraintValidator<Url, String> {
    private boolean require;

    @Override
    public void initialize(Url url) {
        this.require = url.require();
    }

    @Override
    public boolean isValid(String url, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(url) && !require) return true;
        if (url.length() > 250) return false;
        if (url.startsWith("http://")) return true;
        if (url.startsWith("https://")) return true;
        return false;
    }
}

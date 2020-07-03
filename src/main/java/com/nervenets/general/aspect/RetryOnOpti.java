package com.nervenets.general.aspect;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RetryOnOpti {
    int maxRetryTimes() default 3;
}

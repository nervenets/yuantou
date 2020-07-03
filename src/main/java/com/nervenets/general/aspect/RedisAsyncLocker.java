package com.nervenets.general.aspect;

import java.lang.annotation.*;

/**
 * Created by Joe on 2017/12/1.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RedisAsyncLocker {
    String key() default "";
}

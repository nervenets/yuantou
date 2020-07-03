package com.nervenets.general.jwt.aspect;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface JwtSecurity {
    /**
     * @return 是否验证权限
     */
    boolean required() default true;

    /*
     *  @return 权限标识别名
     */
    String permission() default "";

    /**
     * @return 权限名称
     */
    String permissionName() default "";
}

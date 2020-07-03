package com.nervenets.general.jwt.aspect;

import java.lang.annotation.*;

/**
 * 2020/6/10 14:12 created by Joe
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JwtRole {
    /**
     * @return 菜单组标识
     */
    String group();

    /**
     * @return 菜单组名称
     */
    String groupName();

    /**
     * @return 功能标识
     */
    String function();

    /**
     * @return 功能名称
     */
    String functionName();
}

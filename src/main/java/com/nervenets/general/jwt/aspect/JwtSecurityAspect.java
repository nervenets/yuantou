package com.nervenets.general.jwt.aspect;

import com.nervenets.general.exception.NerveNetsGeneralException;
import com.nervenets.general.exception.TokenIllegalException;
import com.nervenets.general.jwt.util.JwtUtils;
import com.nervenets.general.model.SecurityUser;
import com.nervenets.general.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class JwtSecurityAspect {

    /**
     * 返回通知
     */
    @Before("@annotation(com.nervenets.general.jwt.aspect.JwtSecurity) && @annotation(jwtSecurity)")
    public void doBefore(JoinPoint joinPoint, JwtSecurity jwtSecurity) {
        if (jwtSecurity.required() && JwtUtils.enforce()) {
            Class<?> cls = joinPoint.getSourceLocation().getWithinType();

            if (cls.isAnnotationPresent(JwtRole.class)) {
                JwtRole role = cls.getAnnotation(JwtRole.class);

                if (!StringUtils.isBlank(jwtSecurity.permission())) {
                    String permission = String.format("%s:%s:%s", role.group(), role.function(), jwtSecurity.permission());
                    SecurityUser securityUser = JwtUtils.getUser();
                    if (null == securityUser) throw new TokenIllegalException("您的登录状态已过期，请重新登录!");

                    if (!securityUser.getPermissions().contains(permission)) {
                        throw new NerveNetsGeneralException(403, "权限不足，请联系管理员!");
                    }
                }
            }
        }
    }
}

package com.nervenets.general.jwt;

import com.alibaba.dubbo.rpc.RpcException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.nervenets.general.Global;
import com.nervenets.general.enumeration.Platform;
import com.nervenets.general.exception.NerveNetsGeneralException;
import com.nervenets.general.exception.TokenIllegalException;
import com.nervenets.general.jwt.aspect.JwtSecurity;
import com.nervenets.general.jwt.util.JwtUtils;
import com.nervenets.general.model.SecurityUser;
import com.nervenets.general.service.GlobalSecurityService;
import com.nervenets.general.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@AllArgsConstructor
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final GlobalSecurityService globalSecurityService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse response, Object object) {
        // 如果不是映射到方法的直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();

        JwtSecurity jwtSecurity = null;
        if (method.isAnnotationPresent(JwtSecurity.class)) {
            jwtSecurity = method.getAnnotation(JwtSecurity.class);
        }

        String token = httpServletRequest.getHeader(Global.Constants.TOKEN_KEY);// 从 http 请求头中取出 token

        if (null == jwtSecurity || jwtSecurity.required() || !StringUtils.isBlank(token)) {
            if (null == token) throw new TokenIllegalException("您的登录状态无效，请登录!!");

            String sessionId = JwtUtils.getSessionId(token);

            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(sessionId)).build();
            try {
                jwtVerifier.verify(token);
            } catch (JWTVerificationException e) {
                throw new TokenIllegalException("您的登录状态已过期，请重新登录!");
            }

            try {
                SecurityUser securityUser = globalSecurityService.getUserInfo(sessionId);
                log.info("鉴权信息 , token:{} , sessionId:{}, user:{}", token, sessionId, securityUser);
                if (null == securityUser) throw new TokenIllegalException("您的登录状态已过期，请重新登录!");

                httpServletRequest.setAttribute(Global.Constants.SESSION_ID, sessionId);
                httpServletRequest.setAttribute(Global.Constants.SESSION_USER, securityUser);
            } catch (RpcException e) {
                throw new NerveNetsGeneralException("鉴权服务系统无法连接，请稍候再试!", e);
            }
        }

        String enforceKey = httpServletRequest.getHeader(Global.Constants.PLATFORM_KEY);
        if (!StringUtils.isBlank(enforceKey) && !Platform.pc.name().equalsIgnoreCase(enforceKey)) {
            httpServletRequest.setAttribute(Global.Constants.SESSION_ENFORCE, Boolean.TRUE);
        }

        return true;
    }
}

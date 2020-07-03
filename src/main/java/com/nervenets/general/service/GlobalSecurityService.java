package com.nervenets.general.service;

import com.nervenets.general.jwt.util.JwtUtils;
import com.nervenets.general.model.SecurityUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface GlobalSecurityService {
    SecurityUser getUserInfo(String sessionId);

    String getToken(SecurityUser securityUser);

    default void logout(HttpServletRequest request, HttpServletResponse response) {
        JwtUtils.logout(request, response);
    }
}

package com.nervenets.general.jwt.util;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nervenets.general.Global;
import com.nervenets.general.exception.NerveNetsGeneralException;
import com.nervenets.general.exception.TokenIllegalException;
import com.nervenets.general.jwt.aspect.JwtRole;
import com.nervenets.general.jwt.aspect.JwtSecurity;
import com.nervenets.general.model.MenuRole;
import com.nervenets.general.model.SecurityUser;
import com.nervenets.general.service.RedisService;
import com.nervenets.general.utils.SpringContextHolder;
import com.nervenets.general.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.springframework.util.DigestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

@Slf4j
public final class JwtUtils {
    public static String getToken(SecurityUser user) {
        String sessionId = UUID.randomUUID().toString();
        RedisService redisService = SpringContextHolder.getBean(RedisService.class);
        redisService.setGlobalPair(sessionId, JSON.toJSONString(user), user.getExpire());
        return getToken(sessionId);
    }

    private static String getToken(String sessionId) {
        return JWT.create().withAudience(sessionId).sign(Algorithm.HMAC256(sessionId));
    }

    public static String getSessionId(String token) {
        String sessionId;
        try {
            DecodedJWT jwt = JWT.decode(token);
            sessionId = jwt.getAudience().get(0);
        } catch (Exception e) {
            throw new TokenIllegalException("您的登录状态无效，请登录!!!");
        }

        if (null == sessionId) throw new TokenIllegalException("您的登录状态无效，请登录!!");
        return sessionId;
    }

    public static void logout(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(Global.Constants.TOKEN_KEY);// 从 http 请求头中取出 token
        String sessionId = getSessionId(token);
        RedisService redisService = SpringContextHolder.getBean(RedisService.class);
        redisService.delete(sessionId);
    }

    public static SecurityUser getUser() {
        return (SecurityUser) ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getAttribute(Global.Constants.SESSION_USER);
    }

    /**
     * 是否强制执行权限鉴权
     *
     * @return 是/否
     */
    public static boolean enforce() {
        return null == ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getAttribute(Global.Constants.SESSION_ENFORCE);
    }

    public static SecurityUser getUser(String sessionId) {
        RedisService redisService = SpringContextHolder.getBean(RedisService.class);
        if (redisService.hasKey(sessionId)) {
            String value = redisService.getGlobalPair(sessionId);
            if (null == value) return null;
            SecurityUser user = JSON.parseObject(value, SecurityUser.class);
            redisService.expire(sessionId, user.getExpire());
            return user;
        }
        return null;
    }

    public static List<MenuRole> getAllMenuRoles() {
        String packageName = "";
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{}, ClasspathHelper.staticClassLoader());

        Reflections f = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage(packageName, classLoader))
                .addClassLoader(classLoader)
                .filterInputsBy(new FilterBuilder().includePackage(packageName))
                .setScanners(new MethodAnnotationsScanner(), new TypeAnnotationsScanner(), new SubTypesScanner()));

        Set<Class<?>> classSet = f.getTypesAnnotatedWith(JwtRole.class);

        List<String> actionIds = new ArrayList<>();
        List<MenuRole> menuRoles = new ArrayList<>();

        for (Class<?> c : classSet) {
            if (c.isAnnotationPresent(JwtRole.class)) {
                JwtRole role = c.getAnnotation(JwtRole.class);

                String group = role.group();
                String groupName = role.groupName();
                String groupId = DigestUtils.md5DigestAsHex(group.getBytes());

                String function = role.function();
                String functionName = role.functionName();
                String functionId = DigestUtils.md5DigestAsHex((group + function).getBytes());

                MenuRole groupMenuRole = new MenuRole(groupId, groupName, group);
                MenuRole functionMenuRole = new MenuRole(functionId, functionName, function);

                if (!menuRoles.contains(groupMenuRole)) menuRoles.add(groupMenuRole);
                groupMenuRole = menuRoles.get(menuRoles.indexOf(groupMenuRole));

                if (!groupMenuRole.contains(functionMenuRole)) groupMenuRole.add(functionMenuRole);

                Method[] methods = c.getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(JwtSecurity.class)) {
                        JwtSecurity jwtSecurity = method.getAnnotation(JwtSecurity.class);

                        if (jwtSecurity.required() && !StringUtils.isBlank(jwtSecurity.permission())) {
                            String permission = String.format("%s:%s:%s", group, function, jwtSecurity.permission());
                            String actionId = DigestUtils.md5DigestAsHex(permission.getBytes());
                            MenuRole action = new MenuRole(actionId, jwtSecurity.permissionName(), jwtSecurity.permission());
                            if (actionIds.contains(actionId))
                                throw new NerveNetsGeneralException(String.format("存在重复的权限操作指令:[%s] %s", permission, c));
                            functionMenuRole.add(action);
                            actionIds.add(actionId);

                            log.info(" {}, permission: {}", groupName + functionName + jwtSecurity.permissionName(), permission);
                        }

                    }
                }
            }
        }
        return menuRoles;
    }
}

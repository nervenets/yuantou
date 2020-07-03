package com.nervenets.general;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.nervenets.general.jwt.aspect.JwtRole;
import com.nervenets.general.jwt.aspect.JwtSecurity;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Set;

@Slf4j
public class Test {
    @org.junit.Test
    public void test() {
        System.out.println(JWT.create().withAudience("1012").sign(Algorithm.HMAC256("123456")));
    }

    @org.junit.Test
    public void annotatedTest() throws InvocationTargetException, IllegalAccessException {
        String packageName = "com.nervenets";
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{}, ClasspathHelper.staticClassLoader());

        Reflections f = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage(packageName, classLoader))
                .addClassLoader(classLoader)
                .filterInputsBy(new FilterBuilder().includePackage(packageName))
                .setScanners(new MethodAnnotationsScanner(), new TypeAnnotationsScanner(), new SubTypesScanner()));

        Set<Class<?>> classSet = f.getTypesAnnotatedWith(JwtRole.class);
        for (Class<?> c : classSet) {
            JwtRole role = c.getAnnotation(JwtRole.class);

            Method[] declaredMethods = c.getDeclaredMethods();
            for (Method m : declaredMethods) {
                if (m.isAnnotationPresent(JwtSecurity.class)) {
                    JwtSecurity annotation = m.getAnnotation(JwtSecurity.class);

                    log.info(" {}, permission: {}", annotation, String.format("%s:%s:%s", role.group(), role.function(), annotation.permission()));

                }
            }
        }

//        Set<Method> methods = f.getMethodsAnnotatedWith(JwtSecurity.class);
//        int i = 0;
//        for (Method m : methods) {
//            JwtSecurity annotation = m.getAnnotation(JwtSecurity.class);
//
//            log.info("{}, {}, permission: {}", i++, annotation, annotation.permission());
//        }
    }
}

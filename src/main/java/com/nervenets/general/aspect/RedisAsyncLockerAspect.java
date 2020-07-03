package com.nervenets.general.aspect;

import com.nervenets.general.service.RedisService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * Created by Joe on 2017/12/1.
 */
@Aspect
@Component
public class RedisAsyncLockerAspect {
    @Resource
    private RedisService redisService;

    @Pointcut("@annotation(com.nervenets.general.aspect.RedisAsyncLocker)")
    public void asyncAspect() {
    }

    @Around("asyncAspect()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        RedisAsyncLocker locker = getLockKey(pjp);
        String lockKey = locker.key();
        while (true) {
            Boolean locked = redisService.setNx(lockKey, lockKey);
            if (locked) {
                try {
                    return pjp.proceed();
                } catch (Exception e) {
                    redisService.delete(lockKey);
                    throw e;
                } finally {
                    redisService.delete(lockKey);
                }
            }
            Thread.sleep(10);
        }
    }

    public RedisAsyncLocker getLockKey(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        RedisAsyncLocker rtnValue = null;
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    rtnValue = method.getAnnotation(RedisAsyncLocker.class);
                    break;
                }
            }
        }
        return rtnValue;
    }
}

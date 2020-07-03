package com.nervenets.general.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class RetryOnOptiAspect {

    int maxRetries;

    @Pointcut("@annotation(com.nervenets.general.aspect.RetryOnOpti)")
    public void retryOnOpti() {
    }

    @Around("retryOnOpti()")
    public Object doAround(ProceedingJoinPoint pjp)
            throws Throwable {
        int numAttempts = 0;
        do {
            numAttempts++;
            try {

                return pjp.proceed();
            } catch (Exception ex) {
                if (numAttempts > this.maxRetries) {
                    throw ex;
                } else {
                }
            }
        } while (numAttempts <= this.maxRetries);

        return null;
    }
}

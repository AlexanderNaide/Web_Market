package ru.gb.web_market.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
//@Aspect
//@Component
public class DoNotThrowBadCredentialsExceptionAspect {

    @Pointcut("@annotation(DoNotThrowBadCredentialsException)")
    public void annotatedMethodsPointcut(){
    }

    @Around("annotatedMethodsPointcut()")
    public void aroundCartServiceDellFromCart(ProceedingJoinPoint pjp) {
        try {
            pjp.proceed();
        } catch (Throwable e) {
            String className = pjp.getTarget().getClass().getName();
            String methodName = pjp.getSignature().getName();
            log.info(String.format("Во время выполнения в классе %s метода %s произошла ошибка e=%s", className, methodName, e.getMessage()));
        }
    }
}

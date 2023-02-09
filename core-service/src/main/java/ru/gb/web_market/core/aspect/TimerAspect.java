package ru.gb.web_market.core.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class TimerAspect {

    @Pointcut("@annotation(ru.gb.web_market.core.aspect.Timer)")
    public void annotatedMethodsByTimer(){
    }

    @Pointcut("@within(ru.gb.web_market.core.aspect.Timer)")
    public void annotatedClassesByTimer(){
    }

    @Around("annotatedMethodsByTimer() || annotatedClassesByTimer()")
    public Object aroundCartServiceDellFromCart(ProceedingJoinPoint pjp) {
        String className = pjp.getTarget().getClass().getName();
        String methodName = pjp.getSignature().getName();
        Object returnObj = null;
        try {
            Long started = System.currentTimeMillis();
            returnObj = pjp.proceed();
            Long finished = System.currentTimeMillis();
            log.info(String.format("Выполнение метода %s в классе %s заняло %d миллисекунд", methodName, className, finished - started));
        } catch (Throwable e) {
            log.info(String.format("Во время выполнения в классе %s метода %s произошла ошибка e=%s", className, methodName, e.getMessage()));
        }
        return returnObj;
    }
}

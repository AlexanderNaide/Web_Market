package backupSecurityFiles.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class DoNotThrowBadCredentialsExceptionAspect {

    @Pointcut("@annotation(ru.gb.web_market.order.aspect.DoNotThrowBadCredentialsException)")
    public void annotatedMethodsPointcut(){
    }

    @Around("annotatedMethodsPointcut()")
    public Object aroundCartServiceDellFromCart(ProceedingJoinPoint pjp) {
        Object returnObject = null;
        try {
            returnObject = pjp.proceed();
        } catch (Throwable e) {
            String className = pjp.getTarget().getClass().getName();
            String methodName = pjp.getSignature().getName();
            log.info(String.format("Во время выполнения в классе %s метода %s произошла ошибка e=%s", className, methodName, e.getMessage()));
        }
        return returnObject;
    }
}

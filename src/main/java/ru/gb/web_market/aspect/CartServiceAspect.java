package ru.gb.web_market.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Aspect
//@Component
@Slf4j
public class CartServiceAspect {

    // Для подключения добавляем
//        <dependency>
//            <groupId>org.springframework.boot</groupId>
//            <artifactId>spring-boot-starter-aop</artifactId>
//        </dependency>

    //AOP работает только с бинами, с обычными классами работать не будет

    // @Before - Advice(совет), указывает когда исполнить метод - до, после, вместо и др:
    // Before - перед методом,
    // AfterThrowing - выполняется после, если во время выполнения метода произошла ошибка,
    // AfterReturning - выполняется после, если метод выполнился штатно, без ошибок
    // After - выполняется после в любом случае,
    // Before -> (AfterReturning || AfterThrowing) -> After
    // Around - выполняется ВМЕСТО вызываемого метода.

    // "execution (* ru.gb.web_market.services.CartService.getCart(..))" - это Pointcut - описание того, что нужно перехватить
    // * - вместо звездочки можно указать возвращаемое значение, в данном случае "не важно"
    // JoinPoint - объект, содержащий всю информацию о вызове метода, совместим с Эдвайсами "До" или "После"
    // ProceedingJoinPoint - объект совместим с Эдвайсом Around, содержащий всю информацию о вызове метода +
    // также держит оригинальный метод, который можно вызвать .proceed() из перехваченного метода.
    // Object return = pjp.proceed(); // так возвращается то, что метод возвращает в return


    @Before("execution (* ru.gb.web_market.services.CartService.getCart(..))")
    public void beforeCartServiceGetCart(JoinPoint jp){
        Principal principal = (Principal) jp.getArgs()[0];
        log.info(String.format("Пользователь '%s' запросил корзину", principal.getName()));
    }

    @After("execution (* ru.gb.web_market.services.CartService.addToCart(..))")
    public void afterCartServiceAddToCart(JoinPoint jp) {
        Principal principal = (Principal) jp.getArgs()[0];
        Long id = (Long) jp.getArgs()[1];
        Integer count = (Integer) jp.getArgs()[2];
        log.info(String.format("Пользователь '%s' добавил в корзину товар с id = '%s' в количестве '%d'", principal.getName(), id, count));
    }

    @AfterThrowing("execution (* ru.gb.web_market.services.UserService.loadUserByUsername(..))")
    public void afterTrowingUserServiceLoadUserByUsername() {
        log.info("Попытка входа с несуществующим логином");
    }


    // Общие Pointcut можно вынести в отдельный метод:
    @Pointcut("execution (* ru.gb.web_market.services.CartService.addToCart(..))")
    public void cartServiceAfterCartServiceAddToCart(){
    }

    // Тогда в методы вместо Pointcut можно давать просто название этого метода:
    @Before("cartServiceAfterCartServiceAddToCart()")
    public void beforeCartServiceAddToCart(JoinPoint jp) {
        Principal principal = (Principal) jp.getArgs()[0];
        Long id = (Long) jp.getArgs()[1];
        Integer count = (Integer) jp.getArgs()[2];
        log.info(String.format("Пользователь '%s' собирается добавить в корзину товар с id = '%s' в количестве '%d'", principal.getName(), id, count));
    }

    @Around("execution (* ru.gb.web_market.services.CartService.dellFromCart(..))")
    public void aroundCartServiceDellFromCart(ProceedingJoinPoint pjp) {

        Principal principal = (Principal) pjp.getArgs()[0];
        Long id = (Long) pjp.getArgs()[1];
        log.info(String.format("Пользователь '%s' удаляет товар из корзины с id = '%s'", principal.getName(), id));

        try {
            pjp.proceed();
        } catch (Throwable e) {
            log.info(String.format("Во время удаления товара с Id = '%s' из корзины пользователя '%s' произошел сбой: '%s'", id, principal.getName(), e.getMessage()));
        }
    }

}

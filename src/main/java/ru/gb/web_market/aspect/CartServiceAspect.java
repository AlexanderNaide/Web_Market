package ru.gb.web_market.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Aspect
@Component
@Slf4j
public class CartServiceAspect {

    @Before("execution (* ru.gb.web_market.services.CartService.getCart(..))")
    public void beforeCartServiceGetCart(JoinPoint jp){
        Principal principal = (Principal) jp.getArgs()[0];
        log.info(String.format("Пользователь '%s' запросил корзину", principal.getName()));
    }

    @After("execution (* ru.gb.web_market.services.CartService.addToCart(..))")
    public void afterCartServiceAddToCart(JoinPoint jp){
        Principal principal = (Principal) jp.getArgs()[0];
        Long id = (Long) jp.getArgs()[1];
        Integer count = (Integer) jp.getArgs()[2];
        log.info(String.format("Пользователь '%s' добавил в корзину товар с id = '%s' в количестве '%d'", principal.getName(), id, count));
    }
}

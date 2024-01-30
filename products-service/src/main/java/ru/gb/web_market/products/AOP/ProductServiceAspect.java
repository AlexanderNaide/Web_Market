package ru.gb.web_market.products.AOP;

import jakarta.annotation.PostConstruct;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ru.gb.web_market.products.entities.Product;

import java.util.HashMap;
import java.util.Optional;

@Aspect
@Component
public class ProductServiceAspect {

    private HashMap<Long, Product> identityHashMap;


    @PostConstruct
    public void initProductServiceAspect() {
        this.identityHashMap = new HashMap<>();
    }

    @Around("execution (* ru.gb.web_market.products.services.ProductService.findById(..))")
    public Optional<Product> findById(ProceedingJoinPoint pjp) throws Throwable {
        Long id = (Long) pjp.getArgs()[0];
        if (!identityHashMap.containsKey(id)){
            System.out.println("Продукта с id = " + id + " в кэше нет, достаем из базы.");
            identityHashMap.put(id, ((Optional<Product>) pjp.proceed()).get());
        } else {
            System.out.println("Продукт с id = " + id + " в кэше есть, это - " + identityHashMap.get(id).getTitle());
        }
        return Optional.of(identityHashMap.get(id));
    }
}

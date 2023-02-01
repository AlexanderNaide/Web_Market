package ru.gb.web_market.services;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.web_market.entities.Product;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductService productService;
    private Map<Long, Integer> cart;

    @PostConstruct
    public void init(){
        this.cart = new LinkedHashMap<>();
    }

    public Map<Long, Integer> getMapCart(){
        return cart;
    }

    public void addProduct(Long id, Integer count){
//        Product product = productService.findById(id).orElse(null);
        if(cart.containsKey(id)){
            System.out.println("Корзина продукт содержит - " + id);
            cart.put(id, count + cart.get(id) > 0 ? count + cart.get(id) : 1);
        } else {
            System.out.println("Корзина продукт НЕсодержит - " + id);
            cart.put(id, count);
        }
    }

    public void dellProduct(Long id) {
//        cart.remove(cart.keySet().stream().filter(p -> Objects.equals(p.getId(), id)).findFirst().get());
        cart.remove(id);
    }

    public int getCount() {
        return cart.values().stream().reduce(0, Integer::sum);
    }
}

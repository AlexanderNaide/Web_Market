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
    private Map<Product, Integer> cart;

    @PostConstruct
    public void init(){
        this.cart = new LinkedHashMap<>();
    }

    public Map<Product, Integer> getMapCart(){
        return cart;
    }

    public void addProduct(Long id, Integer count){
        Product product = productService.findById(id).get();
        if(cart.containsKey(product)){
            cart.put(product, count + cart.get(product) > 0 ? count + cart.get(product) : 1);
        } else {
            cart.put(product, count);
        }
    }

    public void dellProduct(Long id) {
        cart.remove(cart.keySet().stream().filter(p -> Objects.equals(p.getId(), id)).findFirst().get());
    }
}

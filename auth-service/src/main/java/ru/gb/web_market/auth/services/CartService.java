package ru.gb.web_market.auth.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.web_market.auth.entities.User;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CartService {
    private final UserService userService;

    public void addToCart(String username, Long id, Integer count){

        User user = Objects.requireNonNull(userService.findByUsername(username).orElse(null));
        if(user.getCart().containsKey(id)){
            user.getCart().put(id, count + user.getCart().get(id) > 0 ? count + user.getCart().get(id) : 1);
        } else {
            user.getCart().put(id, count);
        }
        userService.save(user);

    }

    public void dellFromCart(String username, Long id) {
        User user = Objects.requireNonNull(userService.findByUsername(username).orElse(null));
        user.getCart().remove(id);
        userService.save(user);
    }

    public int getCount(String username) {
//        return cart.values().stream().reduce(0, Integer::sum);
        return Objects.requireNonNull(userService.findByUsername(username).orElse(null)).getCart().values().stream().reduce(0, Integer::sum);
    }

    public void clearCart(String username){
        Objects.requireNonNull(userService.findByUsername(username).orElse(null)).getCart().clear();
    }
}

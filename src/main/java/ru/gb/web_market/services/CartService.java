package ru.gb.web_market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.web_market.entities.User;

import java.security.Principal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CartService {
    private final UserService userService;

    public Map<Long, Integer> getCart(Principal principal){
        User user = userService.findByUsername(principal.getName()).orElse(null);
        assert user != null;
        return user.getCart();
    }

    public void addToCart(Principal principal, Long id, Integer count){

        User user = Objects.requireNonNull(userService.findByUsername(principal.getName()).orElse(null));
        if(user.getCart().containsKey(id)){
            user.getCart().put(id, count + user.getCart().get(id) > 0 ? count + user.getCart().get(id) : 1);
        } else {
            user.getCart().put(id, count);
        }
        userService.save(user);

    }

    public void dellFromCart(Principal principal, Long id) {
        User user = Objects.requireNonNull(userService.findByUsername(principal.getName()).orElse(null));
        user.getCart().remove(id);
        userService.save(user);
    }

    public int getCount(Principal principal) {
//        return cart.values().stream().reduce(0, Integer::sum);
        return Objects.requireNonNull(userService.findByUsername(principal.getName()).orElse(null)).getCart().values().stream().reduce(0, Integer::sum);
    }
}

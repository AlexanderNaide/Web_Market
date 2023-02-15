package ru.gb.web_market.auth.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import ru.gb.web_market.api.dto.CartDto;
import ru.gb.web_market.api.dto.ProductToCartDto;
import ru.gb.web_market.auth.entities.User;
import ru.gb.web_market.auth.integrations.AuthServiceIntegration;
import ru.gb.web_market.auth.services.CartService;
import ru.gb.web_market.auth.services.UserService;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/cart")
public class CartController {
    private final CartService cartService;

    private final AuthServiceIntegration authServiceIntegration;

    private final UserService userService;


    @GetMapping("/add_to_cart")
    public void addToCart(@RequestParam Long id, @RequestParam(required = false, defaultValue = "1") Integer count, @RequestHeader String username){
        cartService.addToCart(username, id, count);
    }

    @GetMapping("/dell_from_cart")
    public void dellFromCart(@RequestHeader String username, @RequestParam Long id){
        cartService.dellFromCart(username, id);
    }

    @GetMapping
    public CartDto getCart(@RequestHeader String username){
        User user = userService.findByUsername(username).orElseThrow(() -> new BadCredentialsException(String.format("Пользователь '%s' отсутствует в базе данных", username)));
        CartDto cartDto = new CartDto();
        cartDto.setCart(user.getCart().entrySet().stream().map(e -> {
            ProductToCartDto dto = new ProductToCartDto();
            dto.setId(e.getKey());
            dto.setCount(e.getValue());
            return dto;
        }).collect(Collectors.toList()));
        return authServiceIntegration.updateCart(cartDto);
    }

    @GetMapping("/count")
    public int cartCount(@RequestHeader String username){
        return cartService.getCount(username);
    }

    @GetMapping("/clear")
    public void clearCart(@RequestHeader String username){
        cartService.clearCart(username);
    }
}

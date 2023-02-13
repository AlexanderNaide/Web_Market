package ru.gb.web_market.core.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import ru.gb.web_market.api.dto.CartDto;
import ru.gb.web_market.api.dto.ProductToCartDto;
import ru.gb.web_market.core.entities.User;
import ru.gb.web_market.core.integrations.ProductServiceIntegration;
import ru.gb.web_market.core.services.CartService;
import ru.gb.web_market.core.services.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/cart")
@CrossOrigin("*")
public class CartController {
    private final CartService cartService;

    private final ProductServiceIntegration productIntegration;

    private final UserService userService;


    @GetMapping("/add_to_cart")
    public void addToCart(@RequestParam Long id, @RequestParam(required = false, defaultValue = "1") Integer count, Principal principal){
        cartService.addToCart(principal, id, count);
    }

    @GetMapping("/dell_from_cart")
    public void dellFromCart(Principal principal, @RequestParam Long id){
        cartService.dellFromCart(principal, id);
    }

    @GetMapping
    public List<ProductToCartDto> getCart(Principal principal){
        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new BadCredentialsException(String.format("Пользователь '%s' отсутствует в базе данных", principal.getName())));
        CartDto cartDto = new CartDto();
        cartDto.setCart(user.getCart().entrySet().stream().map(e -> {
            ProductToCartDto dto = new ProductToCartDto();
            dto.setId(e.getKey());
            dto.setCount(e.getValue());
            return dto;
        }).collect(Collectors.toList()));
//        user.getCart().forEach((k, v) -> {
//            ProductToCartDto itemDto = new ProductToCartDto();
//            itemDto.setId(k);
//            itemDto.setCount(v);
//            cartDto.getCart().add(itemDto);
//        });
        return productIntegration.updateCart(cartDto).getCart();
    }

    @GetMapping("/count")
    public int cartCount(Principal principal){
        return cartService.getCount(principal);
    }
}

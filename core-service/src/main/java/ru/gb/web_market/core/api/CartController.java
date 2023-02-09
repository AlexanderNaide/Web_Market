package ru.gb.web_market.core.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.web_market.core.converters.ProductConverter;
import ru.gb.web_market.core.dto.ProductToCartDto;
import ru.gb.web_market.core.services.CartService;
import ru.gb.web_market.core.services.ProductService;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor // Ломбоковская аннотация, которая инициализирует final поля вместо конструктора с @Autowired
@RequestMapping("api/v1/cart")
public class CartController {
    private final ProductConverter productConverter;
    private final CartService cartService;
    private final ProductService productService;


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
        return cartService.getCart(principal).entrySet().stream()
                .map(entry -> productConverter.entityToCardDto(Objects.requireNonNull(productService.findById(entry.getKey()).orElse(null)), entry.getValue()))
                .collect(Collectors.toList());
    }

    @GetMapping("/count")
    public int cartCount(Principal principal){
        return cartService.getCount(principal);
    }
}

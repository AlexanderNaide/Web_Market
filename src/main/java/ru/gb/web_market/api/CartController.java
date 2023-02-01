package ru.gb.web_market.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.web_market.converters.ProductConverter;
import ru.gb.web_market.dto.ProductToCartDto;
import ru.gb.web_market.services.CartService;
import ru.gb.web_market.services.ProductService;

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
    public void addToCart(@RequestParam Long id, @RequestParam(required = false, defaultValue = "1") Integer count){
        cartService.addProduct(id, count);
    }

    @GetMapping("/dell_from_cart")
    public void dellFromCart(@RequestParam Long id){
        cartService.dellProduct(id);
    }

    @GetMapping
    public List<ProductToCartDto> getMapCart(){
//        return cartService.getMapCart().entrySet().stream().collect(Collectors.toList((entry) -> productConverter.entityToDto(entry.getKey()), Map.Entry::getValue));
        return cartService.getMapCart().entrySet().stream()
//                .map(entry -> productConverter.entityToCardDto(entry.getKey(), entry.getValue()))
                .map(entry -> productConverter.entityToCardDto(Objects.requireNonNull(productService.findById(entry.getKey()).orElse(null)), entry.getValue()))
                .collect(Collectors.toList());
    }

    @GetMapping("/count")
    public int cartCount(){
        return cartService.getCount();
    }
}

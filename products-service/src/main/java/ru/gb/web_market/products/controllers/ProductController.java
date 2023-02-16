package ru.gb.web_market.products.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.gb.web_market.api.dto.CartDto;
import ru.gb.web_market.api.dto.OrderFullDto;
import ru.gb.web_market.api.dto.ProductDto;
import ru.gb.web_market.api.dto.ProductFullDto;
import ru.gb.web_market.api.exception.ResourceNotFoundException;
import ru.gb.web_market.products.converters.ProductConverter;
import ru.gb.web_market.products.services.ProductService;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductConverter productConverter;

    @GetMapping("/{id}")
    public ProductFullDto getProductById(@PathVariable Long id){
        return productService.findById(id).map(productConverter::entityToFullDto).orElseThrow(() -> new ResourceNotFoundException("Продукт не найден в базе данных товаров, id:" + id));
    }

    @PostMapping
    public Page<ProductDto> upAll(@RequestParam(required = false, defaultValue = "1") Integer page,
                                  @RequestParam(required = false) String val,
                                  @RequestParam(required = false) Double min,
                                  @RequestParam(required = false) Double max,
                                  @RequestParam(required = false) Long cat,
                                  @RequestParam(required = false) Long sub_cat,
                                  @RequestParam(required = false) String man
                                  ){
        if(page < 1){
            page = 1;
        }
        return productService.findCom(min, max, val, cat, sub_cat, man, page).map(productConverter::entityToDto);
    }

    @GetMapping
    public Page<ProductDto> findAll(){
        return productService.findCom(null, null, null, null, null, null,1).map(productConverter::entityToDto);
    }

    @PostMapping("/update_cart")
    public CartDto updateProductFromCart(@RequestBody CartDto cartDto){
        System.out.println("Попали в updateProductFromCart");
        return productService.updateProductFromCart(cartDto);
    }

    @PostMapping("/update_order_list")
    public OrderFullDto updateOrderListFromOrder(@RequestBody OrderFullDto orderFullDto){
        return productService.updateOrderListFromOrder(orderFullDto);
    }
}
package ru.gb.web_market.core.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.gb.web_market.api.dto.*;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductServiceIntegration {

    private final RestTemplate restTemplate;

    public Optional<ProductFullDto> getProductById(Long id){
        return Optional.ofNullable(restTemplate.getForObject("http://localhost:8084/market-products/api/v1/products/" + id, ProductFullDto.class));
    }

    public CartDto updateCart(CartDto cart){
        return restTemplate.postForEntity("http://localhost:8084/market-products/api/v1/products/update_cart", cart, CartDto.class).getBody();
    }

    public OrderFullDto updateOrderList(OrderFullDto orderFullDto){
        return restTemplate.postForEntity("http://localhost:8084/market-products/api/v1/products/update_order_list", orderFullDto, OrderFullDto.class).getBody();
    }






}

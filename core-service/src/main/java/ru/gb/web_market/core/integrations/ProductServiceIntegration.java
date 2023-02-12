package ru.gb.web_market.core.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
        return restTemplate.getForObject("http://localhost:8084/market-products/api/v1/products/update_cart", CartDto.class, cart);
    }

    public ListDto findCategory(Long id) {
        return restTemplate.getForObject("http://localhost:8084/market-products/api/v1/categories", ListDto.class, id);
    }

    public ListDto findManufacturer(CategoryDto cat, CategoryDto subCat) {
        return restTemplate.getForObject("http://localhost:8084/market-products/api/v1/manufacturers", ListDto.class, cat, subCat);
    }

    public Page<ProductDto> findCom(Double min, Double max, String val, String cat, String sub_cat, String man, Integer page) {
        return restTemplate.getForObject("http://localhost:8084/market-products/api/v1/products/", Page.class, min, max, val, cat, sub_cat, man, page);
    }
}

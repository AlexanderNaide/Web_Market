package ru.gb.web_market.api.dto;

import java.util.List;

public class CartDto {
    private List<ProductToCartDto> cart;

    public List<ProductToCartDto> getCart() {
        return cart;
    }
}

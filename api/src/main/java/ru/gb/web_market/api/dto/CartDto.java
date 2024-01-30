package ru.gb.web_market.api.dto;

import java.util.List;


public class CartDto {
    private List<ProductToCartDto> cart;

    public void setCart(List<ProductToCartDto> cart) {
        this.cart = cart;
    }

    public List<ProductToCartDto> getCart() {
        return cart;
    }

    public CartDto() {
    }

    public CartDto(List<ProductToCartDto> cart) {
        this.cart = cart;
    }
}

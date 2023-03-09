package ru.gb.web_market.api.dto;


import java.math.BigDecimal;

public class OrderItemDto {
    private Long productId;
    private String product;
    private int count;
    private BigDecimal price;
    private BigDecimal totalPrice;


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderItemDto() {
    }

    public OrderItemDto(Long productId, String product, int count, BigDecimal price, BigDecimal totalPrice) {
        this.productId = productId;
        this.product = product;
        this.count = count;
        this.price = price;
        this.totalPrice = totalPrice;
    }
}

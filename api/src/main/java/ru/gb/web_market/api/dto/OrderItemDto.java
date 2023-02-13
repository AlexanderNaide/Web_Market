package ru.gb.web_market.api.dto;


public class OrderItemDto {
    private Long productId;
    private String product;
    private int count;
    private Double price;
    private Double totalPrice;


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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderItemDto() {
    }

    public OrderItemDto(Long productId, String product, int count, Double price, Double totalPrice) {
        this.productId = productId;
        this.product = product;
        this.count = count;
        this.price = price;
        this.totalPrice = totalPrice;
    }
}

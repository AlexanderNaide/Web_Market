package ru.gb.web_market.api.dto;


import java.math.BigDecimal;

public class OrderItemDto {
    private Long productId;
    private String product;
    private int count;
    private BigDecimal price;
    private BigDecimal totalPrice;

    private OrderItemDto(Builder builder) {
        setProductId(builder.productId);
        setProduct(builder.product);
        setCount(builder.count);
        setPrice(builder.price);
        setTotalPrice(builder.totalPrice);
    }


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


    public static final class Builder {
        private Long productId;
        private String product;
        private int count;
        private BigDecimal price;
        private BigDecimal totalPrice;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder setProductId(Long productId) {
            this.productId = productId;
            return this;
        }

        public Builder setProduct(String product) {
            this.product = product;
            return this;
        }

        public Builder setCount(int count) {
            this.count = count;
            return this;
        }

        public Builder setPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder setTotalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public OrderItemDto build() {
            return new OrderItemDto(this);
        }
    }
}

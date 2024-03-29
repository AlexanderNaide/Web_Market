package ru.gb.web_market.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

import static java.time.format.DateTimeFormatter.ofLocalizedDateTime;


public class OrderFullDto {

    private String createdAt;
    private String status;
    private String address;
    private String info;
    private BigDecimal fullPrice;
    private List<OrderItemDto> productList;

    private OrderFullDto(Builder builder) {
        setCreatedAt(builder.createdAt);
        setStatus(builder.status);
        setAddress(builder.address);
        setInfo(builder.info);
        setFullPrice(builder.fullPrice);
        setProductList(builder.productList);
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public BigDecimal getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(BigDecimal fullPrice) {
        this.fullPrice = fullPrice;
    }

    public List<OrderItemDto> getProductList() {
        return productList;
    }

    public void setProductList(List<OrderItemDto> productList) {
        this.productList = productList;
    }

    public OrderFullDto() {
    }

    public OrderFullDto(String createdAt, String status, String address, String info, BigDecimal fullPrice, List<OrderItemDto> productList) {
        this.createdAt = createdAt;
        this.status = status;
        this.address = address;
        this.info = info;
        this.fullPrice = fullPrice;
        this.productList = productList;
    }


    public static final class Builder {
        private String createdAt;
        private String status;
        private String address;
        private String info;
        private BigDecimal fullPrice;
        private List<OrderItemDto> productList;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setInfo(String info) {
            this.info = info;
            return this;
        }

        public Builder setFullPrice(BigDecimal fullPrice) {
            this.fullPrice = fullPrice;
            return this;
        }

        public Builder setProductList(List<OrderItemDto> productList) {
            this.productList = productList;
            return this;
        }

        public OrderFullDto build() {
            return new OrderFullDto(this);
        }
    }
}

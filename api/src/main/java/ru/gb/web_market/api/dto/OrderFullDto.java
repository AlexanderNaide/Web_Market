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


}

package ru.gb.web_market.api.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import static java.time.format.DateTimeFormatter.ofLocalizedDateTime;

public class OrderListDto {

    private Long id;

    private String createdAt;

    private String status;

    private Double totalPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderListDto() {
    }

    public OrderListDto(Long id, String createdAt, String status, Double totalPrice) {
        this.id = id;
        this.createdAt = createdAt;
        this.status = status;
        this.totalPrice = totalPrice;
    }

    public OrderListDto(Long id, LocalDateTime createdAt, String status, Double totalPrice) {
        this.id = id;
        this.createdAt = convertDate(createdAt);
        this.status = status;
        this.totalPrice = totalPrice;
    }

    public String convertDate(LocalDateTime dateTime) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm:ss");
        DateTimeFormatter formatter = ofLocalizedDateTime(FormatStyle.SHORT);
        return formatter.format(dateTime);
    }
}

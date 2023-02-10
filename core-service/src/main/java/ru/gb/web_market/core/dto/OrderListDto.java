package ru.gb.web_market.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import static java.time.format.DateTimeFormatter.ofLocalizedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderListDto {

    private Long id;

    private String createdAt;

    private String status;

    private Double totalPrice;

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

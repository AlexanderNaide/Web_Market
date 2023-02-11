package ru.gb.web_market.core.dto;

import jakarta.persistence.*;
import jdk.jfr.Unsigned;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import ru.gb.web_market.core.entities.Order;
import ru.gb.web_market.core.entities.OrderItem;
import ru.gb.web_market.core.entities.OrderStatusEnums;
import ru.gb.web_market.core.entities.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

import static java.time.format.DateTimeFormatter.ofLocalizedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderFullDto {

    private String createdAt;
    private String status;

    private String address;

    private String info;

    private Double fullPrice;

    private List<OrderItemDto> productList;


    public OrderFullDto(Order order) {
        this.createdAt = convertDate(order.getCreatedAt());
        this.status = order.getStatus().getStatus();
        this.address = order.getAddress();
        this.info = order.getInfo();
        this.productList = order.getProductList().stream().map(OrderItemDto::new).toList();
        this.fullPrice = productList.stream().mapToDouble(OrderItemDto::getTotalPrice).sum();
    }

    public String convertDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = ofLocalizedDateTime(FormatStyle.SHORT);
        return formatter.format(dateTime);
    }
}

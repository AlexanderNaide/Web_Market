package ru.gb.web_market.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gb.web_market.api.dto.ProductDto;
import ru.gb.web_market.core.entities.OrderItem;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    private Long productId;
    private int count;
    private Double price;
    private Double totalPrice;

    public OrderItemDto(OrderItem orderItem) {
        this.productId = orderItem.getProductId();
        this.count = orderItem.getCount();
        this.price = orderItem.getPrice();
        this.totalPrice = orderItem.getTotalPrice();
    }
}

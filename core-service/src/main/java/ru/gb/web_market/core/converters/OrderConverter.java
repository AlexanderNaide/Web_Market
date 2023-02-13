package ru.gb.web_market.core.converters;

import org.springframework.stereotype.Component;
import ru.gb.web_market.api.dto.OrderFullDto;
import ru.gb.web_market.api.dto.OrderItemDto;
import ru.gb.web_market.core.entities.Order;
import ru.gb.web_market.core.entities.OrderItem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import static java.time.format.DateTimeFormatter.ofLocalizedDateTime;

@Component
public class OrderConverter {

    public OrderFullDto orderToOrderFullDto(Order order) {
        OrderFullDto orderFullDto = new OrderFullDto();
        orderFullDto.setCreatedAt(convertDate(order.getCreatedAt()));
        orderFullDto.setStatus(order.getStatus().getStatus());
        orderFullDto.setAddress(order.getAddress());
        orderFullDto.setInfo(order.getInfo());
        orderFullDto.setProductList(order.getProductList().stream().map(this::orderItemToOrderItemDto).toList());
        orderFullDto.setFullPrice(orderFullDto.getProductList().stream().mapToDouble(OrderItemDto::getTotalPrice).sum());
        return orderFullDto;
    }

    public OrderItemDto orderItemToOrderItemDto(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setProductId(orderItem.getProductId());
        orderItemDto.setCount(orderItem.getCount());
        orderItemDto.setPrice(orderItem.getPrice());
        orderItemDto.setTotalPrice(orderItem.getTotalPrice());
        return orderItemDto;
    }

        public String convertDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = ofLocalizedDateTime(FormatStyle.SHORT);
        return formatter.format(dateTime);
    }
}

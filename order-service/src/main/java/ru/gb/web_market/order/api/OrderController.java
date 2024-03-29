package ru.gb.web_market.order.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.gb.web_market.api.dto.OrderFullDto;
import ru.gb.web_market.api.dto.OrderListDto;
import ru.gb.web_market.api.exception.ResourceNotFoundException;
import ru.gb.web_market.order.converters.OrderConverter;
import ru.gb.web_market.order.entities.OrderItem;
import ru.gb.web_market.order.integrations.ProductServiceIntegration;
import ru.gb.web_market.order.services.OrderService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
//@CrossOrigin("*")
public class OrderController {

    private final OrderService orderService;
    private final ProductServiceIntegration productIntegration;
    private final OrderConverter orderConverter;

    @GetMapping
    public List<OrderListDto> findOrderList(@RequestHeader String username){
        return orderService.findOrderList(username).stream()
                .map(e -> new OrderListDto(
                        e.getId(),
                        e.getCreatedAt(),
                        e.getStatus().getStatus(),
//                        e.getProductList().stream().mapToDouble(OrderItem::getPrice).sum()))
                        e.getProductList().stream().map(OrderItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add)))
                .toList();
    }


    // https://stackoverflow.com/questions/19468209/spring-security-configuration-http-403-error

    @GetMapping ("/create")
    public void createdOrder(@RequestHeader String username){
        orderService.createdOrder(username);
    }

    @GetMapping("/{id}")
    public OrderFullDto getOrderById(@PathVariable Long id){
        OrderFullDto orderFullDto = orderConverter.orderToOrderFullDto(orderService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Заказ не найден в базе данных товаров, id:" + id)));
        return productIntegration.updateOrderList(orderFullDto);
    }


}
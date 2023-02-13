package ru.gb.web_market.core.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.gb.web_market.api.dto.OrderFullDto;
import ru.gb.web_market.api.dto.OrderListDto;
import ru.gb.web_market.api.exception.ResourceNotFoundException;
import ru.gb.web_market.core.converters.OrderConverter;
import ru.gb.web_market.core.entities.OrderItem;
import ru.gb.web_market.core.integrations.ProductServiceIntegration;
import ru.gb.web_market.core.services.OrderService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@CrossOrigin("*")
public class OrderController {

    private final OrderService orderService;
    private final ProductServiceIntegration productIntegration;

    private final OrderConverter orderConverter;

    @GetMapping
    public List<OrderListDto> findOrderList(Principal principal){
        return orderService.findOrderList(principal).stream()
                .map(e -> new OrderListDto(
                        e.getId(),
                        e.getCreatedAt(),
                        e.getStatus().getStatus(),
                        e.getProductList().stream().mapToDouble(OrderItem::getPrice).sum()))
                .toList();
    }


    // https://stackoverflow.com/questions/19468209/spring-security-configuration-http-403-error

    @PutMapping ("/create")
    public void createdOrder(Principal principal){
        orderService.createdOrder(principal);
    }

    @GetMapping("/{id}")
    public OrderFullDto getOrderById(@PathVariable Long id){
        OrderFullDto orderFullDto = orderConverter.orderToOrderFullDto(orderService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Заказ не найден в базе данных товаров, id:" + id)));
        return productIntegration.updateOrderList(orderFullDto);
    }


}
package ru.gb.web_market.core.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.gb.web_market.core.converters.ProductConverter;
import ru.gb.web_market.core.dto.OrderListDto;
import ru.gb.web_market.core.dto.ProductDto;
import ru.gb.web_market.core.dto.ProductFullDto;
import ru.gb.web_market.core.entities.OrderItem;
import ru.gb.web_market.core.exceptions.ResourceNotFoundException;
import ru.gb.web_market.core.services.OrderService;
import ru.gb.web_market.core.services.ProductService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<OrderListDto> findOrderList(Principal principal){
        System.out.println("Запрос пришел");
        List<OrderListDto> list = orderService.findOrderList(principal).stream()
                .map(e -> new OrderListDto(e.getId(), e.getCreatedAt(), e.getStatus().getStatus(), e.getProductList().stream().mapToDouble(OrderItem::getPrice).sum())).toList();
        list.forEach(e -> System.out.println(e.getId() + " " + e.getCreatedAt()));
        return list;
    }


    // https://stackoverflow.com/questions/19468209/spring-security-configuration-http-403-error

    @PostMapping ("/create")
    public void createdOrder(Principal principal){
        System.err.println("Попали в createdOrder");
        orderService.createdOrder(principal);
    }


}
package ru.gb.web_market.core.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.web_market.api.dto.CartDto;
import ru.gb.web_market.api.dto.OrderListDto;
import ru.gb.web_market.api.dto.ProductToCartDto;
import ru.gb.web_market.core.entities.*;
import ru.gb.web_market.core.integrations.AuthServiceIntegration;
import ru.gb.web_market.core.integrations.ProductServiceIntegration;
import ru.gb.web_market.core.repositories.OrderRepository;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

import static java.time.format.DateTimeFormatter.ofLocalizedDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final ProductServiceIntegration productServiceIntegration;

    private final AuthServiceIntegration authServiceIntegration;

    public List<Order> findOrderList(String username) {
        return orderRepository.findAllByUsername(username);
    }

    @Transactional
    public void createdOrder(String username) {
        if(username != null){
            Order order = new Order();
            order.setUsername(username);
            order.setStatus(OrderStatusEnums.CREATED);
            orderRepository.save(order);
            CartDto cartDto = authServiceIntegration.getCartDto(username);
            List<ProductToCartDto> list = cartDto.getCart();
            list.forEach(e -> {
                OrderItem item = new OrderItem(order, e);
                orderItemService.Save(item);
            });
            authServiceIntegration.clearCart(username);
            orderRepository.save(order);
//            return order;
        }
//        else{
//            return null;
//        }

    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }
}

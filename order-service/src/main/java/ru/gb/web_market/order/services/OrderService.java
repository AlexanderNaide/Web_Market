package ru.gb.web_market.order.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.web_market.api.dto.CartDto;
import ru.gb.web_market.api.dto.ProductToCartDto;
import ru.gb.web_market.order.entities.*;
import ru.gb.web_market.order.integrations.AuthServiceIntegration;
import ru.gb.web_market.order.integrations.ProductServiceIntegration;
import ru.gb.web_market.order.repositories.OrderRepository;

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

package ru.gb.web_market.core.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.web_market.api.exception.ResourceNotFoundException;
import ru.gb.web_market.core.entities.*;
import ru.gb.web_market.core.integrations.ProductServiceIntegration;
import ru.gb.web_market.core.repositories.OrderRepository;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

import static java.time.format.DateTimeFormatter.ofLocalizedDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final UserService userService;

    private final ProductServiceIntegration serviceIntegration;
//    private final ProductService productService;

//    public List<String> findAllCategories() {
//        return categoriesRepository.findAllCategories();
//    }
//
//    public List<String> findAllSubCategories(String cat) {
//        long catId = Long.parseLong(Objects.requireNonNull(Arrays.stream(cat.split(",")).findFirst().orElse(null)));
//        return categoriesRepository.findAllSubCategories(catId);
//    }
//
//    public Optional<Category> findById(long id){
//        return categoriesRepository.findById(id);
//    }

    public List<Order> findOrderList(Principal principal) {
        return orderRepository.findAllByUser(userService.findByUsername(principal.getName()).orElse(null));
    }

    @Transactional
    public void createdOrder(Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElse(null);
        if(user != null){
            Order order = new Order();
            order.setUser(user);
            order.setStatus(OrderStatusEnums.CREATED);
            DateTimeFormatter formatter = ofLocalizedDateTime(FormatStyle.SHORT);
            orderRepository.save(order);
            order.setHistory(order.getStatus().getStatus() + " - " + formatter.format(order.getCreatedAt()));
            Map<Long, Integer> cart = user.getCart();
            cart.forEach((e, c) -> {
//                OrderItem item = new OrderItem(order, productService.findById(e).orElseThrow(() -> new ResourceNotFoundException("Продукт не найден в базе данных товаров, id:" + e)), c);
                OrderItem item = new OrderItem(order, serviceIntegration.getProductById(e).orElseThrow(() -> new ResourceNotFoundException("Продукт не найден в базе данных товаров, id:" + e)), c);
                orderItemService.Save(item);
            });
            user.getCart().clear();
            orderRepository.save(order);
        }
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }
}

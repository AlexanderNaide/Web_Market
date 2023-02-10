package ru.gb.web_market.core.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.gb.web_market.core.dto.OrderListDto;
import ru.gb.web_market.core.entities.*;
import ru.gb.web_market.core.repositories.CategoriesRepository;
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
    private final ProductService productService;

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
        System.out.println("Попали в createdOrder");
        User user = userService.findByUsername(principal.getName()).orElse(null);
        System.out.println("Получили юзера: " + user.getUsername());
        if(user != null){
            Order order = new Order();
            System.out.println("1");
            order.setUser(user);
            System.out.println("2");
            order.setStatus(OrderStatusEnums.CREATED);
            System.out.println("3");
//            Order finalOrder = orderRepository.saveAndFlush(order);
            DateTimeFormatter formatter = ofLocalizedDateTime(FormatStyle.SHORT);
            System.out.println("4a");
            System.out.println(order.getStatus());
            System.out.println("4b");
            System.out.println(order.getStatus().getStatus());
            System.out.println("4c");
            orderRepository.save(order);
            System.out.println(order.getCreatedAt());
            System.out.println("4d");

            order.setHistory(order.getStatus().getStatus() + " - " + formatter.format(order.getCreatedAt()));
            System.out.println("4");
//            order.setProductList(new ArrayList<>());
            Map<Long, Integer> cart = user.getCart();
            System.out.println("5");
            System.out.println(order.getCreatedAt());
            System.out.println(order.getStatus().getStatus());
            System.out.println("Товары:");
            cart.forEach((e, c) -> System.out.println(e + " " + c));
            cart.forEach((e, c) -> {
                OrderItem item = new OrderItem(order, productService.findById(e).get(), c);
                orderItemService.Save(item);
//                System.out.println("а что с листом?");
//                System.out.println(order.getProductList().size());
//
//                order.getProductList().add(item);
//                System.out.println("Добавили Item:" + item.getProduct().getTitle());
            });

            orderRepository.save(order);
            System.out.println(order.getStatus().getStatus());

//            user.getCart().forEach((key, value) -> order.getProductList().add(new OrderItem(order, productService.findById(key).get(), value)));
//            orderRepository.saveAndFlush(order);
        }
    }
}

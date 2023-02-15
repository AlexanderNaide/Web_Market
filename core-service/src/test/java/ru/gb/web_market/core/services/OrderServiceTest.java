package ru.gb.web_market.core.services;


import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.gb.web_market.api.dto.CartDto;
import ru.gb.web_market.api.dto.ProductToCartDto;
import ru.gb.web_market.core.entities.Order;
import ru.gb.web_market.core.entities.OrderItem;
import ru.gb.web_market.core.entities.OrderStatusEnums;
import ru.gb.web_market.core.integrations.AuthServiceIntegration;
import ru.gb.web_market.core.repositories.OrderRepository;

import java.util.ArrayList;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private AuthServiceIntegration authServiceIntegration;

    @MockBean
    private OrderRepository orderRepository;


    @Test
    public void createOrderTest() {
        CartDto cartDto = new CartDto();
        cartDto.setCart(new ArrayList<>());
        cartDto.getCart().add(new ProductToCartDto(1L, "001", "bread", 50.0, 2));

        Order ordertest = new Order();
        ordertest.setId(1L);
        ordertest.setUsername("Bob");
        ordertest.setStatus(OrderStatusEnums.CREATED);
        ordertest.setProductList(new ArrayList<>());

//        orderRepository

        Mockito.doReturn(cartDto).when(authServiceIntegration).getCartDto("Bob");
//        Mockito.doReturn(ordertest).when(orderRepository).save(Order.class);


//        Order order = orderService.createdOrder("Bob");
//        Assertions.assertEquals(order.getProductList().stream().mapToDouble(OrderItem::getTotalPrice).sum(), 100.00);
    }
}

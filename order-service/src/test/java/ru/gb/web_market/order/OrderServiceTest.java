package ru.gb.web_market.order;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.gb.web_market.api.dto.CartDto;
import ru.gb.web_market.api.dto.ProductToCartDto;
import ru.gb.web_market.order.entities.Order;
import ru.gb.web_market.order.entities.OrderItem;
import ru.gb.web_market.order.entities.OrderStatusEnums;
import ru.gb.web_market.order.integrations.AuthServiceIntegration;
import ru.gb.web_market.order.repositories.OrderRepository;
import ru.gb.web_market.order.services.OrderService;

import java.util.ArrayList;

@SpringBootTest
@ActiveProfiles("test")
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private AuthServiceIntegration authServiceIntegration;

//    @MockBean
    private OrderRepository orderRepository;


    @Test
    public void createOrderTest() {
        CartDto cartDto = new CartDto();
        cartDto.setCart(new ArrayList<>());
        cartDto.getCart().add(new ProductToCartDto(1L, "001", "bread", 50.0, 2));

        Order orderTest = new Order();
        orderTest.setId(1L);
        orderTest.setUsername("Bob");
        orderTest.setStatus(OrderStatusEnums.CREATED);
        orderTest.setProductList(new ArrayList<>());

        Mockito.doReturn(cartDto).when(authServiceIntegration).getCartDto("Bob");


//        Order order = orderService.createdOrder("Bob");
        // я вот так обратиться к листу не могу, потому что orderItem не подшиваются поэтому запрашиваю его снова:
//        order = orderService.findById(order.getId()).get();
//        Assertions.assertEquals(order.getProductList().stream().mapToDouble(OrderItem::getTotalPrice).sum(), 100.00);
    }
}

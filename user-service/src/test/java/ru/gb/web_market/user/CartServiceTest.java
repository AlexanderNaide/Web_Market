package ru.gb.web_market.user;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.gb.web_market.api.dto.CartDto;
import ru.gb.web_market.api.dto.ProductToCartDto;
import ru.gb.web_market.user.entities.User;
import ru.gb.web_market.user.integrations.ProductServiceIntegration;
import ru.gb.web_market.user.services.CartService;
import ru.gb.web_market.user.services.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@ActiveProfiles("test")
public class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductServiceIntegration productServiceIntegration;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void initCart(){
        User user = new User();
        user.setId(1L);
        user.setUsername("Bob");
        user.setCart(new HashMap<>());
        user.getCart().put(1L, 2);

        Mockito.doReturn(Optional.of(user)).when(userService).findByUsername("Bob");

        ProductToCartDto productToCartDto = new ProductToCartDto();
        productToCartDto.setId(1L);
        productToCartDto.setCount(2);
        productToCartDto.setArticle("100");
        productToCartDto.setPrice(50.0);
        productToCartDto.setTitle("Bread");

        CartDto cartDto = new CartDto();
        cartDto.setCart(new ArrayList<>());
        cartDto.getCart().add(productToCartDto);

        ProductToCartDto requestProductToCartDto = new ProductToCartDto();
        productToCartDto.setId(1L);
        productToCartDto.setCount(2);

        CartDto requestCartDto = new CartDto();
        cartDto.setCart(new ArrayList<>());
        cartDto.getCart().add(requestProductToCartDto);

        Mockito.doReturn(cartDto).when(productServiceIntegration).updateCart(requestCartDto);

    }

    @Test
    @org.junit.jupiter.api.Order(1)
    public void addToCartTest() {
//        mvc.perform(get("/api"))



    }
}

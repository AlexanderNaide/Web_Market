package ru.gb.web_market.user;


import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import ru.gb.web_market.api.dto.CartDto;
import ru.gb.web_market.api.dto.ProductToCartDto;
import ru.gb.web_market.user.configs.AppConfig;
import ru.gb.web_market.user.controllers.CartController;
import ru.gb.web_market.user.entities.User;
import ru.gb.web_market.user.integrations.ProductServiceIntegration;
import ru.gb.web_market.user.services.CartService;
import ru.gb.web_market.user.services.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@ActiveProfiles("test")
public class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartController cartController;

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

//        given(userService.findByUsername("Bob")).willReturn(Optional.of(user));

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
    @Order(1)
    public void getCountTest() throws Exception{
        mvc.perform(
                        get("/api/v1/cart/count")
//                                .contentType(MediaType.APPLICATION_JSON)
                                .header("username", "Bob"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(2));
    }

    @Test
    @Order(2)
    public void addToCartTest() throws Exception {
        mvc.perform(get("/api/v1/cart/add_to_cart")
                        .header("username", "Bob")
                        .param("id", "1")
                        .param("count", "1"))
                .andDo(print())
                .andExpect(status().isOk());


    }

    @Test
    @Order(3)
    public void getCartTest() throws Exception{
        mvc.perform(
                get("/api/v1/cart")
//                        .contentType(MediaType.APPLICATION_JSON)
                        .header("username", "Bob"))
                .andDo(print())
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.cart.size()").value(1));
//                .andExpect(jsonPath("$", hasSize(1)));
    }



}

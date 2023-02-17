package ru.gb.web_market.products.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.gb.web_market.api.dto.CartDto;
import ru.gb.web_market.api.dto.ProductToCartDto;
import ru.gb.web_market.products.entities.Product;
import ru.gb.web_market.products.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;


    @Test
    public void updateProductFromCartTest(){

        ProductToCartDto product = new ProductToCartDto();
        product.setId(1L);
        product.setCount(2);
        CartDto base = new CartDto();
        base.setCart(new ArrayList<>());
        base.getCart().add(product);

        Product bread = new Product();
        bread.setId(1L);
        bread.setArticle("300");
        bread.setTitle("Bread");
        bread.setPrice(55.0);
        Mockito.doReturn(Optional.of(bread)).when(productRepository)
                .findById(1L);

        CartDto returnDto = productService.updateProductFromCart(base);


        // Тестируем, что на 1 обработку запроса сервис обращается к репозиторию 1 раз.
        Mockito.verify(productRepository, Mockito.times(1)).findById(ArgumentMatchers.any());

        // Тестируем, что сервис заполняет все поля, и сумма соответствует ожидаемой
        Assertions.assertEquals(returnDto.getCart().stream().mapToDouble(e -> e.getPrice() * e.getCount()).sum(), 110.0);
    }

}

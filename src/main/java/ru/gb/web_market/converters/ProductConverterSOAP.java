package ru.gb.web_market.converters;

import org.springframework.stereotype.Component;
import ru.gb.web_market.dto.ProductDto;
import ru.gb.web_market.dto.ProductFullDto;
import ru.gb.web_market.dto.ProductToCartDto;
import ru.gb.web_market.entities.Product;

@Component
public class ProductConverterSOAP {

    public ru.gb.web_market.soap.products.Product entityToProductSOAP(Product product){
        ru.gb.web_market.soap.products.Product responceProduct = new ru.gb.web_market.soap.products.Product();
        responceProduct.setId(product.getId());
        responceProduct.setTitle(product.getTitle());
        responceProduct.setPrice(product.getPrice());
        responceProduct.setArticle(product.getArticle());
        responceProduct.setCategory(product.getCategory().getTitle());
        return responceProduct;
    }

}

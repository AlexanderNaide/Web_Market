package ru.gb.web_market.converters;

import org.springframework.stereotype.Component;
import ru.gb.web_market.dto.ProductDto;
import ru.gb.web_market.dto.ProductFullDto;
import ru.gb.web_market.dto.ProductToCartDto;
import ru.gb.web_market.entities.Product;

@Component
public class ProductConverter {

/*    public Product dtoToEntity(ProductDto productDto){
        return new Product(productDto.getId(), null, null, null, null, productDto.getArticle(), null, productDto.getTitle(), productDto.getPrice(), null, null, null, null, null);
    }*/

    public ProductDto entityToDto(Product product){
        return new ProductDto(product.getId(), product.getArticle(), product.getTitle(), product.getPrice());
    }

    public ProductToCartDto entityToCardDto(Product product, Integer count){
        return new ProductToCartDto(product.getId(), product.getArticle(), product.getTitle(), product.getPrice(), count);
    }

    public ProductFullDto entityToFullDto(Product product){
        return new ProductFullDto(product.getId(), product.getCategory().getTitle(), product.getArticle(), product.getTitle(), product.getPrice(), product.getManufacturer().getTitle(), product.getDescription());
    }

}

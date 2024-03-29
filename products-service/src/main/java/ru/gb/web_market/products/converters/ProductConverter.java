package ru.gb.web_market.products.converters;

import org.springframework.stereotype.Component;
import ru.gb.web_market.api.dto.ProductDto;
import ru.gb.web_market.api.dto.ProductFullDto;
import ru.gb.web_market.api.dto.ProductToCartDto;
import ru.gb.web_market.products.entities.Product;


@Component
public class ProductConverter {

/*    public Product dtoToEntity(ProductDto productDto){
        return new Product(productDto.getId(), null, null, null, null, productDto.getArticle(), null, productDto.getTitle(), productDto.getPrice(), null, null, null, null, null);
    }*/

    public ProductDto entityToDto(Product product){
//        return new ProductDto(product.getId(), product.getArticle(), product.getTitle(), product.getPrice());

        // Заменено на Builder

        return ProductDto.Builder.newBuilder()
                .setId(product.getId())
                .setArticle(product.getArticle())
                .setTitle(product.getTitle())
                .setPrice(product.getPrice())
                .build();
    }

    public ProductToCartDto entityToCardDto(Product product, Integer count){
//        return new ProductToCartDto(product.getId(), product.getArticle(), product.getTitle(), product.getPrice(), count);

        // Заменено на Builder

        return ProductToCartDto.Builder.newBuilder()
                .setId(product.getId())
                .setArticle(product.getArticle())
                .setTitle(product.getTitle())
                .setPrice(product.getPrice())
                .setCount(count)
                .build();
    }

    public ProductFullDto entityToFullDto(Product product){
//        return new ProductFullDto(product.getId(), product.getCategory().getTitle(), product.getArticle(), product.getTitle(), product.getPrice(), product.getManufacturer().getTitle(), product.getDescription());

        // Заменено на Builder

        return ProductFullDto.Builder.newBuilder()
                .setId(product.getId())
                .setSubCategory(product.getCategory().getTitle())
                .setArticle(product.getArticle())
                .setTitle(product.getTitle())
                .setPrice(product.getPrice())
                .setManufacturer(product.getManufacturer().getTitle())
                .setDescription(product.getDescription())
                .build();
    }

}

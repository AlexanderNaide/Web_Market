package ru.gb.web_market.soap.converters;

import org.springframework.stereotype.Component;
import ru.gb.web_market.dto.ProductDto;
import ru.gb.web_market.dto.ProductFullDto;
import ru.gb.web_market.dto.ProductToCartDto;
import ru.gb.web_market.entities.Category;
import ru.gb.web_market.entities.Product;

@Component
public class ProductConverterSOAP {

    public ru.gb.web_market.soap.products.Product entityToProductSOAP(Product product){
        ru.gb.web_market.soap.products.Product responseProduct = new ru.gb.web_market.soap.products.Product();
        responseProduct.setId(product.getId());
        responseProduct.setTitle(product.getTitle());
        responseProduct.setPrice(product.getPrice());
        responseProduct.setArticle(product.getArticle());
        responseProduct.setCategory(product.getCategory().getTitle());
        return responseProduct;
    }

    public ru.gb.web_market.soap.categories.Category categoryEntityToCategorySOAP(Category category){
        ru.gb.web_market.soap.categories.Category responseCategory = new ru.gb.web_market.soap.categories.Category();
        responseCategory.setId(category.getId());
        responseCategory.setTitle(category.getTitle());
        return responseCategory;
    }

}

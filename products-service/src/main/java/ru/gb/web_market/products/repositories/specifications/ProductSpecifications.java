package ru.gb.web_market.products.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.gb.web_market.products.entities.Category;
import ru.gb.web_market.products.entities.Manufacturer;
import ru.gb.web_market.products.entities.Product;

public class ProductSpecifications {

    public static Specification<Product> priceGreaterOrEqualsThan(Double minPrice){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<Product> priceLessenOrEqualsThan(Double maxPrice){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    public static Specification<Product> titleLike(String title){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", title));
    }

    public static Specification<Product> artLike(String title){
//        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("article").as(String.class), String.format("%%%s%%", title));
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("article"), String.format("%%%s%%", title));
    }

    public static Specification<Product> category(Category category){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("category"), category);
    }

    public static Specification<Product> manufacturer(Manufacturer man){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("manufacturer"), man);
    }

}

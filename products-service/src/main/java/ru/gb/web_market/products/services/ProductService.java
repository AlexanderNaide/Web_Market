package ru.gb.web_market.products.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.gb.web_market.api.dto.CartDto;
import ru.gb.web_market.products.repositories.specifications.ProductSpecifications;
import ru.gb.web_market.products.entities.Category;
import ru.gb.web_market.products.entities.Manufacturer;
import ru.gb.web_market.products.entities.Product;
import ru.gb.web_market.products.repositories.ProductRepository;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoriesService categoriesService;
    private final ManufacturerService manufacturerService;


    public Page<Product> findCom(Double minPrice, Double maxPrice, String title, Long categories, Long subCategories, String man, Integer page){

        Specification<Product> spec = Specification.where(null);

        if(minPrice != null){
            spec = spec.and(ProductSpecifications.priceGreaterOrEqualsThan(minPrice));
        }
        if(maxPrice != null){
            spec = spec.and(ProductSpecifications.priceLessenOrEqualsThan(maxPrice));
        }
        if(title != null){
            spec = spec.and(ProductSpecifications.titleLike(title)).or(ProductSpecifications.artLike(title));
        }

        if(subCategories != null){
//            long catId = Long.parseLong(Objects.requireNonNull(Arrays.stream(subCategories.split(",")).findFirst().orElse(null)));
            Optional<Category> categoryOptional = categoriesService.findById(subCategories);
            if(categoryOptional.isPresent()){
                spec = spec.and(ProductSpecifications.category(categoryOptional.get()));
            }
        } else if (categories != null) {
//            long catId = Long.parseLong(Objects.requireNonNull(Arrays.stream(categories.split(",")).findFirst().orElse(null)));
            Optional<Category> categoryOptional = categoriesService.findById(categories);
            if(categoryOptional.isPresent()){
                spec = spec.and(ProductSpecifications.category(categoryOptional.get()));
            }
        }

        if(man != null){
//            spec = spec.and(ProductSpecifications.manufacturer(man));
            long manId = Long.parseLong(Objects.requireNonNull(Arrays.stream(man.split(",")).findFirst().orElse(null)));
            Optional<Manufacturer> manufacturerOptional = manufacturerService.findById(manId);
            if(manufacturerOptional.isPresent()){
                spec = spec.and(ProductSpecifications.manufacturer(manufacturerOptional.get()));
            }
        }

        return productRepository.findAll(spec, PageRequest.of(page - 1, 10));
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public CartDto updateProductFromCart(CartDto cartDto){
        cartDto.getCart().forEach(e -> {
            Product product = productRepository.findById(e.getId()).orElse(null);
            if(product != null){
                e.setArticle(product.getArticle());
                e.setTitle(product.getTitle());
                e.setPrice(product.getPrice());
            } else {
                e.setId(0L);
            }
        });
        cartDto.getCart().removeIf(e -> e.getId() == 0L);
        return cartDto;
    }
}

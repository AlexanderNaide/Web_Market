package ru.gb.web_market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.gb.web_market.aspect.Timer;
import ru.gb.web_market.entities.Category;
import ru.gb.web_market.entities.Manufacturer;
import ru.gb.web_market.entities.Product;
import ru.gb.web_market.repositories.ProductRepository;
import ru.gb.web_market.repositories.specifications.ProductSpecifications;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor // Ломбоковская аннотация, которая инициализирует final поля вместо конструктора с @Autowired
@Timer
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoriesService categoriesService;
    private final ManufacturerService manufacturerService;


    public Page<Product> findCom(Double minPrice, Double maxPrice, String title, String categories, String subCategories, String man, Integer page){

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
            long catId = Long.parseLong(Objects.requireNonNull(Arrays.stream(subCategories.split(",")).findFirst().orElse(null)));
            Optional<Category> categoryOptional = categoriesService.findById(catId);
            if(categoryOptional.isPresent()){
                spec = spec.and(ProductSpecifications.category(categoryOptional.get()));
            }
        } else if (categories != null) {
            long catId = Long.parseLong(Objects.requireNonNull(Arrays.stream(categories.split(",")).findFirst().orElse(null)));
            Optional<Category> categoryOptional = categoriesService.findById(catId);
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
}

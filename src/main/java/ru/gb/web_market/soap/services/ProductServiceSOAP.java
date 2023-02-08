package ru.gb.web_market.soap.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.gb.web_market.aspect.Timer;
import ru.gb.web_market.soap.converters.ProductConverterSOAP;
import ru.gb.web_market.entities.Product;
import ru.gb.web_market.repositories.ProductRepository;
import ru.gb.web_market.repositories.specifications.ProductSpecifications;
import ru.gb.web_market.soap.products.GetProductByCategoryRequest;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Timer
public class ProductServiceSOAP {
    private final ProductRepository productRepository;
    private final CategoriesServiceSOAP categoriesServiceSOAP;

    private final ProductConverterSOAP productConverterSOAP;

    public List<ru.gb.web_market.soap.products.Product> getProductByCategory(GetProductByCategoryRequest request){
        Specification<Product> spec = Specification.where(null);
        int page = request.getPage().intValue();
        spec = spec.and(ProductSpecifications.category(categoriesServiceSOAP.findByTitle(request.getCategory()).orElseThrow(RuntimeException::new)));
        return productRepository.findAll(spec, PageRequest.of(page - 1, 10)).stream().map(productConverterSOAP::entityToProductSOAP).toList();
    }
}
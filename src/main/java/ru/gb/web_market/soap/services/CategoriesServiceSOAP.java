package ru.gb.web_market.soap.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.web_market.soap.converters.ProductConverterSOAP;
import ru.gb.web_market.entities.Category;
import ru.gb.web_market.repositories.CategoriesRepository;
import ru.gb.web_market.soap.categories.GetCategoriesByParentCategoryRequest;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriesServiceSOAP {

    private final CategoriesRepository categoriesRepository;

    private final ProductConverterSOAP productConverterSOAP;

    public List<ru.gb.web_market.soap.categories.Category> findAllByParentCategory(GetCategoriesByParentCategoryRequest request) {
        ru.gb.web_market.entities.Category category = findByTitle(request.getTitle()).get();
        return categoriesRepository.findAllByParentCategory(category).stream().map(productConverterSOAP::categoryEntityToCategorySOAP).toList();
    }

    public Optional<Category> findByTitle(String title){
        return categoriesRepository.findFirstByTitle(title);
    }
}

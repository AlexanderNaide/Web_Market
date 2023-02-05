package ru.gb.web_market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.web_market.entities.Category;
import ru.gb.web_market.repositories.CategoriesRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriesServiceSOAP {

    private final CategoriesRepository categoriesRepository;

    public List<Category> findAllByParentCategory(Category category) {
        return categoriesRepository.findAllByParentCategory(category);
    }

    public Optional<Category> findByTitle(String title){
        return categoriesRepository.findFirstByTitle(title);
    }
}

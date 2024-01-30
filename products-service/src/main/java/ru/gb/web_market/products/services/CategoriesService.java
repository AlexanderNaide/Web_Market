package ru.gb.web_market.products.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.web_market.api.dto.CategoryDto;
import ru.gb.web_market.products.entities.Category;
import ru.gb.web_market.products.repositories.CategoriesRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface CategoriesService {

    List<CategoryDto> findCategories(Long id);
    Optional<Category> findById(Long id);
}

package ru.gb.web_market.products.ceche;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.web_market.api.dto.CategoryDto;
import ru.gb.web_market.products.entities.Category;
import ru.gb.web_market.products.entities.Manufacturer;
import ru.gb.web_market.products.services.CategoriesRepositoryService;
import ru.gb.web_market.products.services.CategoriesService;
import ru.gb.web_market.products.services.ManufacturerRepositoryService;
import ru.gb.web_market.products.services.ManufacturerService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service(value = "cacheCategories")
@RequiredArgsConstructor
public class CategoriesCache implements CategoriesService {

    private final CategoriesRepositoryService categoriesRepositoryService;
    private final Map<Long, List<CategoryDto>> categoriesCache;
    private final Map<Long, Category> categoryCache;

    @Override
    public List<CategoryDto> findCategories(Long id) {

        if (!categoriesCache.containsKey(id)){
            categoriesCache.put(id, categoriesRepositoryService.findCategories(id).stream().map(e -> new CategoryDto(e.getId(), e.getTitle())).toList());
        }
        return categoriesCache.get(id);
    }

    @Override
    public Optional<Category> findById(Long id) {
        if (!categoryCache.containsKey(id)){
            Optional<Category> getCat = categoriesRepositoryService.findById(id);
            getCat.ifPresent(category -> categoryCache.put(id, category));
        }
        return Optional.of(categoryCache.get(id));
    }
}

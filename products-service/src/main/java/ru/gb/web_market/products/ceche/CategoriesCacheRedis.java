package ru.gb.web_market.products.ceche;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.gb.web_market.api.dto.CategoryDto;
import ru.gb.web_market.products.entities.Category;
import ru.gb.web_market.products.services.CategoriesRepositoryService;
import ru.gb.web_market.products.services.CategoriesService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service(value = "cacheCategoriesRedis")
public class CategoriesCacheRedis implements CategoriesService {

    private final CategoriesRepositoryService categoriesRepositoryService;
    private final RedisTemplate<Long, List<CategoryDto>> categoriesCache;
//    private final RedisTemplate<Long, Category> categoryCache;

    private final Map<Long, Category> categoryCache;

    @Autowired
    public CategoriesCacheRedis(CategoriesRepositoryService categoriesRepositoryService,
                                @Qualifier("listCategoryRedis") RedisTemplate<Long, List<CategoryDto>> categoriesCache
//                                @Qualifier("categoryRedis") RedisTemplate<Long, Category> categoryCache
    ) {
        this.categoriesRepositoryService = categoriesRepositoryService;
        this.categoriesCache = categoriesCache;
//        this.categoryCache = categoryCache;
        this.categoryCache = new HashMap<>();
    }

    @Override
    public List<CategoryDto> findCategories(Long id) {

        categoriesCache.opsForValue().get(id);
//        categoriesCache.hasKey(id);

        if (Boolean.FALSE.equals(categoriesCache.hasKey(id))){
            System.out.println("Категория " + id + " отсутствует");
            categoriesCache.opsForValue().set(id, categoriesRepositoryService.findCategories(id).stream().map(e -> new CategoryDto(e.getId(), e.getTitle())).toList());
        }
        return categoriesCache.opsForValue().get(id);
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

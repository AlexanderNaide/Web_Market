package ru.gb.web_market.products.ceche;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.gb.web_market.api.dto.CategoryDto;
import ru.gb.web_market.products.entities.Category;
import ru.gb.web_market.products.entities.Manufacturer;
import ru.gb.web_market.products.services.CategoriesRepositoryService;
import ru.gb.web_market.products.services.CategoriesService;

import java.util.*;

/**
 * Класс для хранения кэша в Redis
 */

@Service(value = "cacheCategoriesRedis")
public class CategoriesCacheRedis implements CategoriesService {

    private static final String CATEGORY_LIST_PREFIX = "clp_";
    private static final String CATEGORY_ENTITY_PREFIX = "cep_";
    private final CategoriesRepositoryService categoriesRepositoryService;
    private final RedisTemplate<String, List<CategoryDto>> categoriesCacheRedis;
    private final RedisTemplate<String, Category> categoryCacheRedis;

    public CategoriesCacheRedis(CategoriesRepositoryService categoriesRepositoryService,
                                @Qualifier("listCategoryRedis") RedisTemplate<String, List<CategoryDto>> categoriesCacheRedis,
                                @Qualifier("categoryRedis") RedisTemplate<String, Category> categoryCacheRedis
                                ){
        this.categoriesRepositoryService = categoriesRepositoryService;
        this.categoriesCacheRedis = categoriesCacheRedis;
        this.categoryCacheRedis = categoryCacheRedis;
    }

    @Override
    public List<CategoryDto> findCategories(Long id) {
        if (Boolean.FALSE.equals(categoriesCacheRedis.hasKey(CATEGORY_LIST_PREFIX + id))){
            List<CategoryDto> list = categoriesRepositoryService.findCategories(id).stream().map(e -> new CategoryDto(e.getId(), e.getTitle())).toList();
            categoriesCacheRedis.opsForValue().set(CATEGORY_LIST_PREFIX + id, list);
        }
        return categoriesCacheRedis.opsForValue().get(CATEGORY_LIST_PREFIX + id);
    }

    @Override
    public Optional<Category> findById(Long id) {
        if (Boolean.FALSE.equals(categoryCacheRedis.hasKey(CATEGORY_ENTITY_PREFIX + id))){
            Optional<Category> oCat = categoriesRepositoryService.findById(id);
            oCat.ifPresent(category -> categoryCacheRedis.opsForValue().set(CATEGORY_ENTITY_PREFIX + id, category));
        }
        return Optional.ofNullable(categoryCacheRedis.opsForValue().get(CATEGORY_ENTITY_PREFIX + id));
    }
}

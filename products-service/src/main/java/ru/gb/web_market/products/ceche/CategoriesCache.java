package ru.gb.web_market.products.ceche;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.web_market.api.dto.CategoryDto;
import ru.gb.web_market.products.entities.Manufacturer;
import ru.gb.web_market.products.services.CategoriesRepositoryService;
import ru.gb.web_market.products.services.CategoriesService;
import ru.gb.web_market.products.services.ManufacturerRepositoryService;
import ru.gb.web_market.products.services.ManufacturerService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service(value = "cache")
@RequiredArgsConstructor
public class CategoriesCache implements CategoriesService {

    private final CategoriesRepositoryService categoriesRepositoryService;

    private final Map<Long, List<String>> categoriesCache;

    @Override
    public List<CategoryDto> findCategories(Long id) {


        return categoriesRepository.findCategories(id).stream().map(e -> new CategoryDto(e.getId(), e.getTitle())).toList();
    }

    @Override
    public List<String> findManufacturer(Long categories, Long subCategories) {

        System.out.println("Попали в ManufacturerCache, findManufacturer");
        System.out.println("    тут -> " + categories + " - " + categoriesCache.containsKey(categories) + " / " + subCategories + " - " + categoriesCache.containsKey(subCategories));

        if (subCategories != null){
            if (!categoriesCache.containsKey(subCategories)){
                categoriesCache.put(subCategories, categoriesRepositoryService.findCategories(null, subCategories));
            }
            return categoriesCache.get(subCategories);
        } else if (categories != null) {
            if (!categoriesCache.containsKey(categories)){
                categoriesCache.put(categories, categoriesRepositoryService.findCategories(categories, null));
            }
            return categoriesCache.get(categories);
        } else {
            if (!categoriesCache.containsKey(0L)){
                categoriesCache.put(0L, categoriesRepositoryService.findCategories(null, null));
            }
            return categoriesCache.get(0L);
        }
    }

    @Override
    public Optional<Manufacturer> findById(long id) {
        System.out.println("Попали в ManufacturerCache, findById");
        return categoriesRepositoryService.findById(id);
    }
}

package ru.gb.web_market.products.ceche;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.web_market.products.entities.Category;
import ru.gb.web_market.products.entities.Manufacturer;
import ru.gb.web_market.products.services.ManufacturerRepositoryService;
import ru.gb.web_market.products.services.ManufacturerService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service(value = "cacheManufacturer")
@RequiredArgsConstructor
public class ManufacturerCache implements ManufacturerService {

    private final ManufacturerRepositoryService manufacturerRepositoryService;

    private final Map<Long, List<String>> manufacturerListCache;
    private final Map<Long, Manufacturer> manufacturerCache;

    @Override
    public List<String> findManufacturer(Long categories, Long subCategories) {

        if (subCategories != null){
            if (!manufacturerListCache.containsKey(subCategories)){
                manufacturerListCache.put(subCategories, manufacturerRepositoryService.findManufacturer(null, subCategories));
            }
            return manufacturerListCache.get(subCategories);
        } else if (categories != null) {
            if (!manufacturerListCache.containsKey(categories)){
                manufacturerListCache.put(categories, manufacturerRepositoryService.findManufacturer(categories, null));
            }
            return manufacturerListCache.get(categories);
        } else {
            if (!manufacturerListCache.containsKey(0L)){
                manufacturerListCache.put(0L, manufacturerRepositoryService.findManufacturer(null, null));
            }
            return manufacturerListCache.get(0L);
        }
    }

    @Override
    public Optional<Manufacturer> findById(long id) {
        if (!manufacturerCache.containsKey(id)){
            Optional<Manufacturer> getMan = manufacturerRepositoryService.findById(id);
            getMan.ifPresent(manufacturer -> manufacturerCache.put(id, manufacturer));
        }
        return Optional.of(manufacturerCache.get(id));
    }
}

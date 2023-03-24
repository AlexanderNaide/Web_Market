package ru.gb.web_market.products.ceche;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.web_market.products.entities.Manufacturer;
import ru.gb.web_market.products.services.ManufacturerRepositoryService;
import ru.gb.web_market.products.services.ManufacturerService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service(value = "cache")
@RequiredArgsConstructor
public class ManufacturerCache implements ManufacturerService {

    private final ManufacturerRepositoryService manufacturerRepositoryService;

    private final Map<Long, List<String>> manufacturerCache;

    @Override
    public List<String> findManufacturer(Long categories, Long subCategories) {

        System.out.println("Попали в ManufacturerCache, findManufacturer");
        System.out.println("    тут -> " + categories + " - " + manufacturerCache.containsKey(categories) + " / " + subCategories + " - " + manufacturerCache.containsKey(subCategories));

        if (subCategories != null){
            if (!manufacturerCache.containsKey(subCategories)){
                manufacturerCache.put(subCategories, manufacturerRepositoryService.findManufacturer(null, subCategories));
            }
            return manufacturerCache.get(subCategories);
        } else if (categories != null) {
            if (!manufacturerCache.containsKey(categories)){
                manufacturerCache.put(categories, manufacturerRepositoryService.findManufacturer(categories, null));
            }
            return manufacturerCache.get(categories);
        } else {
            if (!manufacturerCache.containsKey(0L)){
                manufacturerCache.put(0L, manufacturerRepositoryService.findManufacturer(null, null));
            }
            return manufacturerCache.get(0L);
        }
    }

    @Override
    public Optional<Manufacturer> findById(long id) {
        System.out.println("Попали в ManufacturerCache, findById");
        return manufacturerRepositoryService.findById(id);
    }
}

package ru.gb.web_market.products.ceche;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.gb.web_market.products.entities.Category;
import ru.gb.web_market.products.entities.Manufacturer;
import ru.gb.web_market.products.services.ManufacturerRepositoryService;
import ru.gb.web_market.products.services.ManufacturerService;

import java.util.*;

/**
 * Класс для хранения кэша в Redis
 */

@Service(value = "cacheManufacturerRedis")
//@RequiredArgsConstructor
public class ManufacturerCacheRedis implements ManufacturerService {

    private static final String MANUFACTURER_LIST_PREFIX = "mlp_";
    private static final String MANUFACTURER_ENTITY_PREFIX = "mep_";
    private final ManufacturerRepositoryService manufacturerRepositoryService;
    private final RedisTemplate<String, List<String>> manufacturerListCacheRedis;
    private final RedisTemplate<String, Manufacturer> manufacturerCacheRedis;

    public ManufacturerCacheRedis(ManufacturerRepositoryService manufacturerRepositoryService,
                                  @Qualifier("listManufacturerRedis") RedisTemplate<String, List<String>> manufacturerListCacheRedis,
                                  @Qualifier("manufacturerRedis") RedisTemplate<String, Manufacturer> manufacturerCacheRedis) {
        this.manufacturerRepositoryService = manufacturerRepositoryService;
        this.manufacturerListCacheRedis = manufacturerListCacheRedis;
        this.manufacturerCacheRedis = manufacturerCacheRedis;
    }

    @Override
    public List<String> findManufacturer(Long categories, Long subCategories) {

        if (subCategories != null){
            if (Boolean.FALSE.equals(manufacturerListCacheRedis.hasKey(MANUFACTURER_LIST_PREFIX + subCategories))){
                manufacturerListCacheRedis.opsForValue().set(MANUFACTURER_LIST_PREFIX + subCategories, manufacturerRepositoryService.findManufacturer(null, subCategories));
            }
            return manufacturerListCacheRedis.opsForValue().get(MANUFACTURER_LIST_PREFIX + subCategories);
        } else if (categories != null) {
            if (Boolean.FALSE.equals(manufacturerListCacheRedis.hasKey(MANUFACTURER_LIST_PREFIX + categories))){
                manufacturerListCacheRedis.opsForValue().set(MANUFACTURER_LIST_PREFIX + categories, manufacturerRepositoryService.findManufacturer(categories, null));
            }
            return manufacturerListCacheRedis.opsForValue().get(MANUFACTURER_LIST_PREFIX + categories);
        } else {
            if (Boolean.FALSE.equals(manufacturerListCacheRedis.hasKey(MANUFACTURER_LIST_PREFIX + "00"))){
                manufacturerListCacheRedis.opsForValue().set(MANUFACTURER_LIST_PREFIX + "00", manufacturerRepositoryService.findManufacturer(null, null));
            }
            return manufacturerListCacheRedis.opsForValue().get(MANUFACTURER_LIST_PREFIX + "00");
        }
    }

    @Override
    public Optional<Manufacturer> findById(long id) {

        if (Boolean.FALSE.equals(manufacturerCacheRedis.hasKey(MANUFACTURER_ENTITY_PREFIX + id))){
            Optional<Manufacturer> oMan = manufacturerRepositoryService.findById(id);
            oMan.ifPresent(manufacturer -> manufacturerCacheRedis.opsForValue().set(MANUFACTURER_ENTITY_PREFIX + id, manufacturer));
        }
        return Optional.ofNullable(manufacturerCacheRedis.opsForValue().get(MANUFACTURER_ENTITY_PREFIX + id));
    }
}

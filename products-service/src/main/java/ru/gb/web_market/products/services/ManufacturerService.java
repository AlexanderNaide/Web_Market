package ru.gb.web_market.products.services;

import ru.gb.web_market.products.entities.Manufacturer;

import java.util.List;
import java.util.Optional;

public interface ManufacturerService {
    List<String> findManufacturer(Long categories, Long subCategories);
    Optional<Manufacturer> findById(long id);
}

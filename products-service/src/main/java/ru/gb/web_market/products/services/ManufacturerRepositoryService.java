package ru.gb.web_market.products.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.web_market.products.entities.Manufacturer;
import ru.gb.web_market.products.repositories.ManufacturerRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ManufacturerRepositoryService implements ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    @Override
    public List<String> findManufacturer(Long categories, Long subCategories){
        if (subCategories != null){
            return manufacturerRepository.findManufacturer(subCategories);
        } else if (categories != null) {
            return manufacturerRepository.findManufacturer(categories);
        } else {
            return manufacturerRepository.findAllManufacturer();
        }
    }

    @Override
    public Optional<Manufacturer> findById(long id){
        return manufacturerRepository.findById(id);
    }
}

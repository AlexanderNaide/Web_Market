package ru.gb.web_market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.web_market.entities.Category;
import ru.gb.web_market.entities.Manufacturer;
import ru.gb.web_market.repositories.ManufacturerRepository;

import java.util.*;

@Service
@RequiredArgsConstructor // Ломбоковская аннотация, которая инициализирует final поля вместо конструктора с @Autowired
public class ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    public List<String> findManufacturer(String categories, String subCategories){
//    public List<String> findManufacturer(String categories, String subCategories){
        long cat;
        if (subCategories != null){
            cat = Long.parseLong(Objects.requireNonNull(Arrays.stream(subCategories.split(",")).findFirst().orElse(null)));
            return manufacturerRepository.findManufacturer(cat);
        } else if (categories != null) {
            cat = Long.parseLong(Objects.requireNonNull(Arrays.stream(categories.split(",")).findFirst().orElse(null)));
            return manufacturerRepository.findManufacturer(cat);
        } else {
            return manufacturerRepository.findAllManufacturer();
        }
    }

    public Optional<Manufacturer> findById(long id){
        return manufacturerRepository.findById(id);
    }
}

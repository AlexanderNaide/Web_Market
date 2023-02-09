package ru.gb.web_market.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.web_market.core.entities.Category;
import ru.gb.web_market.core.repositories.CategoriesRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor // Ломбоковская аннотация, которая инициализирует final поля вместо конструктора с @Autowired
public class CategoriesService {

    private final CategoriesRepository categoriesRepository;

    public List<String> findAllCategories() {
        return categoriesRepository.findAllCategories();
    }

    public List<String> findAllSubCategories(String cat) {
        long catId = Long.parseLong(Objects.requireNonNull(Arrays.stream(cat.split(",")).findFirst().orElse(null)));
        return categoriesRepository.findAllSubCategories(catId);
    }

    public Optional<Category> findById(long id){
        return categoriesRepository.findById(id);
    }
}

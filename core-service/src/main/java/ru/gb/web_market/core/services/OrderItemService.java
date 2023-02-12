package ru.gb.web_market.core.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.web_market.core.entities.OrderItem;
import ru.gb.web_market.core.repositories.OrderItemRepository;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

/*    public List<String> findAllCategories() {
        return categoriesRepository.findAllCategories();
    }

    public List<String> findAllSubCategories(String cat) {
        long catId = Long.parseLong(Objects.requireNonNull(Arrays.stream(cat.split(",")).findFirst().orElse(null)));
        return categoriesRepository.findAllSubCategories(catId);
    }

    public Optional<Category> findById(long id){
        return categoriesRepository.findById(id);
    }*/

    @Transactional
    public void Save (OrderItem item){
        orderItemRepository.save(item);
    }

}

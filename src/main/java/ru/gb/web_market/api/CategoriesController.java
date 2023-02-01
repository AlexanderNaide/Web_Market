package ru.gb.web_market.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.gb.web_market.services.CategoriesService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoriesController {

    private final CategoriesService categoriesService;

    @GetMapping
    public List<String> getCategories(){
        return categoriesService.findAllCategories();
    }

    @GetMapping("/sub")
    public List<String> getSubCategories(@RequestParam(required = false) String cat){
        return categoriesService.findAllSubCategories(cat);
    }
}
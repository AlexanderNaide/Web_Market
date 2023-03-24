package ru.gb.web_market.products.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.gb.web_market.api.dto.CategoryDto;
import ru.gb.web_market.products.services.CategoriesService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
//@RequiredArgsConstructor
public class CategoriesController {

    private final CategoriesService categoriesService;

    @Autowired
    public CategoriesController(@Qualifier("cacheCategories") CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    @PostMapping()
    public List<CategoryDto> getCategories(@RequestParam(required = false) Long cat){
        return categoriesService.findCategories(cat == null ? 1L : cat);
    }
}
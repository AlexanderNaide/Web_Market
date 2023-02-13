package ru.gb.web_market.products.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.gb.web_market.api.dto.CategoryDto;
import ru.gb.web_market.api.dto.ListDto;
import ru.gb.web_market.products.services.CategoriesService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CategoriesController {

    private final CategoriesService categoriesService;

    @PostMapping()
    public List<CategoryDto> getCategories(@RequestParam(required = false) Long cat){
        return categoriesService.findCategories(cat == null ? 1L : cat);
    }
}
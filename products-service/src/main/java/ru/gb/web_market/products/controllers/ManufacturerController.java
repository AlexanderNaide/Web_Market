package ru.gb.web_market.products.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.gb.web_market.api.dto.CategoryDto;
import ru.gb.web_market.products.services.ManufacturerService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/manufacturers")
//@RequiredArgsConstructor
public class ManufacturerController {
    private final ManufacturerService manufacturerService;
    @Autowired
    public ManufacturerController(@Qualifier("cacheManufacturer") ManufacturerService manufacturerService) {
//    public ManufacturerController(@Qualifier("cacheManufacturerRedis") ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @GetMapping
    public List<String> getManufacturer(
            @RequestParam(required = false) Long cat,
            @RequestParam(required = false) Long sub_cat
    ){
        return manufacturerService.findManufacturer(cat, sub_cat);
    }
}
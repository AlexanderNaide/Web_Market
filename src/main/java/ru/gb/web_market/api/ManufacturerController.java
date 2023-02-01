package ru.gb.web_market.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.gb.web_market.services.ManufacturerService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/manufacturers")
@RequiredArgsConstructor
public class ManufacturerController {
    private final ManufacturerService manufacturerService;

    @GetMapping
    public List<String> getManufacturer(
//    public Map<Long, String> getManufacturer(
            @RequestParam(required = false) String cat,
            @RequestParam(required = false) String sub_cat
    ){
        return manufacturerService.findManufacturer(cat, sub_cat);
    }
}
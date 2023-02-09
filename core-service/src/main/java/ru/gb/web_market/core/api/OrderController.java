package ru.gb.web_market.core.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.gb.web_market.core.converters.ProductConverter;
import ru.gb.web_market.core.dto.ProductDto;
import ru.gb.web_market.core.dto.ProductFullDto;
import ru.gb.web_market.core.exceptions.ResourceNotFoundException;
import ru.gb.web_market.core.services.ProductService;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final ProductService productService;
    private final ProductConverter productConverter;



    @GetMapping
    public Page<ProductDto> findAll(){
        return productService.findCom(null, null, null, null, null, null,1).map(productConverter::entityToDto);
    }
}
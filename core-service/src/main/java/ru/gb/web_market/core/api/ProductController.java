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
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductConverter productConverter;

    @GetMapping("/{id}")
    public ProductFullDto getProductById(@PathVariable Long id){
//        return new ProductFullDto(productService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
//        return productService.findById(id).map(ProductFullDto::new).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//        return productService.findById(id).map(productConverter::entityToFullDto).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return productService.findById(id).map(productConverter::entityToFullDto).orElseThrow(() -> new ResourceNotFoundException("Продукт не найден в базе данных товаров, id:" + id));
    }

    @PostMapping
    public Page<ProductDto> upAll(@RequestParam(required = false, defaultValue = "1") Integer page,
                                  @RequestParam(required = false) String val,
                                  @RequestParam(required = false) Double min,
                                  @RequestParam(required = false) Double max,
                                  @RequestParam(required = false) String cat,
                                  @RequestParam(required = false) String sub_cat,
                                  @RequestParam(required = false) String man
                                  ){
        if(page < 1){
            page = 1;
        }
        return productService.findCom(min, max, val, cat, sub_cat, man, page).map(productConverter::entityToDto);
    }

    @GetMapping
    public Page<ProductDto> findAll(){
        return productService.findCom(null, null, null, null, null, null,1).map(productConverter::entityToDto);
    }
}
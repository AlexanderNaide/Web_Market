package ru.gb.web_market.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductToCartDto {

    private Long id;

    private String article;

    private String title;

    private Double price;

    private Integer count;

}

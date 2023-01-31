package ru.gb.web_market.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor //конструктор без аргументов для Джексона
@AllArgsConstructor //конструктор со всеми аргументами
public class ProductFullDto {

    private Long id;

    private String subCategory;

    private String article;

    private String title;

    private Double price;

    private String manufacturer;

    private String description;
}

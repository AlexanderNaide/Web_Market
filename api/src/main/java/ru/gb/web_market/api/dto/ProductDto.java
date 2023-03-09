package ru.gb.web_market.api.dto;

import java.math.BigDecimal;

public class ProductDto {

    private Long id;

    private String article;

    private String title;

    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ProductDto() {
    }

    public ProductDto(Long id, String article, String title, BigDecimal price) {
        this.id = id;
        this.article = article;
        this.title = title;
        this.price = price;
    }
}

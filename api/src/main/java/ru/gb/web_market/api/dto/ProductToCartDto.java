package ru.gb.web_market.api.dto;

import java.math.BigDecimal;

public class ProductToCartDto {

    private Long id;

    private String article;

    private String title;

    private BigDecimal price;

    private Integer count;

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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public ProductToCartDto() {
    }

    public ProductToCartDto(Long id, String article, String title, BigDecimal price, Integer count) {
        this.id = id;
        this.article = article;
        this.title = title;
        this.price = price;
        this.count = count;
    }
}

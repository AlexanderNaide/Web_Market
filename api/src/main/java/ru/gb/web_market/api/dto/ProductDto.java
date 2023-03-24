package ru.gb.web_market.api.dto;

import java.math.BigDecimal;

public class ProductDto {

    private Long id;

    private String article;

    private String title;

    private BigDecimal price;

    private ProductDto(Builder builder) {
        setId(builder.id);
        setArticle(builder.article);
        setTitle(builder.title);
        setPrice(builder.price);
    }

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


    public static final class Builder {
        private Long id;
        private String article;
        private String title;
        private BigDecimal price;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setArticle(String article) {
            this.article = article;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public ProductDto build() {
            return new ProductDto(this);
        }
    }
}

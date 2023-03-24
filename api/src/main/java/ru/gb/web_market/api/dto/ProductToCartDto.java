package ru.gb.web_market.api.dto;

import java.math.BigDecimal;

public class ProductToCartDto {

    private Long id;

    private String article;

    private String title;

    private BigDecimal price;

    private Integer count;

    private ProductToCartDto(Builder builder) {
        setId(builder.id);
        setArticle(builder.article);
        setTitle(builder.title);
        setPrice(builder.price);
        setCount(builder.count);
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


    public static final class Builder {
        private Long id;
        private String article;
        private String title;
        private BigDecimal price;
        private Integer count;

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

        public Builder setCount(Integer count) {
            this.count = count;
            return this;
        }

        public ProductToCartDto build() {
            return new ProductToCartDto(this);
        }
    }
}

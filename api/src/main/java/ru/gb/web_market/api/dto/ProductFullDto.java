package ru.gb.web_market.api.dto;

import java.math.BigDecimal;

public class ProductFullDto {

    private Long id;

    private String subCategory;

    private String article;

    private String title;

    private BigDecimal price;

    private String manufacturer;

    private String description;

    private ProductFullDto(Builder builder) {
        setId(builder.id);
        setSubCategory(builder.subCategory);
        setArticle(builder.article);
        setTitle(builder.title);
        setPrice(builder.price);
        setManufacturer(builder.manufacturer);
        setDescription(builder.description);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
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

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductFullDto() {
    }

    public ProductFullDto(Long id, String subCategory, String article, String title, BigDecimal price, String manufacturer, String description) {
        this.id = id;
        this.subCategory = subCategory;
        this.article = article;
        this.title = title;
        this.price = price;
        this.manufacturer = manufacturer;
        this.description = description;
    }


    public static final class Builder {
        private Long id;
        private String subCategory;
        private String article;
        private String title;
        private BigDecimal price;
        private String manufacturer;
        private String description;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setSubCategory(String subCategory) {
            this.subCategory = subCategory;
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

        public Builder setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public ProductFullDto build() {
            return new ProductFullDto(this);
        }
    }
}

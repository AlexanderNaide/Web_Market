package ru.gb.web_market.api.dto;

public class ProductFullDto {

    private Long id;

    private String subCategory;

    private String article;

    private String title;

    private Double price;

    private String manufacturer;

    private String description;

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
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

    public ProductFullDto(Long id, String subCategory, String article, String title, Double price, String manufacturer, String description) {
        this.id = id;
        this.subCategory = subCategory;
        this.article = article;
        this.title = title;
        this.price = price;
        this.manufacturer = manufacturer;
        this.description = description;
    }
}

package ru.gb.web_market.products.entities;

import jakarta.persistence.*;
import jdk.jfr.Unsigned;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Unsigned
    private Long id;

    @Column(name = "article", nullable = false)
    private String article;

    @Column(name = "modification")
    private String modification;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @Unsigned
    private Category category;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id", nullable = false)
    @Unsigned
    private Manufacturer manufacturer;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "purchase_price")
    private BigDecimal purchasePrice;

    @Column(name = "old_price")
    private BigDecimal oldPrice;

    @Column(name = "count")
    private Integer count;

    @Column(name = "description", length = 1200)
    private String description;

    @Column(name = "path")
    private String path;

    @Column(name = "images_title", length = 2000)
    private String imagesTitle;

    @Column(name = "images_linc", length = 2000)
    private String imagesLinc;

    @Column(name = "specifications", length = 3000)
    private String specifications;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}

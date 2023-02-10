package ru.gb.web_market.core.entities;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jdk.jfr.Unsigned;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "order_items")
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Unsigned
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @Unsigned
    private Order order;

    @OneToOne
    @Nonnull
    @Unsigned
    private Product product;

    @Column(name = "count", nullable = false)
    private int count;

    @Column(name = "purchase_price", nullable = false)
    private Double price;

    public OrderItem(Order order, Product product, int count) {
        this.order = order;
        this.product = product;
        this.count = count;
        this.price = product.getPrice() * count;
    }
}

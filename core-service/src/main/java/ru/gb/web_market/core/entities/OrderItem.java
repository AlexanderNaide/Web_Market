package ru.gb.web_market.core.entities;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jdk.jfr.Unsigned;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gb.web_market.api.dto.ProductDto;
import ru.gb.web_market.api.dto.ProductFullDto;
import ru.gb.web_market.api.dto.ProductToCartDto;

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

    @Nonnull
    @Unsigned
    private Long productId;

    @Column(name = "count", nullable = false)
    private int count;

    @Column(name = "purchase_price", nullable = false)
    private Double price;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    public OrderItem(Order order, ProductToCartDto productDto) {
        this.order = order;
        this.productId = productDto.getId();
        this.count = productDto.getCount();
        this.price = productDto.getPrice();
        this.totalPrice = price * count;
    }
}

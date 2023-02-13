package ru.gb.web_market.core.entities;

import jakarta.persistence.*;
import jdk.jfr.Unsigned;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Unsigned
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Unsigned
    private User user;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "status")
    private OrderStatusEnums status;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "history")
    private String history;

    @Column(name = "address")
    private String address;

    @Column(name = "info")
    private String info;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> productList;
}
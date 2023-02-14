package ru.gb.web_market.core.entities;

import jakarta.persistence.*;
import jdk.jfr.Unsigned;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

import static java.time.format.DateTimeFormatter.ofLocalizedDateTime;

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

    @Column(name = "username")
    private String username;

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


    public void setStatus(OrderStatusEnums status) {
        DateTimeFormatter formatter = ofLocalizedDateTime(FormatStyle.SHORT);
        LocalDateTime current = LocalDateTime.now();
        if (history == null){
            history = status.getStatus() + " - " + formatter.format(current);
        } else {
            history = history + "<->" + status.getStatus() + " - " + formatter.format(current);
        }
        this.status = status;
    }
}

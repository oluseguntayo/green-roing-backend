package com.kabadiwala.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"order", "item"})
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relation with Order
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // Relation with Item (master table)
    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    // Dynamic user input
    private Double quantity;   // can be weight or count
    private String unit;       // "kg" or "pieces" depending on item.isCountable
    private Double price;
}

package com.kabadiwala.entity;

import com.kabadiwala.enums.ItemUnit;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;          // e.g. Iron, Plastic, Paper

    @Column(nullable = true)
    private String imageUrl;      // item image

    @Column(nullable = false)
    private boolean isCountable;  // true = countable (pieces), false = weight (kg)
    @Column(nullable = false)
    private Double pricePerUnit;  // price per kg or per piece

    @Column(nullable = false)
    private ItemUnit unit;
}

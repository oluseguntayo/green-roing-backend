package com.kabadiwala.dto;

import lombok.Data;

@Data
public class OrderItemDto {
    private Long id;
    private ItemDto item;
    private Double quantity;   // can be weight or count
    private String unit;       // "kg" or "pieces" depending on item.isCountable
    private Double price;
}
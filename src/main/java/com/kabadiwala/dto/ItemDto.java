package com.kabadiwala.dto;

import lombok.Data;

@Data
public class ItemDto {
    private Long id;
    private String name;
    private String imageUrl;
    private boolean isCountable;
    private Double pricePerUnit;
    private com.kabadiwala.enums.ItemUnit unit;
}
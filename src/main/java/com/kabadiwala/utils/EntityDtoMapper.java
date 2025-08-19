package com.kabadiwala.utils;

import com.kabadiwala.dto.*;
import com.kabadiwala.entity.*;

import java.util.stream.Collectors;

public class EntityDtoMapper {

    public static UserDto toUserDto(User user) {
        if (user == null) return null;
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setAvatarUrl(user.getAvatarUrl());
        dto.setContactNumber(user.getContactNumber());
        dto.setRole(user.getRole());
        dto.setStatus(user.getStatus());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }

    public static User toUserEntity(UserDto dto) {
        if (dto == null) return null;
        User user = new User();
        user.setId(dto.getId());
        user.setFullName(dto.getFullName());
        user.setAvatarUrl(dto.getAvatarUrl());
        user.setContactNumber(dto.getContactNumber());
        user.setRole(dto.getRole());
        user.setStatus(dto.getStatus());
        return user;
    }

    public static ItemDto toItemDto(Item item) {
        if (item == null) return null;
        ItemDto dto = new ItemDto();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setImageUrl(item.getImageUrl());
        dto.setCountable(item.isCountable());
        dto.setPricePerUnit(item.getPricePerUnit());
        dto.setUnit(item.getUnit());
        return dto;
    }

    public static Item toItemEntity(ItemDto dto) {
        if (dto == null) return null;
        Item item = new Item();
        item.setId(dto.getId());
        item.setName(dto.getName());
        item.setImageUrl(dto.getImageUrl());
        item.setCountable(dto.isCountable());
        item.setPricePerUnit(dto.getPricePerUnit());
        item.setUnit(dto.getUnit());
        return item;
    }

    public static OrderItemDto toOrderItemDto(OrderItem entity) {
        if (entity == null) return null;
        OrderItemDto dto = new OrderItemDto();
        dto.setId(entity.getId());
        dto.setItem(toItemDto(entity.getItem()));
        dto.setQuantity(entity.getQuantity());
        dto.setUnit(entity.getUnit());
        dto.setPrice(entity.getPrice());
        return dto;
    }

    public static OrderItem toOrderItemEntity(OrderItemDto dto, Order order) {
        if (dto == null) return null;
        OrderItem entity = new OrderItem();
        entity.setId(dto.getId());
        entity.setItem(toItemEntity(dto.getItem()));
        entity.setQuantity(dto.getQuantity());
        entity.setUnit(dto.getUnit());
        entity.setPrice(dto.getPrice());
        entity.setOrder(order);
        return entity;
    }

    public static OrderDto toOrderDto(Order order) {
        if (order == null) return null;
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setUserId(order.getUser().getId());
        dto.setOrderItems(
                order.getOrderItems().stream()
                        .map(EntityDtoMapper::toOrderItemDto)
                        .collect(Collectors.toList())
        );
        return dto;
    }

    public static Order toOrderEntity(OrderDto dto, User user) {
        if (dto == null) return null;
        Order order = new Order();
        order.setId(dto.getId());
        order.setUser(user);
        order.setOrderItems(
                dto.getOrderItems().stream()
                        .map(oi -> toOrderItemEntity(oi, order))
                        .collect(Collectors.toList())
        );
        return order;
    }
}
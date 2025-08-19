package com.kabadiwala.service;

import com.kabadiwala.dto.*;
import com.kabadiwala.entity.*;
import com.kabadiwala.utils.EntityDtoMapper;
import com.kabadiwala.repository.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {
    private static final Logger logger = LoggerFactory.getLogger(ItemService.class);
    private final ItemRepository itemRepository;

    public ItemDto createItem(ItemDto dto) {
        Item item = EntityDtoMapper.toItemEntity(dto);
        return EntityDtoMapper.toItemDto(itemRepository.save(item));
    }

    public List<ItemDto> getAllItems() {
        return itemRepository.findAll().stream()
                .map(EntityDtoMapper::toItemDto)
                .collect(Collectors.toList());
    }

        public ItemDto getItemById(Long id) {
            Item item = itemRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Item not found"));
            return EntityDtoMapper.toItemDto(item);
        }

        public ItemDto updateItem(Long id, ItemDto dto) {
            Item item = itemRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Item not found"));
            item.setName(dto.getName());
            item.setImageUrl(dto.getImageUrl());
            item.setCountable(dto.isCountable());
            item.setPricePerUnit(dto.getPricePerUnit());
            item.setUnit(dto.getUnit());
            return EntityDtoMapper.toItemDto(itemRepository.save(item));
        }

        public void deleteItem(Long id) {
            if (!itemRepository.existsById(id)) {
                throw new RuntimeException("Item not found");
            }
            itemRepository.deleteById(id);
        }
}
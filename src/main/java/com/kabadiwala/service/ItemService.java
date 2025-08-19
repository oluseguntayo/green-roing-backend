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
}
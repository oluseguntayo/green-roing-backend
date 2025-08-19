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
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderDto createOrder(OrderDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Order order = EntityDtoMapper.toOrderEntity(dto, user);
        return EntityDtoMapper.toOrderDto(orderRepository.save(order));
    }

    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(EntityDtoMapper::toOrderDto)
                .collect(Collectors.toList());
    }
}
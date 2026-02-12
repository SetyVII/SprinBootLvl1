package com.example.grupo5.dto;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDTO(
        int orderId,
        int customerId,
        LocalDateTime orderDate,
        List<OrderItemDTO> items,
        double totalAmount,
        Integer shipmentId
) {}

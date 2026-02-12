package com.example.grupo5.dto;

public record OrderItemDTO(
        int productId,
        String productName,
        int quantity,
        double unitPrice
) {}

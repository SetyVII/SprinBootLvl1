package com.example.grupo5.dto;

public record CartLineDTO(
        int productId,
        String productName,
        double unitPrice,
        int quantity,
        double lineTotal
) {}


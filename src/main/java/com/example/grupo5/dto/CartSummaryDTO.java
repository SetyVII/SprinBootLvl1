package com.example.grupo5.dto;

import java.util.List;

public record CartSummaryDTO(
        List<CartLineDTO> items,
        double totalAmount
) {}


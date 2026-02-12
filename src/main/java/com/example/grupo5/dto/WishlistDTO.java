package com.example.grupo5.dto;

public record WishlistDTO(
        int id,
        String name,
        boolean shared,
        int customerId
) {}

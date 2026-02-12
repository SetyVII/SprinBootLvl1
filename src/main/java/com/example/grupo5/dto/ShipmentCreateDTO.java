package com.example.grupo5.dto;

public record ShipmentCreateDTO(
        String address,
        String city,
        String zipCode,
        String state,
        String country
) {}


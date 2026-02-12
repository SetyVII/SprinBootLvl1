package com.example.grupo5.dto;

import java.time.LocalDateTime;

public record ShipmentDTO(
        int shipmentId,
        int orderId,
        LocalDateTime shipmentDate,
        String address,
        String city,
        String zipCode,
        String state,
        String country
) {}

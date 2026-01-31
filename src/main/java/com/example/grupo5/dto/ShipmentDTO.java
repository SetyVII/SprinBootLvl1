package com.example.grupo5.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentDTO {
    private int id;
    private LocalDateTime date;
    private String zipCode;
    private String address;
    private String city;
    private String state;
    private String country;
    private int orderId;
}

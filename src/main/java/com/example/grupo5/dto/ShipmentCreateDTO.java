package com.example.grupo5.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentCreateDTO {
    private String zipCode;
    private String address;
    private String city;
    private String state;
    private String country;
}


package com.example.grupo5.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartLineDTO {
    private int productId;
    private String productName;
    private double unitPrice;
    private int quantity;
    private double lineTotal;
}


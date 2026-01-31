package com.example.grupo5.dto.ProductDto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCreateDTO {
    private String name;
    private String sku;
    private String description;
    private double price;
    private int stock;
    private Set<Integer> categoryIds;
}
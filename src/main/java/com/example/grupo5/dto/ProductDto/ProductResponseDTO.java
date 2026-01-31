package com.example.grupo5.dto.ProductDto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDTO {
    private int id;
    private String name;
    private String sku;
    private int stock;
    private double price;
    private String description;
    private Set<CategorySummaryDto> categories;
}

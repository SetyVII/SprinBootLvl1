package com.example.grupo5.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer id;

    // TODO: better use unique, ask first
    @Column(nullable = false,length=100)
    private String name;

    @Column(nullable = false, unique = true,length=15)
    private String sku;

    // here possible could have a problem
    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private Double price;

    @Column(length=1000)
    String description;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    // why we use here the name?
    @JoinTable(
            name = "products_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

}

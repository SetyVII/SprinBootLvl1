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
    private int id;

    // TODO: better use unique, ask first
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String sku;

    // here possible could have a problem
    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private double price;

    String description;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    // why we use here the name?
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

}

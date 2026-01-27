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
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int id;

    // TODO: better limit the size ? implement
    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToMany(mappedBy = "categories")
    private Set<Product> products = new HashSet<>();

}

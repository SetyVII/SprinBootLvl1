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
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer id;

    @Column(nullable = false, name = "first_name",length = 50)
    private String firstName;

    @Column(nullable = false, name = "last_name",length = 50)
    private String lastName;

    @Column(nullable = false,length = 30)
    private String email;

    @Column(nullable = false,length = 30)
    private String password;

    @Column(nullable = false,length = 100)
    private String address;

    // We use String cause +34 or prefix
    @Column(name = "phone_number",length = 20)
    private String phoneNumber;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Wishlist> wishlists = new HashSet<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Order> orders = new HashSet<>();
}

package com.example.grupo5.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="shipment_id")
    private int id;

    @Column(name = "shipment_date", nullable = false)
    // TODO: dont solve much is better call this later
    private LocalDateTime date = LocalDateTime.now();

    @Column(nullable = false, name = "zip_code")
    private String zipCode;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String country;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = true)
    private Order order;

}

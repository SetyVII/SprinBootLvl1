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
@Table(name = "shipments")
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipment_id")
    private int id;

    @Column(name = "shipment_date", nullable = false)
    private LocalDateTime date = LocalDateTime.now();

    @Column(nullable = false, name = "zip_code")
    private String zipCode;

    @Column(nullable = false, name = "address")
    private String address;

    @Column(nullable = false, name = "city")
    private String city;

    @Column(nullable = false, name = "state")
    private String state;

    @Column(nullable = false, name = "country")
    private String country;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

}

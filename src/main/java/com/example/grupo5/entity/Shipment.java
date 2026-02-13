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
    private Integer id;

    @Column(name = "shipment_date", nullable = false)
    private LocalDateTime date = LocalDateTime.now();

    @Column(nullable = false, name = "zip_code",length = 10)
    private String zipCode;

    @Column(nullable = false, name = "address",length = 250)
    private String address;

    @Column(nullable = false, name = "city",length = 50)
    private String city;

    @Column(nullable = false, name = "state",length = 50)
    private String state;

    @Column(nullable = false, name = "country",length = 50)
    private String country;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

}

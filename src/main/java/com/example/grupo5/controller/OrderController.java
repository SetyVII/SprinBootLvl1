package com.example.grupo5.controller;

import com.example.grupo5.dto.OrderDTO;
import com.example.grupo5.dto.ShipmentCreateDTO;
import com.example.grupo5.dto.ShipmentDTO;
import com.example.grupo5.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create/{customerId}")
    public ResponseEntity<OrderDTO> createFromCart(@PathVariable int customerId) {
        OrderDTO created = orderService.createFromCart(customerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/send/{orderId}")
    public ResponseEntity<ShipmentDTO> sendOrder(@PathVariable int orderId,
                                                 @RequestBody ShipmentCreateDTO dto) {
        ShipmentDTO shipment = orderService.createShipment(orderId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(shipment);
    }
}


package com.example.grupo5.controller;

import com.example.grupo5.dto.CartAddItemDTO;
import com.example.grupo5.dto.CartSummaryDTO;
import com.example.grupo5.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CartSummaryDTO> getCart(@PathVariable int customerId) {
        return ResponseEntity.ok(cartService.listCart(customerId));
    }

    @PostMapping("/{customerId}")
    public ResponseEntity<CartSummaryDTO> addProduct(@PathVariable int customerId,
                                                     @RequestBody CartAddItemDTO dto) {
        return ResponseEntity.ok(cartService.addProduct(customerId, dto));
    }

    @DeleteMapping("/{customerId}/{productId}")
    public ResponseEntity<CartSummaryDTO> removeProduct(@PathVariable int customerId,
                                                        @PathVariable int productId) {
        return ResponseEntity.ok(cartService.removeProduct(customerId, productId));
    }

    @PostMapping("/empty/{customerId}")
    public ResponseEntity<CartSummaryDTO> emptyCart(@PathVariable int customerId) {
        return ResponseEntity.ok(cartService.emptyCart(customerId));
    }
}


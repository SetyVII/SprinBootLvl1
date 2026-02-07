package com.example.grupo5.controller;

import com.example.grupo5.dto.WishlistCreateDTO;
import com.example.grupo5.dto.WishlistDTO;
import com.example.grupo5.dto.ProductDto.ProductResponseDTO;
import com.example.grupo5.service.WishlistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlists")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping("/list/{customerId}")
    public ResponseEntity<List<WishlistDTO>> listByCustomer(@PathVariable int customerId) {
        return ResponseEntity.ok(wishlistService.listByCustomerId(customerId));
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<WishlistDTO> create(@PathVariable int customerId,
                                              @RequestBody WishlistCreateDTO dto) {
        WishlistDTO created = wishlistService.create(customerId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{wishlistId}")
    public ResponseEntity<Void> delete(@PathVariable int wishlistId) {
        wishlistService.delete(wishlistId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{wishlistId}")
    public ResponseEntity<List<ProductResponseDTO>> listProducts(@PathVariable int wishlistId) {
        return ResponseEntity.ok(wishlistService.listProducts(wishlistId));
    }
}


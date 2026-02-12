package com.example.grupo5.controller;

import com.example.grupo5.dto.ProductDto.ProductCreateDTO;
import com.example.grupo5.dto.ProductDto.ProductResponseDTO;
import com.example.grupo5.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Create a new product
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductCreateDTO productDTO) {
        ProductResponseDTO createdProduct = productService.save(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    // Get all products
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    // Get product by id
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable int id) {
        ProductResponseDTO product = productService.findById(id);
        return ResponseEntity.ok(product);
    }

    // Update a product
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable int id, @RequestBody ProductCreateDTO productDTO) {
        ProductResponseDTO updatedProduct = productService.update(id, productDTO);
        return ResponseEntity.ok(updatedProduct);
    }

    // Delete a product
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Only for testing
    // New Methods for pages
    @GetMapping("/{page}/{pageSize}")
    public List<ProductResponseDTO> listPaged(@PathVariable int page, @PathVariable int pageSize) {
        return productService.listPaged(page, pageSize);
    }

    // Check the responses
    @GetMapping("/search/{query}/{page}/{pageSize}")
    public List<ProductResponseDTO> searchPaged(@PathVariable String query, @PathVariable int page, @PathVariable int pageSize) {
        return productService.searchPaged(query, page, pageSize);
    }


}

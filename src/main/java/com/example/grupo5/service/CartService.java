package com.example.grupo5.service;

import com.example.grupo5.Exceptions.BadRequestException;
import com.example.grupo5.Exceptions.NotFoundException;
import com.example.grupo5.dto.CartAddItemDTO;
import com.example.grupo5.dto.CartLineDTO;
import com.example.grupo5.dto.CartSummaryDTO;
import com.example.grupo5.entity.CartItem;
import com.example.grupo5.entity.Customer;
import com.example.grupo5.entity.Product;
import com.example.grupo5.repository.CartItemRepository;
import com.example.grupo5.repository.CustomerRepository;
import com.example.grupo5.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public CartService(CartItemRepository cartItemRepository,
                       CustomerRepository customerRepository,
                       ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public CartSummaryDTO listCart(int customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new NotFoundException("Customer not found with id: " + customerId);
        }

        List<CartItem> items = cartItemRepository.findByCustomerIdOrderByProductNameAsc(customerId);
        List<CartLineDTO> lines = items.stream()
                .map(item -> new CartLineDTO(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getPrice(),
                        item.getQuantity(),
                        item.getPrice() * item.getQuantity()
                ))
                .toList();

        double total = lines.stream().mapToDouble(CartLineDTO::lineTotal).sum();
        return new CartSummaryDTO(lines, total);
    }

    @Transactional
    public CartSummaryDTO addProduct(int customerId, CartAddItemDTO dto) {
        if (dto.quantity() <= 0) {
            throw new BadRequestException("Quantity must be greater than zero.");
        }

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found with id: " + customerId));

        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + dto.productId()));

        CartItem item = cartItemRepository.findByCustomerIdAndProductId(customerId, product.getId())
                .orElseGet(() -> {
                    CartItem newItem = new CartItem();
                    newItem.setCustomer(customer);
                    newItem.setProduct(product);
                    newItem.setQuantity(0);
                    newItem.setPrice(product.getPrice());
                    return newItem;
                });

        item.setQuantity(item.getQuantity() + dto.quantity());
        item.setPrice(product.getPrice());
        cartItemRepository.save(item);

        return listCart(customerId);
    }

    @Transactional
    public CartSummaryDTO removeProduct(int customerId, int productId) {
        if (cartItemRepository.findByCustomerIdAndProductId(customerId, productId).isEmpty()) {
            throw new NotFoundException("Cart item not found for customer id: " + customerId + " and product id: " + productId);
        }

        cartItemRepository.deleteByCustomerIdAndProductId(customerId, productId);
        return listCart(customerId);
    }

    @Transactional
    public CartSummaryDTO emptyCart(int customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new NotFoundException("Customer not found with id: " + customerId);
        }

        cartItemRepository.deleteByCustomerId(customerId);
        return listCart(customerId);
    }
}


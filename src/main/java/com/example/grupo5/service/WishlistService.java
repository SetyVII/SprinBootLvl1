package com.example.grupo5.service;

import com.example.grupo5.Exceptions.BadRequestException;
import com.example.grupo5.Exceptions.NotFoundException;
import com.example.grupo5.dto.WishlistCreateDTO;
import com.example.grupo5.dto.WishlistDTO;
import com.example.grupo5.dto.ProductDto.ProductResponseDTO;
import com.example.grupo5.entity.Customer;
import com.example.grupo5.entity.Product;
import com.example.grupo5.entity.Wishlist;
import com.example.grupo5.repository.CustomerRepository;
import com.example.grupo5.repository.WishlistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final CustomerRepository customerRepository;
    private final ProductService productService;

    public WishlistService(WishlistRepository wishlistRepository,
                           CustomerRepository customerRepository,
                           ProductService productService) {
        this.wishlistRepository = wishlistRepository;
        this.customerRepository = customerRepository;
        this.productService = productService;
    }

    @Transactional(readOnly = true)
    public List<WishlistDTO> listByCustomerId(int customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new NotFoundException("Customer not found with id: " + customerId);
        }
        return wishlistRepository.findByCustomerId(customerId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional
    public WishlistDTO create(int customerId, WishlistCreateDTO dto) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found with id: " + customerId));

        Wishlist wishlist = new Wishlist();
        wishlist.setName(dto.getName());
        wishlist.setShared(dto.isShared());
        wishlist.setCustomer(customer);

        return toDTO(wishlistRepository.save(wishlist));
    }

    @Transactional
    public void delete(int wishlistId) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new NotFoundException("Wishlist not found with id: " + wishlistId));

        if (!wishlist.getProducts().isEmpty()) {
            throw new BadRequestException("Wishlist must be empty before deletion.");
        }

        wishlistRepository.delete(wishlist);
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> listProducts(int wishlistId) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new NotFoundException("Wishlist not found with id: " + wishlistId));

        return wishlist.getProducts().stream()
                .sorted(Comparator.comparing(Product::getName))
                .map(productService::mappingDTO)
                .toList();
    }

    private WishlistDTO toDTO(Wishlist wishlist) {
        return new WishlistDTO(
                wishlist.getId(),
                wishlist.getName(),
                wishlist.isShared(),
                wishlist.getCustomer().getId()
        );
    }
}


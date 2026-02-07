package com.example.grupo5.repository;

import com.example.grupo5.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByCustomerIdOrderByProductNameAsc(int customerId);

    void deleteByCustomerId(int customerId);

    Optional<CartItem> findByCustomerIdAndProductId(int customerId, int productId);

    void deleteByCustomerIdAndProductId(int customerId, int productId);
}

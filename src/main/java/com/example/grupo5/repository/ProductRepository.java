package com.example.grupo5.repository;

import com.example.grupo5.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    // List page, ordered by name
    Page<Product> findAllByOrderByNameAsc(Pageable pageable);

    // Search by name or description, page + order by name
    // MEMO: I hate this query consult not good code
    Page<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrderByNameAsc(
            String nameQuery,
            String descriptionQuery,
            Pageable pageable
    );
}

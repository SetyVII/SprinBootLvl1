package com.example.grupo5.repository;

import com.example.grupo5.dto.CategoryDTO;
import com.example.grupo5.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}

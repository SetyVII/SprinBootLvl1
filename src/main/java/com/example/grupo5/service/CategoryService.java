package com.example.grupo5.service;

import com.example.grupo5.dto.CategoryDTO;
import com.example.grupo5.entity.Category;
import com.example.grupo5.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Create
    public CategoryDTO save(CategoryDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());

        Category saved = categoryRepository.save(category);
        return new CategoryDTO(saved.getId(), saved.getName(), saved.getDescription());
    }

    // Read
    public List<CategoryDTO> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(cat -> new CategoryDTO(cat.getId(), cat.getName(), cat.getDescription())).toList();
    }

    public CategoryDTO findById(int id) {
        return categoryRepository.findById(id)
                .map(cat -> new CategoryDTO(cat.getId(), cat.getName(), cat.getDescription()))
                .orElseThrow();
    }

    // Update
    public CategoryDTO update(int id, CategoryDTO dto) {
        Category category = categoryRepository.findById(id).orElseThrow();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());

        Category updated = categoryRepository.save(category);
        return new CategoryDTO(updated.getId(), updated.getName(), updated.getDescription());
    }
    // Delete
    public CategoryDTO delete(int id) {
        Category category = categoryRepository.findById(id).orElseThrow();
        CategoryDTO categoryDTO = new CategoryDTO(category.getId(), category.getName(), category.getDescription());
        categoryRepository.deleteById(id);
        return categoryDTO;
    }
}

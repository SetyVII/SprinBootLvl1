package com.example.grupo5.service;

import com.example.grupo5.dto.ProductDto.CategorySummaryDto;
import com.example.grupo5.dto.ProductDto.ProductCreateDTO;
import com.example.grupo5.dto.ProductDto.ProductResponseDTO;
import com.example.grupo5.entity.Category;
import com.example.grupo5.entity.Product;
import com.example.grupo5.repository.CategoryRepository;
import com.example.grupo5.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class ProductService {


    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    // Create
    public ProductResponseDTO save(ProductCreateDTO product) {

         Product newProduct = new Product();
        saving(product, newProduct);

        Product saved = productRepository.save(newProduct);
        return mappingDTO(saved);
    }


    // Read

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> findAll() {
        List<Product> p = productRepository.findAll();
        return p.stream().map(this::mappingDTO).collect(toList());
    }

    @Transactional(readOnly = true)
    public ProductResponseDTO findById(int id) {
        // TODO: Add exception handling
        Product product = productRepository.findById(id).orElseThrow();
        return mappingDTO(product);
    }
    // TODO: Add findByCategory


    // Update
    public ProductResponseDTO update(int id,ProductCreateDTO product) {
        // TODO: Need resolve this for more clean code
        Product changeProduct = productRepository.findById(id).orElseThrow();

        saving(product, changeProduct);

        return mappingDTO(productRepository.save(changeProduct));

    }

    // Delete
    @Transactional
    public ProductResponseDTO delete(int id) {
        ProductResponseDTO product = findById(id);
        productRepository.deleteById(id);
        // This will be a message
        return product;
    }

    // List paginated
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> listPaged(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Product> result = productRepository.findAllByOrderByNameAsc(pageable);
        return result.getContent().stream()
                .map(this::mappingDTO)   // Product -> ProductResponseDTO (with category DTOs)
                .toList();
    }

    // Search paginated
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> searchPaged(String query, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Product> result =
                productRepository
                        .findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrderByNameAsc(
                                query, query, pageable
                        );
        return result.getContent().stream()
                .map(this::mappingDTO)
                .toList();
    }


    // Mapping
    public ProductResponseDTO mappingDTO (Product product) {

        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .sku(product.getSku())
                .stock(product.getStock())
                .price(product.getPrice())
                .description(product.getDescription())
                .categories(
                        product.getCategories().stream()
                                .map(c -> new CategorySummaryDto(c.getName(), c.getDescription()))
                                .collect(Collectors.toSet())
                )
                .build();
    }
    private void saving(ProductCreateDTO product, Product changeProduct) {
        changeProduct.setName(product.getName());
        changeProduct.setSku(product.getSku());
        changeProduct.setStock(product.getStock());
        changeProduct.setPrice(product.getPrice());
        changeProduct.setDescription(product.getDescription());
        Set<Category> categories =
                new HashSet<>(categoryRepository.findAllById(product.getCategoryIds()));
        changeProduct.setCategories(categories);
    }
}

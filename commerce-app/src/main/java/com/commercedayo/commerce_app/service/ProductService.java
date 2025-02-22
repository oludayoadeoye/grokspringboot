package com.commercedayo.commerce_app.service;

import com.commercedayo.models.Product;
import com.commercedayo.repo.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Product createProduct(String name, String description, BigDecimal price,
            String category, String imageUrl, Double rating) {
        Product product = new Product(
                null,
                name,
                description,
                price,
                category,
                imageUrl,
                rating,
                LocalDateTime.now(),
                LocalDateTime.now());
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public Optional<Product> updateProduct(Long id, String name, String description,
            BigDecimal price, String category,
            String imageUrl, Double rating) {
        return productRepository.findById(id)
                .map(existing -> {
                    Product updated = new Product(
                            id,
                            name != null ? name : existing.name(),
                            description != null ? description : existing.description(),
                            price != null ? price : existing.price(),
                            category != null ? category : existing.category(),
                            imageUrl != null ? imageUrl : existing.imageUrl(),
                            rating != null ? rating : existing.rating(),
                            existing.createdAt(),
                            LocalDateTime.now());
                    return productRepository.save(updated);
                });
    }

    @Transactional
    public boolean deleteProduct(Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    productRepository.delete(product);
                    return true;
                })
                .orElse(false);
    }

    public List<Product> searchProducts(String category, String name, BigDecimal minPrice, BigDecimal maxPrice) {
        if (category != null) {
            return productRepository.findByCategory(category);
        }
        if (name != null) {
            return productRepository.findByNameContainingIgnoreCase(name);
        }
        if (minPrice != null && maxPrice != null) {
            return productRepository.findByPriceBetween(minPrice, maxPrice);
        }
        return getAllProducts();
    }
}
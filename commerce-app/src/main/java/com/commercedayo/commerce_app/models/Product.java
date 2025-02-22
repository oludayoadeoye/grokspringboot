package com.commercedayo.commerce_app.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public record Product(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id,

        @Column(nullable = false) String name,

        @Column(length = 1000) String description,

        @Column(nullable = false) BigDecimal price,

        @Column String category,

        @Column String imageUrl,

        @Column Double rating,

        @Column LocalDateTime createdAt,

        @Column LocalDateTime updatedAt) {
    public Product {
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (rating != null && (rating < 0 || rating > 5)) {
            throw new IllegalArgumentException("Rating must be between 0 and 5");
        }
    }
}
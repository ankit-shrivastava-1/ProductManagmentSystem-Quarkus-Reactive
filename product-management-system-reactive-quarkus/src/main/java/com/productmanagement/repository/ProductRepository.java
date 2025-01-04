package com.productmanagement.repository;

import com.productmanagement.entity.Product;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product> {
    // Custom repository methods (if needed)
}


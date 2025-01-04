package com.productmanagement.service;

import java.util.List;

import com.productmanagement.entity.Product;

import io.smallrye.mutiny.Uni;

/**
 * Reactive service interface for managing Product entities.
 */
public interface ProductService {

	Uni<List<Product>> getAllProducts();

	Uni<Product> getProductById(Long id);

	Uni<Product> saveProduct(Product product);

	Uni<Product> updateProduct(Long id, Product product);

	Uni<Void> deleteProduct(Long id);

	Uni<Boolean> checkProductStockAvailable(Long id, int count);
}

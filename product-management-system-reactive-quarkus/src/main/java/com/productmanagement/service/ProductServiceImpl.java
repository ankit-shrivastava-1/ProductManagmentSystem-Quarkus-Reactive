package com.productmanagement.service;

import java.util.List;

import com.productmanagement.entity.Product;
import com.productmanagement.exception.ProductNotFoundException;
import com.productmanagement.repository.ProductRepository;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	@Inject
	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public Uni<List<Product>> getAllProducts() {
		return productRepository.listAll();
	}

	@Override
	public Uni<Product> getProductById(Long id) {
		return productRepository.findById(id)
				.onItem()
				.ifNull()
				.failWith(() -> new ProductNotFoundException("The product doesn't exist"));
	}

	@Override
	public Uni<Product> saveProduct(Product product) {
		return productRepository.persist(product);
	}

	@Override
	public Uni<Product> updateProduct(Long id, Product product) {
		return productRepository.findById(id)
				.onItem()
				.ifNull()
				.failWith(() -> new ProductNotFoundException("The product doesn't exist")).onItem()
				.transform(entity -> {
					entity.setName(product.getName());
					entity.setPrice(product.getPrice());
					entity.setQuantity(product.getQuantity());
					return entity;
				});
	}

	@Override
	public Uni<Void> deleteProduct(Long id) {
		return productRepository.findById(id)
				.onItem()
				.ifNull()
				.failWith(() -> new ProductNotFoundException("The product doesn't exist")).onItem()
				.transformToUni(entity -> productRepository.delete(entity));
	}

	@Override
	public Uni<Boolean> checkProductStockAvailable(Long id, int count) {
		return productRepository.findById(id)
				.onItem()
				.ifNull()
				.failWith(() -> new ProductNotFoundException("The product doesn't exist")).onItem()
				.transform(product -> product.getQuantity() >= count);
	}
}

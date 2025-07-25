package com.visto.application.service;

import com.visto.domain.exception.ResourceNotFoundException;
import com.visto.domain.model.Product;
import com.visto.domain.validator.ProductValidator;
import com.visto.infrastructure.persistence.ProductRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements ProductServiceInterface {

    private final ProductRepository productRepository;
    private final ProductValidator productValidator;

    public ProductService(ProductRepository productRepository, ProductValidator productValidator) {
        this.productRepository = productRepository;
        this.productValidator = productValidator;
    }

    @Override
    public Product createProduct(Product product) {
        productValidator.validate(product);
        return productRepository.save(product);
    }

    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> getProductsByName(String name, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com id: " + id));
    }

    @Override
    public Product updateProduct(Long id, Product updatedData) {
        productValidator.validate(updatedData);
        Product existing = getProductById(id);

        existing.update(updatedData.getName(), updatedData.getPrice(), updatedData.getDescription(), updatedData.getQuantity());

        return productRepository.save(existing);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }
}
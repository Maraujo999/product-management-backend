package com.visto.application.service;

import com.visto.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductServiceInterface {
    Product createProduct(Product product);

    Page<Product> getAllProducts(Pageable pageable);

    Page<Product> getProductsByName(String name, Pageable pageable);

    Product getProductById(Long id);

    Product updateProduct(Long id, Product updatedData);

    void deleteProduct(Long id);
}
package com.visto.domain.port;

import com.visto.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductRepositoryPort {
    Product save(Product product);

    Optional<Product> findById(Long id);

    Page<Product> findAll(Pageable pageable);

    Page<Product> findByNameContaining(String name, Pageable pageable);

    void delete(Product product);
}
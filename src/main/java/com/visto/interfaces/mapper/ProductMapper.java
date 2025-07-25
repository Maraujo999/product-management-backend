package com.visto.interfaces.mapper;

import com.visto.interfaces.dto.ProductRequestDTO;
import com.visto.interfaces.dto.ProductResponseDTO;
import com.visto.domain.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper implements ProductMapperInterface {

    @Override
    public Product toEntity(ProductRequestDTO dto) {
        return Product.of(dto.name(), dto.price(), dto.description(), dto.quantity());
    }

    @Override
    public ProductResponseDTO toDto(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getQuantity()
        );
    }
}
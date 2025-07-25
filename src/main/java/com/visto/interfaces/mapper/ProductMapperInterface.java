package com.visto.interfaces.mapper;

import com.visto.domain.model.Product;
import com.visto.interfaces.dto.ProductRequestDTO;
import com.visto.interfaces.dto.ProductResponseDTO;

public interface ProductMapperInterface {
    Product toEntity(ProductRequestDTO dto);
    ProductResponseDTO toDto(Product entity);
}

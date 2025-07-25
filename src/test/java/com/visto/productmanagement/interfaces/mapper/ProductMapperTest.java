package com.visto.productmanagement.interfaces.mapper;

import com.visto.domain.model.Product;
import com.visto.interfaces.dto.ProductRequestDTO;
import com.visto.interfaces.dto.ProductResponseDTO;
import com.visto.interfaces.mapper.ProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    private ProductMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ProductMapper();
    }

    @Test
    void shouldConvertProductRequestDtoToEntity() {
        ProductRequestDTO dto = new ProductRequestDTO("Mouse Gamer", 299.99, "Mouse com sensor óptico", 15);

        Product product = mapper.toEntity(dto);

        assertNotNull(product);
        assertEquals("Mouse Gamer", product.getName());
        assertEquals(299.99, product.getPrice());
        assertEquals("Mouse com sensor óptico", product.getDescription());
        assertEquals(15, product.getQuantity());
    }

    @Test
    void shouldConvertProductEntityToResponseDto() {
        Product product = Product.of("Teclado Mecânico", 399.90, "Teclado com switch blue", 10);

        try {
            var idField = Product.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(product, 99L);
        } catch (Exception e) {
            fail("Erro ao configurar ID para teste: " + e.getMessage());
        }

        ProductResponseDTO dto = mapper.toDto(product);

        assertNotNull(dto);
        assertEquals(99L, dto.id());
        assertEquals("Teclado Mecânico", dto.name());
        assertEquals(399.90, dto.price());
        assertEquals("Teclado com switch blue", dto.description());
        assertEquals(10, dto.quantity());
    }
}

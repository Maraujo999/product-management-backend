package com.visto.productmanagement.interfaces.controller;

import com.visto.application.service.ProductService;
import com.visto.domain.model.Product;
import com.visto.interfaces.controller.ProductController;
import com.visto.interfaces.dto.ProductRequestDTO;
import com.visto.interfaces.dto.ProductResponseDTO;
import com.visto.interfaces.mapper.ProductMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;


@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    @MockBean
    private ProductMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateProduct() throws Exception {
        ProductRequestDTO dto = new ProductRequestDTO("Teclado", 120.0, "Mecânico", 10);
        Product entity = Product.of("Teclado", 120.0, "Mecânico", 10);
        Product saved = Product.of("Teclado", 120.0, "Mecânico", 10);
        ProductResponseDTO responseDTO = new ProductResponseDTO(1L, "Teclado", 120.0, "Mecânico", 10);

        Mockito.when(mapper.toEntity(dto)).thenReturn(entity);
        Mockito.when(service.createProduct(entity)).thenReturn(saved);
        Mockito.when(mapper.toDto(saved)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value("Teclado"))
                .andExpect(jsonPath("$.message").value("Produto criado com sucesso"))
                .andExpect(jsonPath("$.status").value(201));
    }

    @Test
    void shouldReturnPaginatedList() throws Exception {
        Product product = Product.of("Teclado", 120.0, "Mecânico", 10);
        ProductResponseDTO dto = new ProductResponseDTO(1L, "Teclado", 120.0, "Mecânico", 10);
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
        Page<Product> page = new PageImpl<>(List.of(product));

        Mockito.when(service.getAllProducts(any(Pageable.class))).thenReturn(page);
        Mockito.when(mapper.toDto(any())).thenReturn(dto);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].name").value("Teclado"))
                .andExpect(jsonPath("$.message").value("Lista carregada com sucesso"))
                .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    void shouldReturnProductById() throws Exception {
        Product product = Product.of("Teclado", 120.0, "Mecânico", 10);
        ProductResponseDTO dto = new ProductResponseDTO(1L, "Teclado", 120.0, "Mecânico", 10);

        Mockito.when(service.getProductById(1L)).thenReturn(product);
        Mockito.when(mapper.toDto(product)).thenReturn(dto);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Teclado"))
                .andExpect(jsonPath("$.message").value("Produto encontrado"))
                .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    void shouldUpdateProduct() throws Exception {
        ProductRequestDTO dto = new ProductRequestDTO("Teclado", 130.0, "Mecânico RGB", 15);
        Product entity = Product.of("Teclado", 130.0, "Mecânico RGB", 15);
        Product updated = Product.of("Teclado", 130.0, "Mecânico RGB", 15);
        ProductResponseDTO responseDTO = new ProductResponseDTO(1L, "Teclado", 130.0, "Mecânico RGB", 15);

        Mockito.when(mapper.toEntity(dto)).thenReturn(entity);
        Mockito.when(service.updateProduct(eq(1L), eq(entity))).thenReturn(updated);
        Mockito.when(mapper.toDto(updated)).thenReturn(responseDTO);

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Teclado"))
                .andExpect(jsonPath("$.message").value("Produto atualizado com sucesso"))
                .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.message").value("Produto excluído com sucesso"))
                .andExpect(jsonPath("$.status").value(204));
    }
}

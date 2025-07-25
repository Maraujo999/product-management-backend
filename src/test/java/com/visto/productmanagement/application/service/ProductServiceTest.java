package com.visto.productmanagement.application.service;

import com.visto.application.service.ProductService;
import com.visto.domain.exception.ResourceNotFoundException;
import com.visto.domain.model.Product;
import com.visto.domain.validator.ProductValidator;
import com.visto.infrastructure.persistence.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private ProductValidator validator;

    @InjectMocks
    private ProductService service;

    private AutoCloseable closeable;

    private final Product product = Product.of("Notebook", 2000.0, "Bom", 10);

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateProductSuccessfully() {
        when(repository.save(product)).thenReturn(product);

        Product created = service.createProduct(product);

        verify(validator).validate(product);
        verify(repository).save(product);
        assertEquals(product, created);
    }

    @Test
    void shouldListAllProducts() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> expectedPage = new PageImpl<>(List.of(product));

        when(repository.findAll(pageable)).thenReturn(expectedPage);

        Page<Product> result = service.getAllProducts(pageable);

        assertEquals(1, result.getContent().size());
        assertEquals(product, result.getContent().get(0));
    }

    @Test
    void shouldFilterProductsByName() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> expectedPage = new PageImpl<>(List.of(product));

        when(repository.findByNameContainingIgnoreCase("Note", pageable)).thenReturn(expectedPage);

        Page<Product> result = service.getProductsByName("Note", pageable);

        assertEquals(1, result.getContent().size());
        assertEquals(product, result.getContent().get(0));
    }

    @Test
    void shouldReturnProductById() {
        when(repository.findById(1L)).thenReturn(Optional.of(product));

        Product found = service.getProductById(1L);

        assertEquals(product, found);
    }

    @Test
    void shouldThrowWhenProductNotFoundById() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> service.getProductById(99L));

        assertEquals("Produto não encontrado com id: 99", ex.getMessage());
    }

    @Test
    void shouldUpdateProductSuccessfully() {
        Product updated = Product.of("Novo", 3000.0, "Desc novo", 5);

        when(repository.findById(1L)).thenReturn(Optional.of(product));
        when(repository.save(product)).thenReturn(product);

        Product result = service.updateProduct(1L, updated);

        assertEquals("Novo", result.getName());
        assertEquals(3000.0, result.getPrice());
        assertEquals("Desc novo", result.getDescription());
        assertEquals(5, result.getQuantity());

        verify(validator).validate(updated);
        verify(repository).save(product);
    }

    @Test
    void shouldDeleteProductSuccessfully() {
        when(repository.findById(1L)).thenReturn(Optional.of(product));

        service.deleteProduct(1L);

        verify(repository).delete(product);
    }

    @Test
    void shouldThrowWhenTryingToDeleteNonexistentProduct() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.deleteProduct(1L));
    }
}

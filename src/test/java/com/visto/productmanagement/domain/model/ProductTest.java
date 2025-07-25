package com.visto.productmanagement.domain.model;

import com.visto.domain.exception.BusinessRuleException;
import com.visto.domain.model.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void shouldCreateProductWithValidData() {
        Product product = Product.of("Teclado", 199.90, "Teclado mecânico", 5);

        assertEquals("Teclado", product.getName());
        assertEquals(199.90, product.getPrice());
        assertEquals("Teclado mecânico", product.getDescription());
        assertEquals(5, product.getQuantity());
    }

    @Test
    void shouldThrowWhenNameIsNull() {
        BusinessRuleException exception = assertThrows(BusinessRuleException.class,
                () -> Product.of(null, 199.90, "Produto", 1));
        assertEquals("Nome do produto é obrigatório.", exception.getMessage());
    }

    @Test
    void shouldThrowWhenNameIsBlank() {
        BusinessRuleException exception = assertThrows(BusinessRuleException.class,
                () -> Product.of("   ", 199.90, "Produto", 1));
        assertEquals("Nome do produto é obrigatório.", exception.getMessage());
    }

    @Test
    void shouldThrowWhenPriceIsNegative() {
        BusinessRuleException exception = assertThrows(BusinessRuleException.class,
                () -> Product.of("Teclado", -10.0, "Produto", 1));
        assertEquals("Preço deve ser positivo.", exception.getMessage());
    }

    @Test
    void shouldThrowWhenQuantityIsNegative() {
        BusinessRuleException exception = assertThrows(BusinessRuleException.class,
                () -> Product.of("Teclado", 199.90, "Produto", -1));
        assertEquals("Quantidade deve ser maior ou igual a zero.", exception.getMessage());
    }

    @Test
    void shouldThrowWhenPriceAndQuantityAreZero() {
        BusinessRuleException exception = assertThrows(BusinessRuleException.class,
                () -> Product.of("Teclado", 0.0, "Produto", 0));
        assertEquals("Preço e quantidade não podem ser ambos zero.", exception.getMessage());
    }

    @Test
    void shouldUpdateProductWithValidData() {
        Product product = Product.of("Teclado", 100.0, "Desc", 1);

        product.update("Mouse", 150.0, "Mouse ótico", 10);

        assertEquals("Mouse", product.getName());
        assertEquals(150.0, product.getPrice());
        assertEquals("Mouse ótico", product.getDescription());
        assertEquals(10, product.getQuantity());
    }

    @Test
    void shouldThrowWhenUpdatingWithBlankName() {
        Product product = Product.of("Teclado", 100.0, "desc", 1);

        BusinessRuleException exception = assertThrows(BusinessRuleException.class,
                () -> product.update(" ", 120.0, "teste", 2));

        assertEquals("Nome não pode estar em branco.", exception.getMessage());
    }
}

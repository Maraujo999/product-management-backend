package com.visto.productmanagement.domain.validator;

import com.visto.domain.model.Product;
import com.visto.domain.validator.ProductValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class ProductValidatorTest {

    private ProductValidator validator;

    @BeforeEach
    void setUp() {
        validator = new ProductValidator();
    }

    @Test
    void shouldPassValidationWhenProductIsValid() {
        Product product = Product.of("Notebook", 3500.0, "Produto bom", 10);
        assertDoesNotThrow(() -> validator.validate(product));
    }

    @Test
    void shouldFailWhenNameIsNull() throws Exception {
        Product product = Product.of("Valid", 100.0, "desc", 1);

        Field nameField = Product.class.getDeclaredField("name");
        nameField.setAccessible(true);
        nameField.set(product, null);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> validator.validate(product));
        assertEquals("Nome do produto é obrigatório", ex.getMessage());
    }

    @Test
    void shouldFailWhenNameIsBlank() throws Exception {
        Product product = Product.of("Valid", 100.0, "desc", 1);

        Field nameField = Product.class.getDeclaredField("name");
        nameField.setAccessible(true);
        nameField.set(product, "   ");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> validator.validate(product));
        assertEquals("Nome do produto é obrigatório", ex.getMessage());
    }

    @Test
    void shouldFailWhenPriceAndQuantityAreZero() throws Exception {
        Product product = Product.of("Valid", 100.0, "desc", 1);

        Field priceField = Product.class.getDeclaredField("price");
        priceField.setAccessible(true);
        priceField.set(product, 0.0);

        Field quantityField = Product.class.getDeclaredField("quantity");
        quantityField.setAccessible(true);
        quantityField.set(product, 0);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> validator.validate(product));
        assertEquals("Preço e quantidade não podem ser zero simultaneamente", ex.getMessage());
    }

    @Test
    void shouldFailWhenPriceIsNegative() throws Exception {
        Product product = Product.of("Valid", 100.0, "desc", 1);

        Field priceField = Product.class.getDeclaredField("price");
        priceField.setAccessible(true);
        priceField.set(product, -10.0);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> validator.validate(product));
        assertEquals("Preço deve ser maior ou igual a zero", ex.getMessage());
    }

    @Test
    void shouldFailWhenQuantityIsNegative() throws Exception {
        Product product = Product.of("Valid", 100.0, "desc", 1);

        Field quantityField = Product.class.getDeclaredField("quantity");
        quantityField.setAccessible(true);
        quantityField.set(product, -1);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> validator.validate(product));
        assertEquals("Quantidade deve ser maior ou igual a zero", ex.getMessage());
    }
}

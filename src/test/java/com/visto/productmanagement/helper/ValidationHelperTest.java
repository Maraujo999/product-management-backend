package com.visto.productmanagement.helper;

import com.visto.helper.ValidationHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationHelperTest {

    @Test
    void shouldReturnValueWhenNotNull() {
        String result = ValidationHelper.requireNonNull("test", "Valor não pode ser nulo");
        assertEquals("test", result);
    }

    @Test
    void shouldThrowWhenValueIsNull() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> ValidationHelper.requireNonNull(null, "Valor não pode ser nulo")
        );
        assertEquals("Valor não pode ser nulo", ex.getMessage());
    }

    @Test
    void shouldNotThrowWhenConditionIsTrue() {
        assertDoesNotThrow(() ->
                ValidationHelper.require(1 + 1 == 2, () -> "Não deveria lançar exceção")
        );
    }

    @Test
    void shouldThrowWhenConditionIsFalse() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> ValidationHelper.require(false, () -> "Condição inválida")
        );
        assertEquals("Condição inválida", ex.getMessage());
    }
}

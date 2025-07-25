package com.visto.interfaces.dto;

import jakarta.validation.constraints.*;

public record ProductRequestDTO(

        @NotBlank(message = "Nome é obrigatório") String name,

        @NotNull(message = "Preço é obrigatório") @Positive(message = "Preço deve ser maior que zero") Double price,

        @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres") String description,

        @NotNull(message = "Quantidade é obrigatória") @Min(value = 0, message = "Quantidade não pode ser negativa") Integer quantity

) {
}

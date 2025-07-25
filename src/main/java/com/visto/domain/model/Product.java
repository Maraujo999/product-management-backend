package com.visto.domain.model;

import com.visto.domain.exception.BusinessRuleException;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "products")
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Integer quantity;

    protected Product() {
    }

    private Product(String name, Double price, String description, Integer quantity) {
        if (name == null || name.isBlank()) {
            throw new BusinessRuleException("Nome do produto é obrigatório.");
        }
        if (price == null || price < 0) {
            throw new BusinessRuleException("Preço deve ser positivo.");
        }
        if (quantity == null || quantity < 0) {
            throw new BusinessRuleException("Quantidade deve ser maior ou igual a zero.");
        }
        if (price == 0 && quantity == 0) {
            throw new BusinessRuleException("Preço e quantidade não podem ser ambos zero.");
        }

        this.name = name.trim();
        this.price = price;
        this.description = (description != null) ? description.trim() : "";
        this.quantity = quantity;
    }

    public static Product of(String name, Double price, String description, Integer quantity) {
        return new Product(name, price, description, quantity);
    }

    public void update(String name, Double price, String description, Integer quantity) {
        if (name == null || name.isBlank()) {
            throw new BusinessRuleException("Nome não pode estar em branco.");
        }
        if (price == null || price < 0) {
            throw new BusinessRuleException("Preço deve ser positivo.");
        }
        if (quantity == null || quantity < 0) {
            throw new BusinessRuleException("Quantidade deve ser maior ou igual a zero.");
        }

        this.name = name.trim();
        this.price = price;
        this.description = (description != null) ? description.trim() : "";
        this.quantity = quantity;
    }
}

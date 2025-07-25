package com.visto.domain.validator;

import com.visto.domain.model.Product;
import com.visto.helper.ValidationHelper;
import org.springframework.stereotype.Component;

@Component
public class ProductValidator {

    public void validate(Product product) {
        ValidationHelper.require(product.getPrice() != 0 || product.getQuantity() != 0, () -> "Preço e quantidade não podem ser zero simultaneamente");

        ValidationHelper.require(product.getName() != null && !product.getName().isBlank(), () -> "Nome do produto é obrigatório");

        ValidationHelper.require(product.getPrice() == null || product.getPrice() >= 0, () -> "Preço deve ser maior ou igual a zero");

        ValidationHelper.require(product.getQuantity() == null || product.getQuantity() >= 0, () -> "Quantidade deve ser maior ou igual a zero");
    }
}
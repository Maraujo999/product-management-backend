package com.visto.interfaces.controller;

import com.visto.interfaces.dto.ProductRequestDTO;
import com.visto.interfaces.dto.ProductResponseDTO;
import com.visto.interfaces.mapper.ProductMapper;
import com.visto.interfaces.response.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import com.visto.domain.model.Product;
import com.visto.application.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Produtos", description = "Operações de gerenciamento de produtos")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostMapping
    @Operation(summary = "Cria um novo produto", description = "Cria um novo produto com os dados fornecidos no corpo da requisição.")
    @ApiResponses({@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Produto criado com sucesso"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Dados inválidos"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Erro interno no servidor")})
    public ResponseEntity<ApiResponse<ProductResponseDTO>> create(@Valid @RequestBody ProductRequestDTO dto) {
        Product created = productService.createProduct(productMapper.toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(productMapper.toDto(created), "Produto criado com sucesso", 201));
    }

    @GetMapping
    @Operation(summary = "Lista produtos com paginação", description = "Retorna uma lista paginada e ordenada de produtos. É possível filtrar por nome.")
    @ApiResponses({@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Erro interno no servidor")})
    public ResponseEntity<ApiResponse<Page<ProductResponseDTO>>> listAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id,asc") String[] sort, @RequestParam(required = false) String name) {

        Sort.Direction direction = Sort.Direction.fromString(sort[1]);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort[0]));

        Page<Product> result = (name != null && !name.isEmpty()) ? productService.getProductsByName(name, pageable) : productService.getAllProducts(pageable);

        Page<ProductResponseDTO> responsePage = result.map(productMapper::toDto);
        return ResponseEntity.ok(new ApiResponse<>(responsePage, "Lista carregada com sucesso", 200));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca produto por ID", description = "Retorna os dados de um produto específico pelo ID.")
    @ApiResponses({@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Produto encontrado"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Produto não encontrado"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Erro interno no servidor")})
    public ResponseEntity<ApiResponse<ProductResponseDTO>> findById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(new ApiResponse<>(productMapper.toDto(product), "Produto encontrado", 200));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza produto existente", description = "Atualiza os dados de um produto existente com base no ID informado.")
    @ApiResponses({@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Dados inválidos"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Produto não encontrado"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Erro interno no servidor")})
    public ResponseEntity<ApiResponse<ProductResponseDTO>> update(@PathVariable Long id, @Valid @RequestBody ProductRequestDTO dto) {
        Product updated = productService.updateProduct(id, productMapper.toEntity(dto));
        return ResponseEntity.ok(new ApiResponse<>(productMapper.toDto(updated), "Produto atualizado com sucesso", 200));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui produto", description = "Remove permanentemente o produto com o ID fornecido.")
    @io.swagger.v3.oas.annotations.responses.ApiResponses({@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Produto excluído com sucesso"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Produto não encontrado"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Erro interno no servidor")})
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
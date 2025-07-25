package com.visto.infrastructure.config;

import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.*;
import org.springdoc.core.models.GroupedOpenApi;

@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi productApi() {
        return GroupedOpenApi.builder().group("produtos").packagesToScan("com.visto.interfaces.controller").pathsToMatch("/api/products/**").build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info().title("API de Produtos").version("v1.0.0").description("Documentação da API para gerenciamento de produtos"));
    }
}

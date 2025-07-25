# 🚀 Sistema de Gerenciamento de Produtos

Este projeto consiste em uma API RESTful desenvolvida com **Java 21 + Spring Boot 3**, seguindo os princípios de **Clean Architecture**, **SOLID** e **Clean Code**, integrada com banco de dados em memória H2, migrações com Flyway e documentada com Swagger/OpenAPI. A aplicação também conta com testes unitários usando **JUnit 5** e **Mockito**, e está preparada para execução via **Docker e Docker Compose**.

---

## 🌐 Tecnologias e Ferramentas

* Java 21 + Spring Boot 3.3
* Spring Web, JPA, H2 Database
* Flyway (migrações versionadas)
* OpenAPI 3 (springdoc-openapi)
* JUnit 5 + Mockito
* Docker & Docker Compose
* Swagger UI

---

## 📚 Arquitetura e Padrões

O projeto segue **Clean Architecture**, com separação de responsabilidades clara:

```
src/main/java/com/visto
├── interfaces        # Camada de entrada (controllers, DTOs, mappers)
├── application       # Camada de serviço (orquestração e regras de negócio)
├── domain            # Camada de domínio puro (entidades, exceções, validadores)
├── infrastructure     # Infraestrutura (configurações, persistência)
```

Padrões aplicados:

* **SOLID**: especialmente SRP e DIP
* **DTO Pattern**
* **Mapper Pattern**
* **Repository Pattern (JPA)**
* **ControllerAdvice** para tratamento global de exceções
* **Validações com Bean Validation e regras de negócio separadas**

---

## 🔧 Como executar localmente

### Requisitos

* Java 21
* Maven 3.9+
* Docker + Docker Compose (opcional)

### Executar com Maven

```bash
# Backend
cd backend
mvn clean spring-boot:run
```

Acesse:

* Swagger: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
* H2 Console: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

### Executar com Docker Compose

```bash
docker-compose down -v && docker-compose up --build
```

---

## 📆 Endpoints REST

Todos os endpoints estão documentados via Swagger em:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

Exemplos:

| Método | Endpoint           | Descrição                   |
| ------ | ------------------ | --------------------------- |
| GET    | /api/products      | Listar produtos paginados   |
| GET    | /api/products/{id} | Buscar produto por ID       |
| POST   | /api/products      | Criar novo produto          |
| PUT    | /api/products/{id} | Atualizar produto existente |
| DELETE | /api/products/{id} | Excluir produto             |

---

## 🔹 Banco de Dados e Flyway

* Banco: H2 (memória)
* Script de criação: `src/main/resources/db/migration/V1__create_table.sql`
* Script de insert: `V2__insert_products.sql`

---

## 🔮 Testes

Testes com cobertura de serviço utilizando:

* **JUnit 5**
* **Mockito** para simulação de dependências

Execute com:

```bash
mvn test
```

Exemplo de classe testada:

* `ProductServiceTest`

---

## 💡 Extras implementados

* ✅ Clean Architecture + SOLID
* ✅ Docker e Docker Compose
* ✅ Swagger OpenAPI 3
* ✅ Bean Validation no DTO
* ✅ ControllerAdvice com mensagens de erro sem stacktrace exposta
* ✅ Scripts SQL com Flyway (versão controlada)
* ✅ Separadores de camadas respeitando clean code
* ✅ CORS configurado para Angular localhost:4200
* ✅ Utilitários reutilizáveis (ValidationHelper)

---

## 📂 Estrutura Docker

```bash
# Backend
Dockerfile
EXPOSE 8080

# Docker Compose
version: '3.8'
services:
  backend:
    build: ./backend
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=docker
```

---

## 💭 Considerações Finais

Este projeto demonstra não apenas o cumprimento dos requisitos funcionais, mas também o comprometimento com **qualidade técnica, arquitetura limpa e escalabilidade**. As decisões tomadas priorizam manutenção, clareza de responsabilidades e extensibilidade.

Caso deseje executar o frontend (Angular), ele pode ser conectado diretamente via `localhost:4200` com suporte CORS ativado.

---

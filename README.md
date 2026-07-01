# Open Library API

API REST para gerenciamento de livros, desenvolvida com Spring Boot.

## Tecnologias

- Java 17
- Spring Boot 3.5
- Spring Web
- Spring Data JPA
- H2 (banco em memória)
- JUnit 5 e Mockito

## Como rodar

**Pré-requisitos:** Java 17+ e Maven

**Subir a aplicação:**

    mvn spring-boot:run

A API fica disponível em: http://localhost:8080

**Rodar os testes:**

    mvn test

## Decisões técnicas

Escolhi Spring Boot porque é o framework mais utilizado para criar APIs REST em Java e já traz configuração pronta de servidor, injeção de dependência e integração com banco de dados.

Usei o H2 em memória por ser simples e não exigir instalação de banco. É ideal para desenvolvimento e testes. Os dados são perdidos quando a aplicação é reiniciada.

O Spring Data JPA facilita o acesso ao banco através do BookRepository, sem precisar escrever SQL manual para o CRUD.

Organizei o projeto em camadas (Controller, Service e Repository) para separar responsabilidades: a controller recebe as requisições, o service contém a lógica e o repository acessa o banco.

Para validação, o service verifica se o livro existe antes de atualizar ou deletar. Se o id não for encontrado, é lançada a exceção BookNotFoundException e o GlobalExceptionHandler retorna uma resposta padronizada com status 404 e mensagem de erro em JSON.

Para os testes, criei o BookControllerTest com MockMvc e @SpringBootTest para testar as rotas da API de ponta a ponta (POST, GET, PUT e DELETE), incluindo o cenário de livro não encontrado. No BookServiceTest usei JUnit 5 e Mockito para testar a lógica do service de forma isolada, sem depender do banco real.
## Endpoints
Base URL: http://localhost:8080/api/v1/books

### Criar livro

POST /api/v1/books

Body:

    {
      "title": "Entendendo Algoritmos",
      "author": "Aditya Y. Bhargava",
      "publicationYear": 2017
    }

Resposta (200):

    {
      "id": 1,
      "title": "Entendendo Algoritmos",
      "author": "Aditya Y. Bhargava",
      "publicationYear": 2017
    }

### Listar livros

GET /api/v1/books

Resposta (200):

    [
      {
        "id": 1,
        "title": "Entendendo Algoritmos",
        "author": "Aditya Y. Bhargava",
        "publicationYear": 2017
      }
    ]

### Atualizar livro

PUT /api/v1/books/{id}

Body:

    {
      "title": "Entendendo Algoritmos",
      "author": "Aditya Y. Bhargava",
      "publicationYear": 2018
    }

Resposta (200):

    {
      "id": 1,
      "title": "Entendendo Algoritmos",
      "author": "Aditya Y. Bhargava",
      "publicationYear": 2018
    }

Se o id não existir (404):

    {
      "status": 404,
      "message": "Book not found"
    }

### Deletar livro

DELETE /api/v1/books/{id}

Resposta (200):

    {
      "message": "Book successfully deleted!"
    }

Se o id não existir (404):

    {
      "status": 404,
      "message": "Book not found"
    }

## Banco de dados

O H2 roda em memória. Configuração em src/main/resources/application.properties.

Console H2 (opcional): http://localhost:8080/h2-console

- JDBC URL: jdbc:h2:mem:testdb
- Usuário: sa
- Senha: (vazio)

## Estrutura do projeto

    controller/   → rotas da API
    service/      → regras de negócio
    repository/   → acesso ao banco
    model/        → entidade Book
    dto/          → respostas da API
    exception/    → tratamento de erros

## Testes

- BookControllerTest — testes das rotas com MockMvc
- BookServiceTest — testes unitários do service com Mockito

## Autor

Mayara Caroline

- E-mail: mayaracapereira45@gmail.com

# SGHSS - Sistema de Gestão Hospitalar Simples

Projeto desenvolvido como atividade acadêmica com fins didáticos.

Este projeto é um sistema básico de gestão hospitalar desenvolvido em Java com Spring Boot, seguindo a arquitetura RESTful. Ele permite o gerenciamento de pacientes, profissionais de saúde, consultas, prontuários e receitas.

## 📋 Pré-requisitos

Antes de rodar o projeto, você precisa ter instalado:

- [Java 17+](https://www.oracle.com/java/technologies/javase-downloads.html)
- [MySQL](https://dev.mysql.com/downloads/)
- [Gradle](https://gradle.org/install/)
- [Git](https://git-scm.com/)

> 💡 Certifique-se de que o Lombok está habilitado na sua IDE.

## Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Spring Data JPA
- Spring Security
- Gradle
- MySQL
- Hibernate
- Lombok
- Jakarta Validation
- Postman (para testes)
- Git e GitHub

## Funcionalidades

- Autenticação de usuários (login com email e senha)
- CRUD completo para Pacientes e Profissionais
- Associação entre Pacientes, Consultas, Prontuários e Receitas
- Tratamento básico de erros
- Requisições e respostas em formato JSON

## Como rodar o projeto

1. Clone o repositório:

   ```bash
   git clone https://github.com/Jailton-J/Sghss.git

2. Configure seu banco de dados MySQL no arquivo (não se esquela de criar um banco CREATE DATABASE sghss):
   ```bash
   spring.datasource.url=jdbc:mysql://localhost:3306/sghss
   spring.datasource.username=SEU_USUARIO
   spring.datasource.password=SUA_SENHA
    
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.format_sql=true
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect


4. Execute a aplicação:
    ```bash
   ./gradlew bootRun

6. A API estará disponível em:
  http://localhost:8080

## 🔐 Autenticação
A aplicação possui autenticação via Spring Security.

Endpoint para login:
POST /auth/login

Exemplo de corpo da requisição:
```
{
  "email": "usuario@example.com",
  "senha": "suaSenha"
}
```
Após login bem-sucedido, um token JWT será retornado e deve ser usado nas requisições protegidas com o header:
Authorization: Bearer <TOKEN>

## Endpoints principais
POST /auth/login – Login de usuário

GET /pacientes – Lista todos os pacientes

POST /pacientes – Cadastra um paciente

PUT /pacientes/{id} – Atualiza um paciente

DELETE /pacientes/{id} – Remove um paciente

GET /profissionais – Lista todos os profissionais

POST /profissionais – Cadastra um profissional

(Outros endpoints disponíveis para consultas, receitas, prontuários, etc.)

## Autor
Jailton J.


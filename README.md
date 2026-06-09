![Banner](/VeloSpace/docs/LogoFinal.jpeg)

![Java 21](https://img.shields.io/badge/Java%2021-007396?style=for-the-badge&logo=openjdk&logoColor=white) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white) ![ActiveMQ](https://img.shields.io/badge/ActiveMQ-ED2B2B?style=for-the-badge&logo=apacheactivemq&logoColor=white) ![Oracle](https://img.shields.io/badge/Oracle%20Cloud-F80000?style=for-the-badge&logo=oracle&logoColor=white) [![FIAP](https://img.shields.io/badge/FIAP-ED145B?style=for-the-badge&logoColor=white)]() ![Azure](https://img.shields.io/badge/Azure%20Cloud-0078D4?style=for-the-badge&logo=microsoftazure&logoColor=white)

O VeloSpace é uma plataforma de gerenciamento de cargas espaciais que conecta embarcadores, provedores de lançamento e operadores de payload. Seu objetivo é centralizar e automatizar o fluxo de envio de cargas ao espaço, desde o cadastro e triagem dos payloads até a aprovação, rastreamento e lançamento.

A aplicação permite o cadastro de embarcadores e provedores de lançamento, gerenciamento de payloads com controle de status e prioridade, triagem técnica das cargas por operadores especializados e acompanhamento em tempo real do processo de aprovação e rastreamento.

> Este repositório contém os arquivos da API do VeloSpace, desenvolvida com Spring Boot.

---

[Video Pitch](#vídeo-pitch) | [Demonstração da Solução](#demonstração-da-solução) | [Deploy](#deploy) | [Endpoints](#endpoints) | [Setup do Projeto](#setup-do-projeto) | [Requisições de Teste](#requisições-de-teste) | [Stack Tecnológica](#stack-tecnológica) | [Desenvolvedores](#desenvolvedores)

---

## Vídeo Pitch

**Assista no YouTube:** [https://youtu.be/ukyb5AFKqiA](https://youtu.be/ukyb5AFKqiA)

## Demonstração da Solução

**Assista no YouTube:** [https://youtu.be/Y3aOr6lk_QI](https://youtu.be/Y3aOr6lk_QI)

## Deploy

A API está disponível publicamente em:  

**Endpoint da API:** [https://velospace-rm559914.azurewebsites.net/](https://velospace-rm559914.azurewebsites.net/)  

### Como se autenticar

Para acessar as rotas protegidas da API (todas as rotas exceto **POST /api/v1/users**, **/api/v1/auth** e **/swagger**), é necessário realizar login primeiro.  

Caso ainda não tenha conta, cadastre-se usando:
```
POST /users
Content-Type: application/json

{
  "full_name": "nomedeusuario",
  "email": "usuario@example.com",
  "password": "suasenha"
}
```

Faça login no endpoint:
```
POST /auth
Content-Type: application/json

{
  "email": "usuario@example.com",
  "password": "suasenha"
}
```

**Resposta esperada**
```
{
  "token": "<jwt_token>",
  "refreshToken": "<jwt_refresh_token>"
}
```

Após receber o token, todas as requisições protegidas devem incluir o cabeçalho:  
```
Authorization: Bearer <jwt_token>
```

Exemplo de uso do token:
```
GET /users/me
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### Paginação, Ordenação e Filtros

Alguns endpoints GET permitem enviar parâmetros para controlar a quantidade de resultados, a ordem da listagem e filtros de busca. Esses valores são enviados como query params na URL.

Exemplo real:
```
GET /courses/search?page=0&size=10&orderBy=title&direction=asc&title=java
```

## Endpoints

Os endpoints foram definidos com base nas necessidades reais do app mobile, incluindo rotas de busca e endpoints detalhados para facilitar o consumo dos dados pelo cliente. A ausência de alguns endpoints CRUD ocorre porque esses recursos não são responsabilidade desta API: sua gestão será feita pela API de back office (.NET), utilizada exclusivamente pelos administradores do sistema.

A seguir estão listados os principais endpoints disponíveis na API Front Office (Este repositório).

### Autenticação

```
POST   /api/v1/auth         Faz login e retorna o token de acesso  
POST   /api/v1/auth/refresh Gera um novo token quando o atual expirar  
```

```bash
{
  "email": "string",
  "password": "string"
}
```

### Inspection
```
POST  /api/v1/inspections        Cria uma nova inspection  
GET   /api/v1/inspections{id}     Buscar inpection por ID
```

```bash
{
  "satellite_id": 9007199254740991,
  "measured_height": 1073741824,
  "measured_width": 1073741824,
  "measured_length": 1073741824,
  "measured_weight": 1073741824
}
```

### Launch Providers
```
POST   /api/v1/launch-providers                  Cria um novo Launch Provider 
GET    /api/v1/launch-providers                  Listar todos os Launch Providers
GET    /api/v1/launch-providers/{id}             Listar Launch Providers por ID
GET    /api/v1/launch-providers/{id}/operators   Listar Operadores do Launch Provider
GET    /api/v1/launch-providers/{id}/satelites   Listar Satélites do Launch Provider
GET    /api/v1/launch-providers/me               Buscar meu Launch Provider
PUT    /api/v1/launch-providers/{id}             Atualizar um Launch Provider por ID
PATCH  /api/v1/launch-providers/{id}/password    Atualizar a senha por ID
DELETE /api/v1/launch-providers/{id}             Deletar um Launch Provider por ID
```

```bash
{
  "cnpj": "string",
  "corporate_name": "string",
  "email": "string",
  "phone": "25",
  "password": "string"
}
```

### Operator
```
POST   /api/v1/operators                Cria um novo Operator
POST   /api/v1/operators{id}/approval   Aprovar ou rejeitar um Operator
POST   /api/v1/operators/{id}/reapply   Reaplicar um Operator
GET    /api/v1/operators/{id}           Buscar Operator por ID
GET    /api/v1/operators/me             Buscar meu Operator
PUT    /api/v1/operators/{id}           Atualizar um Operator por ID
PATCH  /api/v1/operators/password       Atualizar senha do Operator
DELETE /api/v1/operators/{id}           Deletar um Operator por ID
``` 

```bash
POST   /api/v1/operators
{
  "launch_provider_id": 9007199254740991,
  "cpf": "string",
  "name": "string",
  "email": "string",
  "phone": "51761",
  "password": "string"
}
```

```bash
POST   /api/v1/operators{id}/approval
{
  "approval": true
}
```

### Satellite Priority
```
GET    /api/v1/setellite-priorities    Listar todas as prioridades do satelite
``` 

### Shipper
```
POST   /api/v1/shippers                      Cria um novo Shipper
GET    /api/v1/shippers/{id}                 Buscar Shipper por ID
GET    /api/v1/shippers/{id}/satellites      Listar todos os satelites do Shipper
GET    /api/v1/shippers/me                   Buscar Shipper do usuário
PUT    /api/v1/shippers{id}                  Atualizar um Shipper por ID
PATCH  /api/v1/shippers/password             Atualizar senha do Shipper
DELETE /api/v1/shippers/{id}                 Deletar um Shipper por ID
``` 

```bash
{
  "type": "PF",
  "shipper_document": "",
  "name": "string",
  "email": "string",
  "phone": "57871028279394",
  "password": "string"
}
```

## Setup do Projeto

### Instalação Local

Antes de iniciar, certifique-se de ter instalado:

- **Git**
- **Java 21**
- **Maven (mvn)**
- **Spring Boot 3.5**
- **Spring Data JPA**
- **Oracle DB**
- **Springdoc OpenAPI** 
- **Spring Security**
- **Docker** 
- **ActiveMQ**

#### 1. Clonar Repositório
```bash
# Clonar o repositório
git clone https://github.com/devpedrosena1/Global-Solution-VeloSpace-Fiap

# Acessar o diretório
cd VeloSpace

# Instalar as dependências
mvn compile
```

#### 2. Configurar o Ambiente

Crie um arquivo .env na raiz do projeto com o seguinte conteúdo (substitua pelas suas próprias credenciais e configurações):

```bash
DB_URL=<jdbc_url_do_banco>
DB_USERNAME=<seu_usuario_do_banco>
DB_PASSWORD=<sua_senha_do_banco>

JWT_SECRET=<sua_chave_secreta>
```
**Observação:** JWT_SECRET é a chave usada para assinar e validar os tokens JWT. Ela deve ser longa e secreta, pois garante a segurança da autenticação.

#### 3. Iniciar o projeto

```bash
mvn spring-boot:run
```

Após a inicialização, a API estará disponível em: http://localhost:8080  
A documentação interativa (Swagger UI) pode ser acessada em: http://localhost:8080/swagger-ui/index.html

## Desenvolvedores


[@Cleytonrik99](https://github.com/Cleytonrik99) - Desenvolvedor Backend

[@EnzoAzevedo](https://github.com/enzoazevedo) - Desenvolvedor Backend

[@MatheusHenriqueNF](https://github.com/MatheusHenriqueNF) - Desenvolvedor Mobile

[@PauloSérgioFB](https://github.com/paulgramador) - Desenvolvedor Backend e Mobile

[@devpedrosena1](https://github.com/devpedrosena1) - Desenvolvedor Backend e DevOps
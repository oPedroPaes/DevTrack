# DevTrack - Plataforma de Gestão de Estudos para Desenvolvedores

## Índice
- [O que é](#o-que-é)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Como Rodar Localmente](#como-rodar-localmente)
- [Rotas da API](#rotas-da-api)
- [Objetos de Transferência de Dados (DTOs)](#objetos-de-transferência-de-dados-dtos---data-transfer-objects)
- [Serviços](#serviços)
- [Modelos de Entidades](#modelos-de-entidades)
- [Roadmap de Desenvolvimento](#roadmap-de-desenvolvimento)
- [Considerações Finais](#considerações-finais)

## O que é

DevTrack é uma plataforma backend para registro, análise e visualização de dados de estudo, voltada para desenvolvedores.  
A aplicação permite que o usuário registre metas e sessões de estudo, acompanhe seu progresso através de gráficos e métricas, e visualize evolução semanal e mensal.  
Futuras versões poderão incluir integração com GitHub e gamificação do progresso.


## Tecnologias Utilizadas

- **Backend:** Spring Boot
- **Banco de Dados:** PostgreSQL
- **Autenticação:** JWT e BCrypt
- **Controle de versão:** Git e GitHub


## Pré-Requisitos

- Java 21 (LTS)
- Maven 3.9.12 (via wrapper)
- PostgreSQL 18
- Git

## Como Rodar Localmente

### Primeiro Passo: Clonar o repositório
```bash
git clone https://github.com/oPedroPaes/DevTrack.git
cd DevTrack
```

### Segundo Passo: Criar o Banco de Dados
No PostgreSQL:
```SQL
CREATE DATABASE devtrack;
```

### Terceiro Passo: Configurar o application.properties
Edite o arquivo:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/devtrack
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
spring.jpa.hibernate.ddl-auto=update
```
Ajuste username e password conforme seu ambiente local.

### Quarto Passo: Executar a aplicação
Abra o terminal na pasta raiz
```bash
cd (pasta raiz)
mvnw spring-boot:run
```

Por padrão a aplicação será iniciada em:
```bash
http://localhost:8080
```

### Quinto Passo: Testar endpoints
Ferramentas recomendadas:
* Postman
* Insomnia
* Curl

Exemplo de registro de usuário:
Método: POST
URL: http://localhost:8080/auth/register
Body:
```json
{
    "name": "João",
    "email": "joaodev@email.com",
    "password": "teste424"
}
```

Resposta esperada (200 OK):
```json
  User registered
```

## Rotas da API
### Rotas de Autenticação
|Rota           |   Método  |   Descrição       |   Body                                                                |   Resposta positiva                       |
|:-------------:|:---------:|:-----------------:|:---------------------------------------------------------------------:|:-----------------------------------------:|
|/auth/register |   POST    |   Criar conta     |   ```json { "name": "...", "email": "...", "password": "..."} ```    |   200 + ```json "User registered" ```      |
|/auth/login    |   POST    |   logar na conta  |   ```json { "email": "...", "password": "..." } ```                  |   200 OK + ```json { "token": "..." } ```  |

### Rotas de Usuário
|   Rota                |   Método  |   Descrição                                   |   Body                |   Resposta positiva                                                                               |
|:---------------------:|:---------:|:---------------------------------------------:|:---------------------:|:-------------------------------------------------------------------------------------------------:|
|   /users/me           |   GET     |   Lê dados do usuário atual                   |   ``` none ```        |   200 OK + ```json { "id": "...", "name": "...", "email": "..."} ```  |
|   /users/me/goals     |   GET     |   Retorna todas as metas do usuário atual     |   ``` none ```        |   200 OK + ```json [ { "id": "...", "title": "...", "description": "...", "targetDate": "yyyy-mm-dd", "status": "..." } ] ```  |
|   /users/me/sessions  |   GET     |   Retorna todas as sessões do usuário atual   |   ``` none ```        |   200 OK + ```json [ {...}, {...}  ]  ``` |
|   /users/me           |   PUT     |   Atualiza dados do usuário atual             |   ```json { "name": "Biel" } ```  |   200 OK + ```json { "id": "...", "name": "...", "email": "..." }  ```    |
|   /users/me           |   DELETE  |   Deleta o usuário atual                      |   ``` none ```        |   204 No Content  |


### Rotas de metas
|   Rota        |   Método  |   Descrição                                               |   Body                                                                    |   Resposta positiva   |
|:-------------:|:---------:|:---------------------------------------------------------:|:-------------------------------------------------------------------------:|:---------------------:|
|   /goals      |   POST    |   Cria uma meta                                           |   ```json { "title": "...", "description": "...", "targetDate": "yyyy-mm-dd" } ```    |   201 Created + ```json { "id": "...", "title": "...", "description": "...", "targetDate": "yyyy-mm-dd", "status": "..."} ```  |
|   /goals      |   GET     |   Retorna uma lista com todas as metas do usuário atual   |   ``` none ```                                                                    |   200 OK + ```json [ {...}, {...}] ```    |
|   /goals/{id} |   GET     |   Retorna uma meta específica do usuário atual            |   ``` none ```                                                                   |   200 OK + ```json { "id": "...", "title": "...", "description": "...", "targetDate": "yyyy-mm-dd", "status": "..." } ``` |
|   /goals/{id} |   PUT     |   Atualiza dados de uma meta específica do usuário atual  |  ```json { "title": "...", "description": "..." } ```                                 |   200 OK + ```json { "id": "...", "title": "...",     "description": "...", "targetDate": "yyyy-mm-dd", "status": "..." } ``` |
|   /goals/{id} |   DELETE  |   Deleta uma meta específica do usuário atual             |   ``` none ```                                                                    |   204 No Content  |

### Rotas de sessões de estudo
|   Rota                    |   Método  |      Descrição                                            |   Body                                    |   Resposta positiva           |
|:-------------------------:|:---------:|:---------------------------------------------------------:|:-----------------------------------------:|:-----------------------------:|
|   /sessions               |  POST     |  Cria uma sessão                                          |   ```json { "subject": "...", "goalId": "..." } ``` |   201 Created + ```json { "id": "...", "date": "yyyy-mm-dd", "durationInMinutes": "...", "subject": "...", "goalId": "..."} ```  |
|   /sessions               |   GET     |   Retorna uma lista com todas as sessões do usuário atual |   ``` none ```                            |   200 OK + ```json [ {...}, {...}] ```   |
|   /sessions/{id}          |   GET     |   Retorna uma sessão específica do usuário atual          |   ``` none ```                            |   200 OK + ```json { "id": "...", "date": "yyyy-mm-dd", "durationInMinutes": "...", "subject": "...", "goalId": "..." } ```  |
|   /sessions/{id}          |   PUT     |   Atualiza dados de uma sessão específica do usuário atual|   ```json { "subject": "..." } ```        |   200 OK + ```json { "id": "...", "date": "yyyy-mm-dd", "durationInMinutes": "...", "subject": "...", "goalId": "..." } ```  |
|   /sessions/{id}/finish   |   PATCH   |   Finaliza a contagem da duração da sessão                |   ``` none ```                            |   200 OK + ```json { "id": "...", "date": "yyyy-mm-dd", "durationInMinutes": "...", "subject": "...", "goalId": "..." } ```  |
|   /sessions/{id}          |   DELETE  |   Deleta uma sessão específica do usuário atual           |   ``` none ```                            |   204 No Content              |

#### Observações:
* Todas as rotas de usuário, metas e sessões requerem autenticação via token JWT no header "Authorization: Bearer <token>
* Novas rotas serão adicionadas conforme o desenvolvimento.
* formatos de datas seguem "yyyy-mm-dd"
* Respostas de listagem podem ser arrays vazios se não houver dados

## Objetos de transferência de dados (DTOs - Data Transfer Objects)
### DTOs de Autenticação
#### AuthResponse
```json
{
    "token": "String"
}
```

Retorna o token JWT após login.
#### LoginRequest
```json
{
    "email": "String",
    "password": "String"
}
```

Corpo da requisição para login.
#### RegisterRequest
```json
{
    "name": "String",
    "email": "email",
    "password": "String"
}
```

Corpo da requisição para criar uma conta.

---

### DTOs de Metas
#### GoalResponse
```json
{
    "id": "UUID",
    "title": "String",
    "description": "String",
    "targetDate": "yyyy-mm-dd",
    "status": "ATIVO | COMPLETO | CANCELADO"
}
```

Retorna os dados de uma meta.
#### CreateGoalRequest
```json
{
    "title": "String",
    "description": "String",
    "targetDate": "yyyy-mm-dd"
}
```

Corpo para criar uma nova meta.
#### UpdateGoalRequest
```json
{
    "title": "String",
    "description": "String"
}
```

Corpo para atualizar uma meta existente.

---

### DTOs de Sessões de Estudo
#### StudySessionResponse
```json
{
    "id": "UUID",
    "date": "yyyy-mm-dd",
    "durationInMinutes": "Integer",
    "subject": "String",
    "goalId": "UUID"
}
```

Retorna os dados de uma sessão de estudo.
#### CreateStudySessionRequest
```json
{
    "subject": "String",
    "goalId": "UUID"
}
```

Corpo para criar uma nova sessão de estudo.
#### UpdateSessionRequest
```json
{
    "subject": "String"
}
```

Corpo para atualizar os dados de uma sessão existente.

---

### DTOs de Usuários
#### UserResponse
```json
{
    "id": "UUID",
    "name": "String",
    "email": "email"
}
```

Retorna os dados do usuário.
#### UpdateUserRequest
```json
{
    "name": "String"
}
```

Corpo para atualizar o nome do usuário;


## Serviços
### AuthService
Responsável pelo processo de autenticação da aplicação

Principais responsabilidades:
- Registrar novos usuários
- Autenticar usuários existentes
- Retornar o token JWT após autenticação

---

### JwtService
Responsável pela manipulação de tokens JWT durante a autenticação

Principais responsabilidades:
- Gerar tokens JWT
- Extrair informações (claims) do token
- Validar tokens recebidos nas requisições

---

### GoalService
Responsável pela lógica de negócio relacionada às metas do usuário.

Principais responsabilidades:
- Criar metas
- Atualizar metas
- Buscar metas do usuário
- Deletar metas

---

### StudySessionService
Responsável pela gestão das sessões de estudo do usuário.

Principais responsabilidades:
- Criar sessões de estudo
- Atualizar sessões
- Finalizar sessões
- Listar sessões do usuário

---

### UserService
Responsável pelas operações relacionadas aos dados do usuário.

Principais responsabilidades:
- Buscar dados do usuário autenticado
- Atualizar informações do usuário
- Deletar conta do usuário

## Modelos de entidades
### User
Representa um usuário da plataforma

Campos:
- id (UUID) — identificador único
- name (String) — nome do usuário
- email (String) — email único usado para login
- password (String) — senha criptografada
- role (Enum) — papel do usuário no sistema

UserRole:
- ROLE_USER
- ROLE_ADMIN

Relacionamentos:
- goals — lista de metas criadas pelo usuário (OneToMany)
- sessions — lista de sessões de estudo registradas (OneToMany)

### Goal
Representa uma meta de estudo criada por um usuário.

Campos:

- id (UUID) — identificador único da meta
- title (String) — título da meta
- description (String) — descrição detalhada
- targetDate (Date) — data limite para atingir a meta
- status (Enum) — estado atual da meta

GoalStatus:
- ATIVO
- COMPLETO
- CANCELADO

Relacionamentos:

- user — usuário dono da meta (ManyToOne)
- sessions — sessões de estudo associadas à meta (OneToMany)

### StudySession
Representa uma sessão de estudo registrada pelo usuário.

Campos:

- id (UUID) — identificador único da sessão
- date (Date) — data em que a sessão ocorreu
- startTime (DateTime) — horário de início da sessão
- endTime (DateTime) — horário de término da sessão
- durationInMinutes (Integer) — duração total da sessão em minutos
- subject (String) — tema ou assunto estudado

Relacionamentos:

- user — usuário dono da sessão (ManyToOne)
- goal — meta associada à sessão (opcional) (ManyToOne)

## Roadmap de Desenvolvimento

### Fase 1 - Fundação
* Setup do projeto
* Conexão com banco
* Autenticação (JWT, BCrypt)
* Proteção de rotas
* Estrutura organizada por feature

### Fase 2 - Core da Plataforma
* CRUD de metas, usuários e sessões
* validação de dados
* Estrutura relacional entre entidades

### Fase 3 - Dashboard Básico
* Total de horas
* Horas por semana
* Horas por mês
* Média diária

_Novas fases vão ser adicionadas conforme o projeto crescer, planejo fazer um dashboard mais avançado, fazer uma integração com a API do GitHub, adicionar um frontend e muito mais._

## Considerações finais
Este projeto é uma forma de colocar meus conhecimentos em prática e integrar tecnologias que venho estudando.
É a primeira vez que trabalho com autenticação JWT e BCrypt, então updates futuros podem surgir conforme eu aprender mais.
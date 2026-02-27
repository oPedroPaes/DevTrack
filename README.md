# DevTrack - Plataforma de Gestão de Estudos para Desenvolvedores

## O que é

DevTrack é uma plataforma backend para registro, análise e visualização de dados de estudo, voltada para desenvolvedores.  
A aplicação permite que o usuário registre metas e sessões de estudo, acompanhe seu progresso através de gráficos e métricas, e visualize evolução semanal e mensal.  
Futuras versões poderão incluir integração com GitHub e gamificação do progresso.


## Tecnologias Utilizadas

- **Backend:** Spring Boot
- **Banco de Dados:** PostgreSQL
- **Autenticação:** JWT e BCrypt
- **Controle de versão:** Git e GitHub


## Pré-Requisistos

- Java 21 (LTS)
- Maven 3.9.12 (via wrapper)
- PostgreSQL 18
- Git

## Como Rodar Localmente

### Primeiro Passo: Clonar o repositório
~~~bash
git clone https://github.com/oPedroPaes/DevTrack.git
cd DevTrack
~~~

### Segundo Passo: Criar o Banco de Dados
No PostgreSQL:
~~~SQL
CREATE DATABASE devtrack;
~~~

### Terceiro Passo: Configurar o application.properties
Edite o arquivo:
~~~properties
spring.datasource.url=jdbc:postgresql://localhost:5432/devtrack
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
spring.jpa.hibernate.ddl-auto=update
~~~
Ajuste username e password conforme seu ambiente local.

### Quarto Passo: Executar a aplicação
Abra o terminal na pasta raiz
~~~bash
cd (pasta raiz)
mvnw spring-boot:run
~~~

Por padrão a aplicação será iniciada em:
~~~bash
http://localhost:8080
~~~

### Quinto Passo: Testar endpoints
Ferramentas recomendadas:
* Postman
* Insomnia
* Curl

Exemplo de registro de usuário:
Método: POST
URL: http://localhost:8080/auth/register
Body:
~~~json
{
    "name": "João",
    "email": "joaodev@email.com",
    "password": "teste424"
}
~~~

Resposta esperada (200 OK):
~~~json
User registered
~~~

## Rotas Principais da API
* /auth/register -> Criar conta
* /auth/login -> logar na conta

Novas rotas serão adicionadas conforme o desenvolvimento.

## Roadmap de Desenvolvimento

### Fase 1 - Fundação
* Setup do projeto
* Conexão com banco
* Autenticação (JWT, BCrypt)
* Proteção de rotas
* Estrutura organizada por feature

### Fase 2 - Core da Plataforma
* CRUD de metas, categorias e sessões
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
# Cadastro de Usuários - Testes API (Backend)

Suíte de testes automatizados para validação da funcionalidade de **Cadastro de Usuários** do sistema [ServeRest](https://serverest.dev/), desenvolvida com foco em qualidade, manutenibilidade e boas práticas de automação de testes de API.

---

## Documento - Estratégia e Cenários BDD (Testes WEB e API):
https://docs.google.com/document/d/1kIhzwVqWpsVe6nAWmb3MmuIyx9Ve910WFOy-YHC-SiI/edit?usp=sharing

## 📋 Sumário

- [Introdução](#introdução)
- [Pré-requisitos](#pré-requisitos)
- [Instalação](#instalação)
- [Como Executar](#como-executar)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Padrões de Projeto e Decisões Técnicas](#padrões-de-projeto-e-decisões-técnicas)
- [Regras de Negócio](#regras-de-negócio)
- [Cenários de Teste](#cenários-de-teste)
- [Geração de Relatórios](#geração-de-relatórios)
- [Boas Práticas Implementadas](#boas-práticas-implementadas)
- [Documentação Complementar](#documentação-complementar)
- [Autor](#autor)

---

## Introdução

Este projeto implementa uma **suíte abrangente de testes automatizados de API** para validar o módulo de Cadastro de Usuários, abrangendo:

- ✅ **Testes de Fluxo Positivo** - Cadastro de usuário comum e administrador
- 📋 **Validação de Contrato** - Estrutura de resposta conforme Schema definido
- 🔍 **Validação de Campos** - Campos obrigatórios, dados inválidos e cadastro em duplicidade
- 🚨 **Validação de Erros e Status Codes** - Respostas de erro, mensagens de validação e Status Codes apropriados

**Total de Cenários:** 10 testes automatizados (CT01-CT10)

---

## Pré-requisitos

Antes de começar, certifique-se de ter instalado:

- **Java Development Kit (JDK)** - [Download](https://www.oracle.com/java/technologies/downloads/)
- **Maven** - [Download](https://maven.apache.org/download.cgi)
- **Git** - [Download](https://git-scm.com/)

### Verificar instalação:

```bash
java -version
mvn --version
```

---

## Instalação

### 1. Clonar o repositório

```bash
git clone https://github.com/rafael-isidro/api-restassured-serverest.git
cd api-restassured-serverest
```

### 2. Instalar dependências

O Maven instalará automaticamente todas as dependências do projeto:

```bash
mvn clean install
```

**Dependências principais:**
- **Rest Assured** - Testes de API REST
- **TestNG** - Framework de testes utilizado
- **Data Faker** - Gera dados dinâmicos e aleatórios para testes (nome, email, senha, etc.)
- **Json Schema Validator** - Validação de schemas JSON
- **Lombok** - Geração de métodos getters, setters e constructors.

---

## Como Executar

### Executar todos os testes

```bash
mvn clean test
```

### Executar testes com suite configurada (testng)

```bash
mvn test -DsuiteXmlFile=src/test/resources/runners/test-user.xml
```

---

## Estrutura do Projeto

```
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/rafaelisidro/
│   │   │       ├── client/
│   │   │       │   ├── BaseClient.java
│   │   │       │   └── UserClient.java
│   │   │       ├── data/
│   │   │       │   ├── constants/
│   │   │       │   │   └── UserMessages.java
│   │   │       │   └── factory/
│   │   │       │       └── UserFactory.java
│   │   │       └── models/
│   │   │           ├── request/
│   │   │           │   └── PostUserRequestModel.java
│   │   │           └── response/
│   │   │               ├── GetAllUsersResponseModel.java
│   │   │               ├── GetUserResponseModel.java
│   │   │               └── PostUserResponseModel.java
│   │   └── resources/
│   │       └── application-test.properties
│   └── test/
│       ├── java/
│       │   └── org/rafaelisidro/
│       │       └── test/
│       │           ├── UserGetTest.java
│       │           └── UserPostTest.java
│       └── resources/
│           ├── runners/
│           │   └── test-user.xml
│           └── schemas/
│               ├── get-users-schema.json
│               └── post-user-schema.json
├── target/
│   ├── classes/
│   ├── test-classes/
│   ├── surefire-reports/
│   └── extent-reports/
├── pom.xml
└── .gitignore
```

### Descrição dos Pastas Principais

- **client/** - Classes responsáveis pela comunicação com a API (BaseClient, UserClient)
- **data/** - Dados de teste, factory e constants
- **models/** - Modelos de requisição e resposta
- **test/** - Classes de testes automatizados
- **schemas/** - Arquivos JSON Schema para validação de respostas
- **runners/** - Arquivos de configuração de suites de testes (TestNG)

---

## Padrões de Projeto e Decisões Técnicas

### 1. **REST Assured como Framework de Testes de API**

- Framework nativo para testes de API REST em Java
- Sintaxe fluente e intuitiva
- Suporte a asserções simples e complexas
- Fácil validação de JSON e schemas
- Excelente integração com TestNG/JUnit
- Ampla comunidade e documentação

---

### 2. **Client Pattern**

- **BaseClient:** Classe abstrata com configuração padrão para request da API
- **Centralização:** Todas as requisições e endpoints centralizados
- **Manutenibilidade:** Mudança na API = alteração em um único cliente
- **Legibilidade:** Testes concentram-se na lógica/validação
- **Reutilização:** Métodos compartilhados entre múltiplos testes

**Implementação:**
```
src/main/java/org/rafaelisidro/client/
  ├── BaseClient.java
  └── UserClient.java
```

---

### 3. **Factory Pattern para Geração de Dados (UserFactory.java)**

- Evita dados "chumbados" que causam falhas repetidas
- Facilita criação de cenários com variações específicas
- Centraliza lógica de geração em um único lugar
- Simplifica manutenção e atualização de dados de teste
- Suporta cenários positivos e negativos

**Implementação:**
```
src/main/java/org/rafaelisidro/data/factory/
  └── UserFactory.java
```

---

### 4. **Model Objects para Request e Response**

- Reutilização em diferentes testes
- Suíte de Testes mais escalável
- Facilidade de manutenção nos atributos do modelo

**Implementação:**
```
src/main/java/org/rafaelisidro/models/
  ├── request/
  │   └── PostUserRequestModel.java
  └── response/
      ├── GetAllUsersResponseModel.java
      ├── GetUserResponseModel.java
      └── PostUserResponseModel.java
```

---

### 5. **JSON Schema Validation**

- Valida estrutura de responses com schemas predefinidos
- Evita testes frágeis que ignoram campos
- Automatiza detecção de mudanças na estrutura API
- Schemas reutilizáveis entre testes

**Implementação:**
```
src/test/resources/schemas/
  ├── get-users-schema.json
  └── post-user-schema.json
```

**Uso no teste:**
```java
response
    .body(matchesJsonSchema(new File("schemas/post-user-schema.json")))
```

---

### 6. **Constantes Centralizadas (UserMessages.java)**

- Facilita manutenção quando mensagens mudam
- Evita duplicação de strings nos testes
- Melhora legibilidade do código
- Suporta mensagens de sucesso e erro

---

## Cenários de Teste

### **Cenários Positivos (Fluxo Principal)**

| ID | Descrição | Método HTTP |
|---|---|---|
| CT01	| Cadastrar usuário comum com dados válidos	| POST /usuarios
| CT02	| Cadastrar usuário administrador com dados válidos	| POST /usuarios
| CT03	| Listar todos os usuários com sucesso	| GET /usuarios
| CT04	| Listar usuário filtrando por nome existente | GET /usuarios?nome=

### **Validação de Campos**

| ID | Descrição | Método HTTP |
|---|---|---|
| CT05	| Tentar cadastrar usuário com email já existente |	POST /usuarios |
| CT06	| Tentar cadastrar usuário com email inválido |	POST /usuarios |
| CT07	| Tentar cadastrar usuário com todos os campos em branco | POST /usuarios

### **Validação de Schema e Response**

| ID | Descrição | Método HTTP |
|---|---|---|
| CT08	| Validar schema da resposta POST | POST /usuarios
| CT09	| Validar schema da resposta GET (todos usuários) | GET /usuarios
| CT10	| Validar que usuários listados não possuem campo password | GET /usuarios

**Total de testes:** 10 cenários automatizados

---

## Boas Práticas Implementadas

### Independência dos Testes
- Cada teste é independente
- Não utiliza dados de testes anteriores
- Podem ser executados em qualquer ordem

### Massa de Dados Dinâmica
- Factory para geração com variações
- Suporta cenários com dados específicos
- Evita inserção de dados "chumbados" nos testes

### Código Limpo e Legível
- Nomes descritivos de métodos
- Métodos pequenos e focados
- Separação clara de responsabilidades

### Manutenibilidade
- Endpoints e Configurações centralizadas (Clients)
- Constantes em arquivo separado
- Factory para geração de dados
- Models reutilizáveis entre testes

### Escalabilidade
- Estrutura permite adicionar novos endpoints facilmente
- Novos testes reutilizam clients e factories
- BaseClient extensível com novos métodos

### Validação Robusta
- Validação de status code
- Validação de schema JSON
- Validação de campos específicos
- Mensagens de erro com contexto

---

## Documentação Complementar

Documentação adicional reunindo bugs encontrados durante a automação:

- **[BUGS.md](./BUGS.md)**

---

## Autor

**Rafael Isidro**  
*Analista de Testes de Software*

📅 **Data:** 01 de Março de 2026  
🔗 **GitHub:** [rafael-isidro](https://github.com/rafael-isidro/)

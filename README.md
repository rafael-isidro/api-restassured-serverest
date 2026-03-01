# Cadastro de Usuários - Testes API (Backend)

Suíte de testes automatizados para validação da funcionalidade de **Cadastro de Usuários** do sistema [ServeRest](https://serverest.dev/), desenvolvida com foco em qualidade, manutenibilidade e boas práticas de automação de testes de API.

---

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

**Total de Cenários:** 09 testes automatizados (CT01-CT09)

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
- **data/** - Dados de teste, factories e constantes
- **models/** - Modelos de requisição e resposta (POJO/DTO)
- **test/** - Classes de testes automatizados
- **schemas/** - Arquivos JSON Schema para validação de respostas
- **runners/** - Arquivos de configuração de suites de testes (TestNG)

---

## Padrões de Projeto e Decisões Técnicas

### 1. **REST Assured como Framework de Testes de API**

**Por que REST Assured?**

- Framework nativo para testes de API REST em Java
- Sintaxe fluente e intuitiva
- Suporte a asserções simples e complexas
- Fácil validação de JSON e schemas
- Excelente integração com JUnit/TestNG
- Ampla comunidade e documentação

**Alternativa considerada:** Selenium WebDriver (inadequado para API)

---

### 2. **Page Object Model adaptado para Client Pattern**

- **BaseClient:** Classe abstrata com métodos comuns (GET, POST, PUT, DELETE)
- **UserClient:** Implementação específica com endpoints de usuários
- **Centralização:** Todas as requisições em um único lugar
- **Manutenibilidade:** Mudança na API = alteração em um único cliente
- **Legibilidade:** Testes concentram-se na lógica, não em detalhes técnicos
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

- **POJO (Plain Old Java Objects):** Representação de estruturas JSON em Java
- Desserialização automática de respostas
- Type-safety na manipulação de dados
- Fácil validação de campos
- Suporta anotações de serialização (Jackson/Gson)

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

- Valida estrutura de respostas contra schemas predefinidos
- Evita testes frágeis que validam apenas alguns campos
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

### 7. **Maven como Gerenciador de Dependências e Build**

- Gerenciamento automático de bibliotecas
- Fácil configuração de plugins (Surefire, Failsafe)
- Suporte a profiles (dev, test, prod)
- Integração com CI/CD pipelines

---

## Regras de Negócio

### RN01 - O sistema não deve permitir o cadastro de usuários com nome já existente na base de dados.

**Validação:** CT03 - Tentar cadastrar usuário com nome já cadastrado

---

### RN02 - E-mail válido e único: O sistema deve validar se o e-mail informado possui formato válido (ex.: usuario@email.com) e não deve permitir cadastro com email já existente na base de dados.

**Validações:**
- CT04 - Tentar cadastrar usuário com email já cadastrado
- CT05 - Tentar cadastrar usuário com email inválido

---

## Cenários de Teste

### **Cenários Positivos (Fluxo Principal)**

| ID | Descrição | Método HTTP |
|---|---|---|
| CT01 | Cadastrar usuário comum com dados válidos | POST /usuarios |
| CT02 | Cadastrar usuário administrador com dados válidos | POST /usuarios |

### **Validação de Regras de Negócio**

| ID | Descrição | Regra de Negócio | HTTP |
|---|---|---|---|
| CT03 | Tentar cadastrar com nome já existente | RN01 | POST /usuarios |
| CT04 | Tentar cadastrar com email já existente | RN02 | POST /usuarios |
| CT05 | Tentar cadastrar com email inválido | RN02 | POST /usuarios |

### **Validação de Campos**

| ID | Descrição | HTTP |
|---|---|---|
| CT06 | Tentar cadastrar com Nome em branco | POST /usuarios |
| CT07 | Tentar cadastrar com Nome inválido (caracteres especiais) | POST /usuarios |
| CT08 | Tentar cadastrar com Email em branco | POST /usuarios |
| CT09 | Tentar cadastrar com Senha em branco | POST /usuarios |
| CT10 | Tentar cadastrar com Todos os campos em branco | POST /usuarios |

### **Validação de Limites de Caracteres**

| ID | Descrição | HTTP |
|---|---|---|
| CT11 | Tentar cadastrar com Nome com menos de 3 caracteres | POST /usuarios |
| CT12 | Tentar cadastrar com Nome com mais de 120 caracteres | POST /usuarios |
| CT13 | Tentar cadastrar com Senha com mais de 100 caracteres | POST /usuarios |

### **Validação de Schema e Response**

| ID | Descrição | HTTP |
|---|---|---|
| CT14 | Validar schema de resposta POST | POST /usuarios |
| CT15 | Validar schema de resposta GET (um usuário) | GET /usuarios/{id} |
| CT16 | Validar schema de resposta GET (todos usuários) | GET /usuarios |

**Total de testes:** 16 cenários automatizados

---

## Geração de Relatórios

### Executar e Visualizar Relatório

Após executar os testes, um relatório é gerado automaticamente:

```bash
mvn clean test
```

**Localização:** `target/surefire-reports/`

### Conteúdo do Relatório (Surefire/Extent Reports)

- ✅ Testes que passaram
- ❌ Testes que falharam
- ⏱️ Tempo de execução de cada teste
- 📊 Estatísticas de execução
- 📝 Logs detalhados de requisições e respostas
- 🔴 Stack trace de erros

### Visualizar Relatório HTML (se configurado com Extent Reports)

```bash
# Abre automaticamente no navegador após execução
target/extent-reports/index.html
```

---

## Boas Práticas Implementadas

### Independência dos Testes
- Cada teste é independente
- Não utiliza dados de testes anteriores
- Podem ser executados em qualquer ordem
- Cleanup automático de dados após teste

### Massa de Dados Dinâmica
- Factory para geração com variações
- IDs e emails únicos por execução
- Suporta cenários com dados específicos
- Fácil parametrização para diferentes casos

### Código Limpo e Legível
- Nomes descritivos de métodos
- Métodos pequenos e focados
- Documentação clara em JavaDoc
- Separação clara de responsabilidades

### Manutenibilidade
- Seletores/endpoints centralizados (Client)
- Constantes em arquivo dedicado
- Factory para geração de dados
- Models reutilizáveis entre testes

### Escalabilidade
- Estrutura permite adicionar novos endpoints facilmente
- Novos testes reutilizam clients e factories
- BaseClient extensível com novos métodos
- Schemas reutilizáveis

### Validação Robusta
- Validação de status code
- Validação de schema JSON
- Validação de campos específicos
- Mensagens de erro com contexto

---

## Configuração de Propriedades

### arquivo: `src/main/resources/application-test.properties`

```properties
# Configuração da API
server.url=https://serverest.dev
server.port=

# Timeout (em milissegundos)
request.timeout=5000

# Headers padrões
request.content-type=application/json
request.accept=application/json
```

**Uso no código:**
```java
String baseUrl = System.getProperty("server.url");
```

---

## Estrutura de um Teste

### Exemplo: Cadastro de usuário

```java
@Test
public void testRegisterUserSuccess() {
    // Arrange - Preparar dados
    PostUserRequestModel user = UserFactory.createValidUser();
    
    // Act - Executar ação
    PostUserResponseModel response = userClient.registerUser(user);
    
    // Assert - Validar resultado
    assertThat(response.getStatusCode()).isEqualTo(201);
    assertThat(response.getMessage()).contains("Cadastro realizado com sucesso");
    assertThat(response.getId()).isNotNull();
}
```

---

## Troubleshooting

### Erro: "Dependências não encontradas"
```bash
mvn clean install -U
```

### Erro: "Testes não encontrados"
Verifique que a classe de teste termina com `Test` ou `Tests`

### Erro: "Propriedades não carregadas"
Verifique se `application-test.properties` existe em `src/main/resources/`

### Erro: "Schema validation failed"
Verifique se o arquivo de schema JSON está em `src/test/resources/schemas/`

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

---

## Licença

Este projeto está disponível sob a licença MIT.

---

## Contribuindo

Para contribuir com melhorias:

1. Faça um Fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/melhoria`)
3. Commit suas mudanças (`git commit -m 'Adiciona melhoria'`)
4. Push para a branch (`git push origin feature/melhoria`)
5. Abra um Pull Request

---

## Suporte

Para dúvidas ou reporte de problemas, abra uma **issue** neste repositório ou entre em contato direto.

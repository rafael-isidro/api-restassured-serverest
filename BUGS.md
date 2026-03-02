# Relatório de Bugs
Registro de bugs encontrados durante a automação dos testes de API do módulo de ***Cadastro de Usuários - ServeRest***.

---

## 📋 Sumário
- [Bugs Reportados](#bugs-reportados)
- [Resumo de Bugs](#resumo-de-bugs)
- [Análise por Severidade](#análise-por-severidade)

---

## Bugs Reportados

### BUG-007: Exposição de Senha em Resposta da API ⚠️ **CRÍTICO DE SEGURANÇA**
**Severidade:** 🔴 **CRÍTICA**  
**Status:** ABERTO  
**Data de Abertura:** 01/03/2026  
**Testes Afetados:** CT10

#### ❌ Comportamento Atual
A API está retornando o campo `password` exposto na Response.

**Exemplo da Resposta Atual:**
```json
{
  "nome": "João Silva",
  "email": "joao@example.com",
  "password": "teste123",
  "administrador": true,
  "_id": "507f1f77bcf86cd799439011"
}
```

#### ✅ Comportamento Esperado:

**Não retornar o campo password no GET de Usuários.**
```json
{
  "nome": "João Silva",
  "email": "joao@example.com",
  "administrador": true,
  "_id": "507f1f77bcf86cd799439011"
}
```

#### 📋 Passos para Reproduzir
1. Executar requisição GET para `/usuarios`
2. Analisar resposta JSON
3. Observar que campo `password` está presente nos usuários retornados

---

### MLH-008: Campo administrador Cadastrado e Retornado como String ao invés de Boolean
**Severidade:** 🟠 **BAIXA**  
**Status:** ABERTO  
**Data de Abertura:** 01/03/2026  
**Testes Afetados:** CT01, CT02, CT03, CT04

#### ❌ Comportamento Atual :
O campo `administrador` está sendo cadastrado e retornado como **String** (`"true"` ou `"false"`) ao invés de **Boolean** (`true` ou `false`).

**Exemplo da Resposta Atual:**
```json
{
  "nome": "Jose Andrade",
  "email": "jose@example.com",
  "administrador": "true",
  "_id": "507f1f77bcf86cd799439011"
}
```

#### ✅ Comportamento Esperado:
**O campo `administrador` deve ser cadastrado e retornado como Boolean**.
```json
{
  "nome": "João Silva",
  "email": "joao@example.com",
  "administrador": true,
  "_id": "507f1f77bcf86cd799439011"
}
```

#### 📋 Passos para Reproduzir
1. Executar requisição POST para criar usuário: `POST /usuarios`
2. Executar requisição GET para listar usuários: `GET /usuarios`
3. Analisar resposta JSON
4. Verificar tipo do campo `administrador`
5. Confirmar que está como String (`"true"`) e não Boolean (`true`)

---

## Resumo de Bugs e Melhorias

| ID | Título | Severidade | Status | Testes Afetados |
|---|---|---|---|---|
| **BUG-007** | **Exposição de Senha em Resposta da API** | 🔴 **CRÍTICA** | ABERTO | CT10 |
| **MLH-008** | **Campo administrador como String ao invés de Boolean** | 🟠 **BAIXA** | ABERTO | CT01, CT02, CT03, CT04 |

---

## Análise por Severidade

### 🔴 CRÍTICA (1)
| Bug | Descrição | Impacto |
|-----|-----------|--------|
| **BUG-007** | Exposição de Senha em Resposta da API | Vulnerabilidade grave de segurança, violação de LGPD, comprometimento de dados |


### 🟠 MÉDIA (1)
| Bug | Descrição | Impacto |
|-----|-----------|--------|
| **MLH-008** | Campo administrador como String ao invés de Boolean | Inconsistência de tipagem, erros em consumidores da API |

---

**Data de Atualização:** 01/03/2026  
**Responsável:** Rafael Isidro  
**Versão:** 1.0

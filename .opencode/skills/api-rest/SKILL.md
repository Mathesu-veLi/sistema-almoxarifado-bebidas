---
name: api-rest
description: Como implementar a API REST Spring Boot fiel ao Contrato Técnico da Indústria de Bebidas, com as 26 operações, DTOs, validação, status HTTP, erros e Postman.
---

# API REST Skill (Spring Boot)

## 1. Regra de ouro: o contrato é lei

A fonte de verdade da integração é `Diversos/Contrato_Tecnico_API_Industria_Bebidas.docx`. A API deve reproduzir exatamente:

- a **base URL** `/api`;
- os **métodos HTTP** e as **rotas**;
- os **nomes dos campos** JSON;
- os **formatos** de data, timestamp e lista;
- os **códigos HTTP** de sucesso e de erro;
- os **filtros** e a **ordenação** de cada listagem.

Não renomeie campos, não invente rotas, não troque 409 por 400 e não devolva um formato "mais fácil". O front-end é de outra empresa e já espera o contrato.

| Item | Regra |
| --- | --- |
| Base URL | `/api` |
| Content-Type / Accept | `application/json` |
| IDs | inteiros positivos |
| Datas operacionais | `YYYY-MM-DD` |
| Timestamps | ISO 8601 com fuso |
| Listas | array JSON, inclusive `[]` |
| Token | `Authorization: Bearer <JWT>` |
| Expiração do token | 3600 segundos |
| Rotas públicas | `POST /api/usuarios` e `POST /api/auth/login` |
| Senha | nunca aparece na resposta |

## 2. Camadas e responsabilidades

```text
Controller  →  Service  →  Repository  →  Banco
     │            │
     │            └─ regras de negócio, @Transactional, concorrência
     └─ HTTP, @Valid, DTO de entrada/saída, status correto
```

Sugestão de organização de pacotes (a equipe pode adaptar):

```text
com.senai.bebidas
├── config
├── controller
├── dto
├── exception
├── model
├── repository
├── security
└── service
```

- **Controller:** não contém regra de negócio. Recebe DTO com `@Valid`, chama o service e devolve `ResponseEntity` com o status correto.
- **Service:** valida regras, controla transações, converte entidades em DTOs e lança exceções de negócio.
- **Repository:** interface `JpaRepository`; consultas derivadas ou `@Query` quando necessário.
- **DTO:** separa o contrato da entidade. Nunca devolva a entidade JPA com senha/hash ou relacionamentos circulares.
- **Exception Handler:** `@RestControllerAdvice` para padronizar `{status, mensagem, timestamp}`.
- **Security:** filtro JWT antes dos controllers; não coloque validação de token dentro de cada endpoint.

## 3. Checklist das 26 operações

Use esta tabela para conferir a coleção Postman e o código. O detalhe de request/response está no contrato.

### Usuários e autenticação (7)

| Método e rota | Sucesso | Pontos de atenção |
| --- | --- | --- |
| `POST /api/usuarios` | 201 | público; nome, login e senha obrigatórios; senha ≥ 8; login único; não devolver senha |
| `POST /api/auth/login` | 200 | público; retorna `token`, `tipo: Bearer`, `expiraEm: 3600`; inválido/inativo → 401 |
| `GET /api/usuarios/me` | 200 | identidade vem do JWT, nunca de um id enviado pelo cliente |
| `GET /api/usuarios` | 200 | ativos por padrão; `?ativo=todos` ou `?ativo=false`; ordem por id crescente |
| `GET /api/usuarios/{id}` | 200 | inclui inativo; não encontrado → 404 |
| `PUT /api/usuarios/{id}` | 200 | atualiza nome, login e `ativo`; não altera senha; login único; `ativo=true` reativa |
| `DELETE /api/usuarios/{id}` | 204 | inativação lógica; preserva histórico; sem corpo |

### Setores (5)

| Método e rota | Sucesso | Pontos de atenção |
| --- | --- | --- |
| `POST /api/setores` | 201 | nome obrigatório e único entre ativos |
| `GET /api/setores` | 200 | ativos por padrão; `?ativo=todos` ou `?ativo=false`; id crescente |
| `GET /api/setores/{id}` | 200 | inclui inativo; 404 se não existir |
| `PUT /api/setores/{id}` | 200 | `ativo` obrigatório; nome único entre ativos; reativação permitida |
| `DELETE /api/setores/{id}` | 204 | exclui se sem vínculos; senão inativa; preserva histórico |

### Fornecedores (5)

| Método e rota | Sucesso | Pontos de atenção |
| --- | --- | --- |
| `POST /api/fornecedores` | 201 | nome e CNPJ obrigatórios; CNPJ com 14 dígitos e único; telefone opcional |
| `GET /api/fornecedores` | 200 | ativos por padrão; `?ativo=todos` ou `?ativo=false`; id crescente |
| `GET /api/fornecedores/{id}` | 200 | inclui inativo; 404 se não existir |
| `PUT /api/fornecedores/{id}` | 200 | CNPJ único; `ativo` obrigatório; fornecedor inativo não pode ser atribuído a novo vínculo |
| `DELETE /api/fornecedores/{id}` | 204 | se associado a insumos, preserva referência e inativa |

### Insumos (5)

| Método e rota | Sucesso | Pontos de atenção |
| --- | --- | --- |
| `POST /api/insumos` | 201 | saldo inicial sempre 0; não aceitar `quantidadeAtual`; fornecedor ativo |
| `GET /api/insumos` | 200 | `?nome=` parcial e sem caixa; `?ativo=todos`; ordem alfabética |
| `GET /api/insumos/{id}` | 200 | inclui inativo; 404 se não existir |
| `PUT /api/insumos/{id}` | 200 | não altera saldo; unidade imutável após movimentação; fornecedor ativo; `ativo` obrigatório |
| `DELETE /api/insumos/{id}` | 204 | exclui sem movimentações; com histórico, inativa e preserva |

### Movimentações (3)

| Método e rota | Sucesso | Pontos de atenção |
| --- | --- | --- |
| `POST /api/movimentacoes` | 201 | `ENTRADA`/`SAIDA`; `usuarioId` vem do JWT; `SAIDA` exige setor ativo; entrada aceita lote/validade opcionais; data não futura; transação + concorrência |
| `GET /api/movimentacoes` | 200 | filtros `insumoId`, `setorId`, `usuarioId`, `tipo`, `dataInicio`, `dataFim`; ordem `dataRegistro` desc e `id` desc |
| `GET /api/movimentacoes/{id}` | 200 | 404 se não existir; não há PUT/DELETE de movimentação |

### Alertas (1)

| Método e rota | Sucesso | Pontos de atenção |
| --- | --- | --- |
| `GET /api/alertas/estoque` | 200 | somente insumos ativos com `quantidadeAtual <= estoqueMinimo`; ordem por nome; `[]` se não houver; situação `CRITICO` |

## 4. DTOs e validação

Use DTOs separados para entrada e saída. Não receba nem devolva a entidade JPA diretamente.

Validações comuns:

```java
public record UsuarioRequest(
        @NotBlank String nome,
        @NotBlank String login,
        @NotBlank @Size(min = 8) String senha) { }
```

```java
public record InsumoRequest(
        @NotBlank String nome,
        @NotBlank String unidadeMedida,
        @NotNull @PositiveOrZero BigDecimal estoqueMinimo,
        @NotNull Integer fornecedorId) { }
```

```java
public record MovimentacaoRequest(
        @NotNull Integer insumoId,
        @NotNull TipoMovimentacao tipo,
        @NotNull @Positive BigDecimal quantidade,
        Integer setorId,
        @NotNull LocalDate dataMovimentacao,
        String lote,
        LocalDate validade) { }
```

Cuidados:

- `POST /api/insumos` **não** recebe `quantidadeAtual`; o saldo começa em zero.
- `POST /api/movimentacoes` **não** recebe `usuarioId`; ele vem do JWT.
- `SAIDA` exige `setorId` de setor ativo e **não** recebe lote/validade.
- `ENTRADA` pode omitir lote/validade; se validade vier, não pode ser anterior à `dataMovimentacao`.
- `dataMovimentacao` não pode ser futura.
- `UN` usa quantidade inteira; `KG` e `L` aceitam até 3 casas decimais. Use `BigDecimal` para dinheiro/quantidade fracionária.
- Nos `PUT` de cadastros, `ativo` é obrigatório.
- CNPJ deve ter 14 dígitos e ser único.
- Login deve ser único e a senha nunca é devolvida.
- Não confie em `usuarioId`, `quantidadeAtual`, `saldoAnterior` ou `saldoAtual` enviados pelo cliente.

## 5. Erros padronizados

Formato:

```json
{
  "status": 409,
  "mensagem": "Quantidade solicitada superior ao estoque disponível",
  "timestamp": "2026-11-05T15:20:00-03:00"
}
```

Mapeamento:

| HTTP | Quando | Exceção sugerida |
| --- | --- | --- |
| 400 | campo obrigatório, formato inválido, quantidade ≤ 0, data futura, `ativo` inválido, `dataInicio > dataFim` | `MethodArgumentNotValidException`, `ValidacaoException` |
| 401 | credenciais inválidas, token ausente/inválido/expirado, usuário inativo | `AuthenticationException`, filtro JWT |
| 404 | ID inexistente | `RecursoNaoEncontradoException` |
| 409 | login/CNPJ duplicado, estoque insuficiente, insumo inativo, vínculo que impede exclusão | `RegraNegocioException` |
| 500 | falha inesperada | `Exception` genérica no handler |

Regras:

- `@RestControllerAdvice` centraliza o tratamento.
- Não capture exceção dentro do método `@Transactional` sem relançar: isso impede o rollback.
- Não devolva stack trace, SQL ou detalhes internos.
- O `timestamp` deve ser o instante real da falha.

## 6. Exclusão, reativação e listagem

- `DELETE /api/usuarios/{id}`: sempre inativação lógica.
- `DELETE` de setores, fornecedores e insumos: exclusão física se não houver vínculos; caso contrário, inativação lógica.
- Ambos retornam **204 sem corpo**.
- `GET /{id}` retorna inativos com `ativo=false`.
- Listagens mostram ativos por padrão e aceitam `?ativo=todos` ou `?ativo=false`.
- `PUT` de cadastros exige `ativo` e permite reativar.
- Movimentações não têm `ativo` e não têm endpoints de alteração/exclusão.

## 7. Ordenação e filtros

- Insumos e alertas: nome crescente.
- Histórico: `dataRegistro` desc, `id` desc no empate.
- Outros cadastros: id crescente.
- Pesquisa de insumos por nome: parcial e `IgnoreCase`.
- Filtros de movimentação por `insumoId`, `setorId`, `usuarioId`, `tipo`, `dataInicio`, `dataFim`.
- A ordenação pode ser feita com `ORDER BY` no banco ou em Java; o contrato atual não exige Quicksort.

## 8. Postman e evidências

1. Crie uma coleção com as 26 operações.
2. Use variáveis de ambiente `baseUrl` e `token`.
3. No login, salve o token retornado.
4. Nas rotas protegidas, envie `Authorization: Bearer {{token}}`.
5. Rode os casos mínimos **CT01 a CT22** do contrato (seção 7).
6. Registre resultado esperado, resultado observado e evidência (print, log ou export).
7. Inclua testes negativos: sem token, token inválido, estoque insuficiente, data futura, duplicidade e insumo inativo.

## 9. Testes automatizados

- `@SpringBootTest` + `MockMvc` para endpoints e status HTTP.
- `@DataJpaTest` para repositórios e consultas.
- Teste o login, o bloqueio sem token, o cadastro de insumo com saldo zero, entrada, saída, estoque insuficiente e rollback.
- Para concorrência, teste duas saídas simultâneas com saldo insuficiente e confirme que nenhuma deixa saldo negativo.
- Execute `mvn test` antes de commitar.

## 10. Anti-padrões

- Alterar rota, campo, JSON ou status do contrato "para facilitar".
- Devolver entidade JPA com senha/hash.
- Receber `usuarioId` no body de movimentação.
- Alterar `quantidadeAtual` diretamente no cadastro de insumo.
- Apagar histórico de movimentação.
- Capturar exceção e não relançar dentro de transação.
- Usar `ddl-auto=create-drop` em um banco com dados.
- Commitar senha, token, chave JWT ou `target/`.
- Implementar frontend, tela Swing ou funcionalidade fora do escopo.

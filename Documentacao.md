# Sistema de Controle Financeiro Pessoal

API REST de estudo para registrar usuários, despesas e receitas. O projeto está
em desenvolvimento e usa Java, Spring Boot, Maven, Spring Data JPA e
PostgreSQL.

## Estado atual

- **Usuários:** cadastro, atualização, login, busca por ID e exclusão.
- **Despesas:** criação, atualização, remoção, consulta por ID, listagem por
  usuário e cálculo do total por usuário.
- **Receitas:** somente cadastro.
- **Categorias:** representadas por `CategoriaEnum`; não há entidade ou
  controller de categorias.
- **Segurança:** `SecurityConfig` está vazio. Login compara senhas em texto
  puro; ainda não há autenticação/autorização com Spring Security nem BCrypt.
- **Testes:** há apenas um teste de contexto (`contextLoads`).
- **Pagamento de despesas:** a entidade e os DTOs incluem forma, status e data
  de vencimento. O status é recebido/mapeado, mas o service ainda não o calcula
  automaticamente com base no vencimento.
- **Orçamento:** ainda não implementado.

## Tecnologias

| Tecnologia | Uso |
|---|---|
| Java 21 | Linguagem principal declarada no `pom.xml` |
| Spring Boot 4.1.1 | Aplicação e API REST |
| Spring Web | Controllers e endpoints HTTP |
| Spring Data JPA | Persistência e repositories |
| Jakarta Validation | Restrições de validação |
| PostgreSQL | Banco de dados |
| Maven | Dependências e build |
| Lombok | Getters, setters e construtores |
| Spring Boot Test / JUnit | Testes |

O `pom.xml` também configura dependências e plugin do Kotlin, embora o código
da aplicação listado neste documento esteja em Java.

## Arquitetura

O fluxo principal é:

```text
Requisição HTTP -> Controller -> Service -> Repository -> PostgreSQL
                       |            |
                       DTO       regras de negócio
                       |
                    Mapper <-> Model/Entity
```

- **Controller:** recebe parâmetros e corpos HTTP, chama o service e define o
  formato da resposta.
- **Service:** concentra operações e regras de negócio, como verificar se o
  usuário existe antes de associar uma despesa.
- **Repository:** abstrai operações de banco com `JpaRepository` e consultas
  derivadas ou JPQL.
- **Model:** entidades JPA persistidas no banco.
- **DTO:** formatos específicos de entrada e saída da API; evitam expor
  diretamente alguns campos das entidades.
- **Mapper:** converte DTOs em entidades e entidades em DTOs.
- **Exception:** exceções específicas e tratamento centralizado em
  `GlobalExceptionHandler`.

## Estrutura do código

Pacote base: `com.example.sistemacontrolefinanceiro`

```text
src/main/java/com/example/sistemacontrolefinanceiro/
├── config/
│   └── SecurityConfig.java
├── controller/
│   ├── DespesaController.java
│   ├── ReceitaController.java
│   └── UsuarioController.java
├── dto/
│   ├── DespesaRequestDTO.java
│   ├── DespesaResponseDTO.java
│   ├── ReceitaRequestDTO.java
│   ├── ReceitaResponseDTO.java
│   ├── UsuarioRequestDTO.java
│   └── UsuarioResponseDTO.java
├── enums/
│   ├── CategoriaEnum.java
│   ├── FormaPagamentoEnum.java
│   └── StatusPagamentoEnum.java
├── exception/
│   ├── DespesaNaoEncontradaException.java
│   ├── GlobalExceptionHandler.java
│   ├── IdNaoExisteException.java
│   ├── SenhaIncorretaException.java
│   ├── UsuarioJaExisteException.java
│   └── UsuarioNaoEncontradoException.java
├── mapper/
│   ├── DespesaMapper.java
│   ├── ReceitaMapper.java
│   └── UsuarioMapper.java
├── model/
│   ├── Despesa.java
│   ├── Receita.java
│   └── Usuario.java
├── repository/
│   ├── DespesaRepository.java
│   ├── ReceitaRepository.java
│   └── UsuarioRepository.java
├── service/
│   ├── DespesaService.java
│   ├── ReceitaService.java
│   └── UsuarioService.java
└── SistemacontrolefinanceiroApplication.java

src/test/java/com/example/sistemacontrolefinanceiro/
└── SistemacontrolefinanceiroApplicationTests.java
```

## Modelo de dados

### Usuario

| Campo | Tipo | Observação |
|---|---|---|
| `id` | `Long` | Chave primária, gerada pelo banco |
| `nome` | `String` | `@NotBlank` |
| `email` | `String` | `@NotBlank` e `@Email` |
| `senha` | `String` | `@NotBlank`; atualmente armazenada em texto puro |

### Despesa

| Campo | Tipo | Observação |
|---|---|---|
| `id` | `Long` | Chave primária, gerada pelo banco |
| `valor` | `BigDecimal` | Valor da despesa |
| `descricao` | `String` | Descrição |
| `data` | `LocalDate` | Data da despesa |
| `dataVencimento` | `LocalDate` | Data de vencimento |
| `categoriaEnum` | `CategoriaEnum` | Categoria; persistida como texto |
| `formaPagamentoEnum` | `FormaPagamentoEnum` | Forma de pagamento; persistida como texto |
| `statusPagamentoEnum` | `StatusPagamentoEnum` | Status; persistido como texto |
| `usuario` | `Usuario` | Relação `ManyToOne`, coluna `usuario_id` |

### Receita

| Campo | Tipo | Observação |
|---|---|---|
| `id` | `Long` | Chave primária, gerada pelo banco |
| `valor` | `BigDecimal` | Valor da receita |
| `descricao` | `String` | Descrição |
| `data` | `LocalDate` | Data da receita |
| `usuario` | `Usuario` | Relação `ManyToOne`, coluna `usuario_id` |

### Enums

| Enum | Valores |
|---|---|
| `CategoriaEnum` | `ALIMENTACAO`, `TRANSPORTE`, `SAUDE`, `EDUCACAO`, `LAZER`, `MORADIA` |
| `FormaPagamentoEnum` | `PIX`, `CARTAO_DEBITO`, `CARTAO_CREDITO` |
| `StatusPagamentoEnum` | `PENDENTE`, `PAGO`, `ATRASADO` |

As enums usadas em `Despesa` têm `@Enumerated(EnumType.STRING)`, portanto o
banco armazena seus nomes, não suas posições.

## DTOs e validação

### Usuários

`UsuarioRequestDTO` recebe `nome`, `email` e `senha`. `UsuarioResponseDTO`
retorna `id`, `nome` e `email`, sem a senha.

O controller ativa a validação do request:

- Cadastro e atualização usam o grupo `CadastroOuAtualizacao`; nesse grupo,
  `nome` é obrigatório.
- `email` é obrigatório, não pode estar em branco e deve ter formato de e-mail.
- `senha` é obrigatória e não pode estar em branco.
- Login valida e-mail e senha, sem exigir `nome`.

`@NotBlank` rejeita `null`, texto vazio e texto composto somente por espaços.

### Despesas

`DespesaRequestDTO` exige:

- `valor` maior que zero;
- `descricao` não vazia;
- `data`, `dataVencimento`, `categoriaEnum`, `formaPagamentoEnum` e
  `usuarioId` informados.
- `statusPagamentoEnum` é opcional no request.

`DespesaResponseDTO` retorna os dados da despesa, incluindo o ID, vencimento,
forma e status de pagamento. `DespesaMapper` converte os campos entre DTO e
entidade.

### Receitas

`ReceitaRequestDTO` contém `id`, `descricao`, `valor` e `data`.
`ReceitaResponseDTO` contém `descricao`, `valor` e `data`.

**Limitação atual:** `ReceitaMapper` usa `dto.id` tanto como ID da receita
quanto como ID do usuário associado. Além disso, `toResponse` altera a entidade
em vez de preencher o DTO de resposta. A associação e a resposta de receita
precisam ser revistas antes de considerar o fluxo completo.

## Endpoints

A aplicação não declara um prefixo global de API; as rotas abaixo são relativas
ao host e à porta configurados.

### Usuários — `/usuarios`

| Método | Rota | Operação |
|---|---|---|
| `POST` | `/usuarios/cadastrar` | Cadastrar usuário |
| `PUT` | `/usuarios/atualizar/{id}` | Atualizar usuário |
| `POST` | `/usuarios/login` | Validar e-mail e senha |
| `GET` | `/usuarios/{id}` | Buscar usuário por ID |
| `DELETE` | `/usuarios/deletar/{id}` | Excluir usuário |

Cadastro/atualização recebem `UsuarioRequestDTO`; login recebe e-mail e senha.
As respostas de cadastro, atualização, login e busca usam
`UsuarioResponseDTO`.

### Despesas — `/Despesas`

O prefixo atual começa com letra maiúscula; as rotas são sensíveis à
configuração do servidor e devem ser chamadas exatamente como declaradas.

| Método | Rota | Operação |
|---|---|---|
| `POST` | `/Despesas/salvar` | Criar despesa |
| `PUT` | `/Despesas/atualizar/{id}` | Atualizar despesa |
| `DELETE` | `/Despesas/remover` | Remover despesa; recebe uma entidade no corpo |
| `GET` | `/Despesas/usuarios/{usuarioId}/` | Listar despesas do usuário |
| `GET` | `/Despesas/usuarios/{usuarioId}/total` | Somar despesas do usuário |
| `GET` | `/Despesas/despesas/{id}` | Buscar despesa por ID |

O total é retornado como `BigDecimal`. A consulta JPQL usa `SUM` e `COALESCE`,
retornando zero quando o usuário não tem despesas. O service também verifica
se o usuário existe.

Exemplo de corpo para criar ou atualizar despesa:

```json
{
  "valor": 45.90,
  "descricao": "Almoço",
  "data": "2026-11-12",
  "dataVencimento": "2026-11-20",
  "categoriaEnum": "ALIMENTACAO",
  "formaPagamentoEnum": "PIX",
  "statusPagamentoEnum": "PENDENTE",
  "usuarioId": 1
}
```

O campo `statusPagamentoEnum` pode ser omitido. Apesar de `dataVencimento` e
status existirem no modelo, atualmente o service não calcula automaticamente
`ATRASADO` quando o vencimento passa.

### Receitas — `/receita`

| Método | Rota | Operação |
|---|---|---|
| `POST` | `/receita/salvar` | Cadastrar receita |

Não há rotas de listagem, busca, atualização ou exclusão de receitas.

## Regras e limitações conhecidas

- O cadastro de usuário verifica e-mail duplicado e senha não vazia.
- O login compara a senha recebida diretamente com a senha armazenada; não use
  este fluxo como autenticação segura em produção.
- O service de despesas valida a existência do usuário ao criar/listar/calcular
  o total; na atualização também valida ID, existência da despesa e valor
  positivo.
- A remoção de despesa recebe a entidade no corpo, em vez de um ID na rota.
- Listagem e busca de despesas retornam entidades diretamente, enquanto
  criação/atualização usam DTOs.
- Algumas exceções ignoram a mensagem recebida no construtor, devolvendo uma
  mensagem genérica.
- `SecurityConfig` existe, mas não configura Spring Security.
- Não há testes específicos dos services, repositories ou endpoints.

## Tratamento de erros

`GlobalExceptionHandler` converte as seguintes exceções em respostas HTTP:

| Exceção | Status |
|---|---|
| `UsuarioJaExisteException` | `409 Conflict` |
| `UsuarioNaoEncontradoException` | `404 Not Found` |
| `SenhaIncorretaException` | `401 Unauthorized` |
| `DespesaNaoEncontradaException` | `404 Not Found` |
| `IdNaoExisteException` | `400 Bad Request` |

`ResponseStatusException` também é usada pelo service de despesas para erros
de requisição.

## Executar e testar

Requisitos: JDK compatível com Java 21, Maven e PostgreSQL.

1. Configure a conexão e as credenciais do PostgreSQL no arquivo de
   configuração local da aplicação. Não publique credenciais no repositório.
2. Na raiz do projeto, inicie a aplicação:

   ```bash
   mvn spring-boot:run
   ```

3. Execute os testes:

   ```bash
   mvn test
   ```

O único teste existente atualmente verifica o carregamento do contexto Spring.

## Próximos passos sugeridos

1. Implementar hash de senhas com BCrypt e configurar autenticação/autorização.
2. Definir e implementar a regra automática de status de pagamento baseada em
   `dataVencimento`.
3. Completar e testar o fluxo de receitas, incluindo o ID do usuário no request
   e o mapeamento da resposta.
4. Padronizar rotas e respostas, especialmente remoção de despesas e retorno
   por DTO na busca/listagem.
5. Adicionar testes unitários e de integração para as regras de negócio.
6. Projetar e implementar o módulo de orçamento.

# AstroTrack

API REST para logística satelital de frotas em regiões remotas, criada como solução para monitoramento de veículos, motoristas, clientes, viagens e checkpoints de rastreamento manual via satélite.

O AstroTrack foi desenvolvido em Java 21 com Spring Boot 3, seguindo arquitetura em camadas, persistência com Spring Data JPA, autenticação JWT, validações com Bean Validation, tratamento global de exceções, documentação OpenAPI/Swagger e endpoints dedicados para navegação HATEOAS.

## Integrantes

| Integrante | RM |
|---|---:|
| Arthur Correia Delila | 563806 |
| Gabriel Henrique Souza Goncalves | 563732 |
| Jose Ricardo Pereira Iannuzzi | 564112 |
| Rafael de Freitas Moraes | 563210 |
| Rafael Pascotte Mercadante | 564928 |

## Links do projeto

| Recurso | Link |
| --- | --- |
| Deploy da API | `https://astrotrack-api.onrender.com` |
|Deploy com swagger|`https://astrotrack-api.onrender.com/swagger-ui/index.html#/`|
| Video de apresentacao | `https://youtu.be/Q7ORViTjn7Q` |
| Repositorio GitHub | `https://github.com/AstroTrack/GS_java` |
| Swagger local | `http://localhost:8080/swagger-ui/index.html` |
| Video pitch | `https://www.youtube.com/watch?v=nHatFjcTgdI` |

## Tema da solucao

O AstroTrack atende o contexto de conectividade e economia espacial aplicado a logistica. Em regioes sem cobertura terrestre estavel, dispositivos IoT instalados em veiculos podem enviar dados por satelite para registrar posicao, alertas de seguranca e eventos de viagem.

A API centraliza esses dados e permite que gestores acompanhem:

- clientes contratantes;
- motoristas;
- veiculos;
- viagens;
- checkpoints de latitude e longitude;
- eventos como botao de panico e porta aberta.

## Tecnologias utilizadas

- Java 21
- Spring Boot 3
- Spring Web
- Spring Data JPA
- Spring Security
- JWT com JJWT
- Spring Validation
- Spring HATEOAS
- SpringDoc OpenAPI / Swagger
- Lombok
- Oracle Database
- Maven

## Arquitetura

O projeto segue organizacao em camadas:

```text
Controller -> Service -> Repository -> Banco de Dados
```

Principais pacotes:

```text
br.com.fiap.AstroTrack
├── config
├── control
├── dto
├── exception
├── model
├── repository
├── security
└── service
```

## Banco de dados

O projeto foi configurado para Oracle Database. As tabelas usam prefixo `AT_` para evitar conflitos com outros objetos do schema.

Tabelas principais:

- `AT_CLIENTES`
- `AT_MOTORISTAS`
- `AT_VEICULOS`
- `AT_VIAGENS`
- `AT_CHECKPOINTS`
- `AT_USUARIOS_SISTEMA`

O arquivo `application.properties` utiliza variaveis de ambiente para proteger as credenciais do banco:

```properties
spring.datasource.url=${ORACLE_DB_URL}
spring.datasource.username=${ORACLE_DB_USERNAME}
spring.datasource.password=${ORACLE_DB_PASSWORD}
```

## Variaveis de ambiente

Antes de executar a aplicacao, configure:

```bash
export ORACLE_DB_URL="jdbc:oracle:thin:@//HOST:PORT/SERVICE_NAME"
export ORACLE_DB_USERNAME="SEU_USUARIO"
export ORACLE_DB_PASSWORD="SUA_SENHA"
export ASTROTRACK_JWT_SECRET="sua-chave-secreta-com-mais-de-32-caracteres"
export ASTROTRACK_JWT_EXPIRATION_MINUTES="120"
```

Exemplo local:

```bash
export ORACLE_DB_URL="jdbc:oracle:thin:@//localhost:1521/FREEPDB1"
export ORACLE_DB_USERNAME="ASTROTRACK"
export ORACLE_DB_PASSWORD="sua_senha"
```

## Execucao local

1. Clone o repositorio:

```bash
git clone INSERIR_LINK_DO_REPOSITORIO_AQUI
```

2. Entre na pasta do projeto:

```bash
cd AstroTrack
```

3. Configure as variaveis de ambiente do Oracle e JWT.

4. Execute a aplicacao:

```bash
./mvnw spring-boot:run
```

5. Acesse o Swagger:

```text
http://localhost:8080/swagger-ui/index.html
```

## Autenticacao JWT

A API usa Spring Security com JWT Bearer Token.

Primeiro, crie um usuario:

```http
POST /auth/register
```

Exemplo de body:

```json
{
  "usuario": "admin",
  "email": "admin@astrotrack.com",
  "senha": "senha123"
}
```

Depois, faca login:

```http
POST /auth/login
```

Exemplo de body:

```json
{
  "email": "admin@astrotrack.com",
  "senha": "senha123"
}
```

A resposta retorna um token JWT. No Swagger, clique em `Authorize` e informe:

```text
Bearer SEU_TOKEN
```

## Endpoints principais

### Autenticacao

| Metodo | Endpoint | Descricao |
| --- | --- | --- |
| POST | `/auth/register` | Cria usuario do sistema e retorna JWT |
| POST | `/auth/login` | Autentica usuario e retorna JWT |

### Clientes

| Metodo | Endpoint | Descricao |
| --- | --- | --- |
| GET | `/clientes` | Lista clientes |
| GET | `/clientes/{id}` | Busca cliente por ID |
| POST | `/clientes` | Cria cliente |
| PUT | `/clientes/{id}` | Atualiza cliente |
| DELETE | `/clientes/{id}` | Remove cliente |

### Motoristas

| Metodo | Endpoint | Descricao |
| --- | --- | --- |
| GET | `/motoristas` | Lista motoristas |
| GET | `/motoristas/{id}` | Busca motorista por ID |
| POST | `/motoristas` | Cria motorista |
| PUT | `/motoristas/{id}` | Atualiza motorista |
| DELETE | `/motoristas/{id}` | Remove motorista |

### Veiculos

| Metodo | Endpoint | Descricao |
| --- | --- | --- |
| GET | `/veiculos` | Lista veiculos |
| GET | `/veiculos/{id}` | Busca veiculo por ID |
| POST | `/veiculos` | Cria veiculo |
| PUT | `/veiculos/{id}` | Atualiza veiculo |
| DELETE | `/veiculos/{id}` | Remove veiculo |

### Viagens

| Metodo | Endpoint | Descricao |
| --- | --- | --- |
| GET | `/viagens` | Lista viagens |
| GET | `/viagens/status?status=EM_ANDAMENTO` | Lista viagens por status |
| GET | `/viagens/{id}` | Busca viagem por ID |
| POST | `/viagens` | Cria viagem |
| PUT | `/viagens/{id}` | Atualiza viagem |
| DELETE | `/viagens/{id}` | Remove viagem |

### Checkpoints

| Metodo | Endpoint | Descricao |
| --- | --- | --- |
| GET | `/checkpoints` | Lista checkpoints |
| GET | `/checkpoints/viagem/{idViagem}` | Lista checkpoints de uma viagem |
| GET | `/checkpoints/{id}` | Busca checkpoint por ID |
| POST | `/checkpoints` | Cria checkpoint |
| PUT | `/checkpoints/{id}` | Atualiza checkpoint |
| DELETE | `/checkpoints/{id}` | Remove checkpoint |

## HATEOAS

O projeto usa Spring HATEOAS por meio de endpoints dedicados de navegacao da API. Os CRUDs principais retornam JSON simples para facilitar os testes no Swagger e no Postman, enquanto os menus HATEOAS centralizam os links disponiveis.

Endpoints HATEOAS disponiveis:

- `GET /hateoas`
- `GET /clientes/hateoas`
- `GET /motoristas/hateoas`
- `GET /veiculos/hateoas`
- `GET /viagens/hateoas`
- `GET /checkpoints/hateoas`

Esses endpoints retornam links para listar, buscar, criar, atualizar e remover recursos, alem dos fluxos especificos como filtro de viagens por status e checkpoints por viagem.

## Validacoes

A API usa Bean Validation em Java Records de entrada:

- `@NotBlank`
- `@NotNull`
- `@Size`
- `@Email`
- `@CPF`
- `@CNPJ`
- `@Pattern`
- `@DecimalMin`
- `@DecimalMax`
- `@PastOrPresent`

## Tratamento de erros

O projeto possui tratamento centralizado com `@RestControllerAdvice`, retornando respostas padronizadas para:

- `400 Bad Request`
- `401 Unauthorized`
- `403 Forbidden`
- `404 Not Found`
- erros de validacao
- erros de regra de negocio
- erros internos

## Modelagem avancada

O projeto contempla recursos avancados de JPA:

- heranca com `PessoaLogistica`;
- objetos embutidos com `@Embedded` e `@Embeddable` em `Rota` e `Coordenada`;
- chave composta com `UsuarioSistemaId`;
- relacionamentos `@ManyToOne` e `@OneToMany`;
- multiplas tabelas relacionadas por chaves estrangeiras.

## Seguranca

- Autenticacao via JWT Bearer Token.
- Senhas criptografadas com BCrypt.
- Filtro JWT com `OncePerRequestFilter`.
- Endpoints protegidos por Spring Security.
- CORS configurado.
- Endpoints publicos apenas para autenticacao e documentacao.

## Informacoes para avaliacao

- Projeto estruturado em camadas.
- CRUD completo para entidades principais.
- Persistencia com Spring Data JPA e Oracle.
- DTOs implementados como Java Records.
- Validacao rigorosa dos dados de entrada.
- Tratamento global de excecoes.
- HATEOAS em endpoints dedicados de navegacao.
- Swagger/OpenAPI configurado.
- JWT implementado com Spring Security.
- Banco com tabelas prefixadas por `AT_`.

## Status dos links externos

| Item | Status |
| --- | --- |
| Deploy | Pendente inserir link |
| Video | Pendente inserir link |
| GitHub | Pendente inserir link |

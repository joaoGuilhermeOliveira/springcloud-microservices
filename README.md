# Microsserviços com Spring Cloud

Este projeto é uma implementação de microsserviços usando Spring Cloud, desenvolvido como parte de um projeto de estudo. A arquitetura busca construir um sistema escalável, modular e distribuído para serviços empresariais, integrando tecnologias como Eureka, Cloud Gateway e Keycloak para autenticação e descoberta de serviços.


## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
- **Spring Cloud**
- **Eureka**
- **Spring Security com Keycloak**
- **RabbitMQ**
- **Docker**

## Estrutura do Projeto

O projeto é composto pelos seguintes microsserviços:

1. **msclientes**: Gerencia o cadastro e as informações dos clientes.
2. **msavaliadorcredito**: Realiza avaliações de crédito e consultas de situação de cliente.
3. **mscartoes**: Gerencia a emissão e informações dos cartões.
4. **mseureka**: Serviço de descoberta de microsserviços.
5. **mscloudgateway**: Roteia as requisições para os microsserviços apropriados.
6. **Keycloak**: Sistema de gerenciamento de identidade e acesso.
7. **RabbitMQ**: Sistema de mensageria para comunicação assíncrona entre os serviços.

## Endpoints dos Microsserviços

### msclientes (`/clientes`)

- **POST `/clientes`**: Cria um novo cliente.
- **GET `/clientes?cpf={cpf}`**: Busca dados do cliente pelo CPF.

### msavaliadorcredito (`/avaliacoes-credito`)

- **GET `/avaliacoes-credito/situacao-cliente?cpf={cpf}`**: Consulta a situação de crédito do cliente pelo CPF.
- **POST `/avaliacoes-credito`**: Realiza uma avaliação de crédito para o cliente.
- **POST `/avaliacoes-credito/solicitacoes-cartao`**: Solicita emissão de um novo cartão para o cliente.

### mscartoes (`/cartoes`)

- **POST `/cartoes`**: Cadastra um novo cartão.
- **GET `/cartoes?renda={renda}`**: Retorna cartões com limite de renda menor ou igual ao especificado.
- **GET `/cartoes?cpf={cpf}`**: Retorna cartões associados ao cliente pelo CPF.

## Como Executar

### 1. Build dos Serviços

Para realizar o build do projeto, execute o seguinte comando na raiz do projeto:

```bash
./mvnw clean package
```

### 2. Gerando as Imagens Docker

Para construir as imagens Docker do Eureka Server, use o seguinte comando na raiz do microsserviço:

```bash
docker build -t ms-eureka .
```

### 3. Criando a Rede Docker

Antes de iniciar os serviços, crie uma rede Docker chamada `ms-network`:

```bash
docker network create ms-network
```

### 4. Executando os Microsserviços

Use os seguintes comandos para iniciar os serviços que estão em contêineres Docker:

```bash
# Inicie o RabbitMQ
docker run --name rabbitmq -p 5672:5672 -p 15672:15672 --network ms-network rabbitmq:4.0-management

# Inicie o Eureka Server
docker run --name ms-eureka -p 8761:8761 --network ms-network ms-eureka

# Inicie o Keycloak
docker run --name keycloak -p 8081:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin --network ms-network quay.io/keycloak/keycloak:26.0.2 start-dev
```

### 5. Acesso aos Serviços

- **Eureka Server**: `http://localhost:8761`
- **Keycloak Admin Console**: `http://localhost:8081`
- **RabbitMQ Management Console**: `http://localhost:15672` (usuário: `guest`, senha: `guest`)

## Importando o Realm no Keycloak

Para configurar o Keycloak corretamente, siga estas etapas:

1. **Acesse o Console do Keycloak**:
   - Abra seu navegador e vá para `http://localhost:8081`.
   - Faça login com suas credenciais de administrador.

2. **Navegue para a Tela de Importação**:
   - No menu à esquerda, clique em **Realms**.
   - Clique em **Add realm** ou no ícone de menu (três pontos verticais) e selecione **Import**.

3. **Importar o Arquivo JSON**:
   - Selecione o arquivo JSON do `realm` que você baixou (por exemplo, `realm-export.json`).
   - Clique em **Import**.

4. **Verifique as Configurações**:
   - Após a importação, verifique se o `realm` foi criado corretamente.
   - Você deve ver o novo `realm` na lista e as configurações devem estar disponíveis.



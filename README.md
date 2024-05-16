# Lafepe

Back end do projeto Lafepe para cadastro e controle de estoque de produtos e medicamentos.

## Requisitos

1. Git
2. Java JDK 17
3. MySQL Server (>= v8.3)
4. MySQL Workbench
5. Docker (opcional)

## Instalação

Clone o repositório do projeto para a sua máquina local

```
git clone https://github.com/renanleitev/lafepe-backend
```

Crie um banco de dados (database) usando o MySQL Workbench com o nome `lafepe`

Vá até o diretório do projeto e rode/execute o arquivo `LafepeApplication` OU rode/execute com o profile `production`:

```
java -Dspring.profiles.active=production -jar ./target/lafepe-0.0.1-SNAPSHOT.jar
```

Acesse do seu navegador o seguinte endereço:

```
http://localhost:8080/
```

## Swagger

Para acessar informações sobre a API, acesse o seguinte endereço do seu navegador:

```
http://localhost:8080/swagger-ui/index.html
```

## Docker

Para fazer o build da imagem docker, rode/execute o comando em seu terminal:

```
docker build -t lafepe:v1 .
```

Para rodar o container docker, rode/execute o comando em seu terminal:

```
docker run -p 8080:80 lafepe:v1
```

Acesse do seu navegador o seguinte endereço:

```
http://localhost:8080/
```

## Equipe

1. Flávio Raposo
2. José Adeilton
3. João Pedro Marinho
4. Renan Leite Vieira
5. Rian Vinicius
6. Robério José

## Referências

* [Maven docs](https://maven.apache.org/guides/index.html)  
* [Spring Boot reference](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)  
* [Spring Data JPA reference](https://docs.spring.io/spring-data/jpa/reference/jpa.html)

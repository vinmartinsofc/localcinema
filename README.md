# MovieCatalog

> ⚠️ **Projeto de estudo, não mantido ativamente.** Foi construído majoritariamente com apoio de IA (Claude) como forma de explorar Spring Boot, JPA e integração com API externa. Não há planos de continuidade.

Catálogo de filmes e séries que integra com a **TMDB (The Movie Database)** para buscar títulos e armazená-los localmente em cache, além de permitir registrar sessões de visualização (o que foi assistido e quando).

## Funcionalidades

- Busca de filmes e séries direto na TMDB
- Cache local dos resultados (salva/atualiza no banco ao buscar)
- Busca local por nome nos títulos já cacheados
- Registro de sessões de visualização (`WatchEntry`) por filme ou série, com data e comentário
- Histórico de visualizações por título e contagem de quantas vezes foi assistido
- Listagem de todas as visualizações de séries

## Tecnologias

- Java 17+
- Spring Boot
- Spring Data JPA
- Spring `RestClient` (integração HTTP com a TMDB)
- Jackson (records como DTOs)
- Banco relacional (via JPA/Hibernate)

## Estrutura do projeto

```
com.localcinema.moviecatalog
├── model        # Title (mapped superclass), Movie, Series, WatchEntry, TitleType
├── repository   # MovieRepository, SeriesRepository, WatchEntryRepository (Spring Data JPA)
├── service      # MovieService, SeriesService, WatchEntryService, TmdbClient
└── dto          # MovieDto, SeriesDto, MovieSearchResponse, SeriesSearchResponse
```

## Configuração

O projeto precisa de uma API key da TMDB (gratuita, via [themoviedb.org](https://www.themoviedb.org/)).

No `application.properties` (ou `application.yml`):

```properties
tmdb.base-url=https://api.themoviedb.org/3
tmdb.api.key=SUA_API_KEY_AQUI

spring.datasource.url=jdbc:postgresql://localhost:5432/moviecatalog
spring.datasource.username=postgres
spring.datasource.password=postgres

spring.jpa.hibernate.ddl-auto=update
```

> Recomendado usar variáveis de ambiente ou um `application-local.properties` fora do controle de versão para não commitar a API key.


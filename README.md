<<<<<<< HEAD
# Sistema Acadêmico - Estrutura Inicial

Primeira versão da aplicação Spring Boot para o dashboard acadêmico com integração MySQL. Esta entrega cobre somente a estrutura Maven + HTML/CSS para que o restante da equipe possa evoluir facilmente.

## Como está organizado

- `pom.xml` &rarr; dependências Spring Boot, JDBC e MySQL.
- `src/main/java/com/sistemaacademico` &rarr; código Java principal.
  - `SistemaAcademicoApplication.java` &rarr; classe principal do Spring Boot.
  - `controller` &rarr; `DashboardController` serve a página inicial.
  - `service`, `repository`, `model`, `dto` &rarr; diretórios preparados para implementar regras de negócio, SQL explícito (NamedParameterJdbcTemplate), entidades e objetos de transferência.
- `src/main/resources/templates/index.html` &rarr; primeira versão do dashboard para edição no Figma/CSS.
- `src/main/resources/static/css/style.css` & `static/js/dashboard.js` &rarr; assets front-end.
- `src/main/resources/application.properties` &rarr; configure aqui o acesso ao banco MySQL.

## Proximos passos sugeridos

1. Implementar os comandos de inserção, atualização e deleção para pelo menos duas tabelas (ex.: alunos e disciplinas) em `ConsultaService` e `ConsultaRepository`.
2. Mapear entidades reais em `model` e DTOs específicos para cada consulta.
3. Construir as quatro consultas SQL exigidas pela etapa 03, incluindo pelo menos uma com `JOIN`.
4. Preencher `dashboard.js` com chamadas AJAX para os endpoints REST e atualizar o HTML com a renderização dos resultados/gráficos.

## Executando o projeto

1. Configure as credenciais do MySQL em `application.properties`.
2. Certifique-se de ter o Maven 3.9+ e o JDK 17 instalados.
3. No terminal:

```powershell
mvn spring-boot:run
```

A aplicação iniciará em `http://localhost:8080`.
=======
# sistema-academico
>>>>>>> a37872546a8204920e906ae4b72e8f5c30ebaf4d

# Sistema Acadêmico

Sistema de gestão acadêmica desenvolvido com **Java Spring Boot** e **JDBC puro** para integração com **MySQL**.

## 🚀 Características

- ✅ **JDBC Puro** - Sem JPA/Hibernate, usando apenas JDBC Template
- ✅ **MySQL** - Banco de dados MySQL com comandos SQL explícitos
- ✅ **Spring Boot** - Framework Java moderno
- ✅ **REST API** - Endpoints completos para CRUD
- ✅ **Dashboard** - Interface web com estatísticas em tempo real
- ✅ **Validação** - Validação de dados no backend e frontend

## 📋 Pré-requisitos

- Java 17+
- MySQL 8.0+
- Maven 3.6+

## 🛠️ Configuração

### 1. Banco de Dados

```sql
CREATE DATABASE sistema_academico;
```

### 2. Configuração do Banco

Edite o arquivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/sistema_academico?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

### 3. Executar o Projeto

```bash
# Compilar
mvn clean compile

# Executar
mvn spring-boot:run
```

## 🌐 Endpoints da API

### Criação de Tabelas
- `GET /api/criar-tabelas` - Cria todas as tabelas do sistema

### Alunos
- `GET /api/alunos` - Lista todos os alunos
- `GET /api/alunos/{id}` - Busca aluno por ID
- `POST /api/alunos` - Cria novo aluno
- `PUT /api/alunos/{id}` - Atualiza aluno
- `DELETE /api/alunos/{id}` - Remove aluno

### Disciplinas
- `GET /api/disciplinas` - Lista todas as disciplinas
- `GET /api/disciplinas/{id}` - Busca disciplina por ID
- `POST /api/disciplinas` - Cria nova disciplina
- `PUT /api/disciplinas/{id}` - Atualiza disciplina
- `DELETE /api/disciplinas/{id}` - Remove disciplina

### Dashboard
- `GET /api/dashboard/estatisticas` - Estatísticas gerais
- `GET /api/dashboard/distribuicao-sexo` - Distribuição por sexo
- `GET /api/dashboard/distribuicao-idade` - Distribuição por idade
- `GET /api/dashboard/top-disciplinas` - Top disciplinas
- `GET /api/dashboard/top-alunos` - Top alunos

## 🎯 Como Usar

1. **Iniciar o sistema**: Acesse `http://localhost:8080`
2. **Criar tabelas**: Acesse `http://localhost:8080/api/criar-tabelas`
3. **Usar o dashboard**: Interface web completa em `http://localhost:8080`

## 📊 Estrutura do Banco

### Tabelas Principais
- **Aluno** - Dados dos estudantes
- **Disciplina** - Matérias do curso
- **Professor** - Docentes
- **Avaliacao** - Notas dos alunos
- **Matricula** - Matrículas
- **Pagamento** - Pagamentos

### Relacionamentos
- Aluno → Disciplina (Muitos para Muitos)
- Aluno → Avaliacao (Um para Muitos)
- Professor → Disciplina (Um para Muitos)

## 🔧 Tecnologias Utilizadas

- **Backend**: Java 17, Spring Boot 3.3.4
- **Banco**: MySQL 8.0, JDBC puro
- **Frontend**: HTML5, CSS3, JavaScript (Vanilla)
- **Template**: Thymeleaf
- **Build**: Maven

## 📝 Comandos SQL Explícitos

O sistema utiliza comandos SQL explícitos em todas as operações:

```java
// Exemplo de consulta
String sql = "SELECT * FROM Aluno WHERE Id_Aluno = ?";
return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Aluno.class), id);

// Exemplo de inserção
String sql = "INSERT INTO Aluno (Nome, Sexo, Idade) VALUES (?, ?, ?)";
return jdbcTemplate.update(sql, aluno.getNome(), aluno.getSexo(), aluno.getIdade());
```

## 🚨 Validações

- **Alunos**: Nome obrigatório, idade mínima 16 anos, sexo M/F
- **Disciplinas**: Nome obrigatório, carga horária entre 20-120 horas
- **Notas**: Valores entre 0-10
- **Frequência**: Valores entre 0-100%

## 📈 Dashboard

O dashboard exibe:
- Total de alunos e disciplinas
- Média geral de notas
- Taxa de aprovação
- Distribuição por sexo e idade
- Top alunos e disciplinas
- Alunos com frequência baixa

## 🔍 Troubleshooting

### Erro de Conexão
1. Verifique se o MySQL está rodando
2. Confirme as credenciais no `application.properties`
3. Execute `/api/criar-tabelas` para criar as tabelas

### Erro de Compilação
1. Verifique se o Java 17 está instalado
2. Execute `mvn clean compile`

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.
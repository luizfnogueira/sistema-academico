# Sistema Acadêmico

## 🚀 Características

- Sistema de gestão acadêmica completo
- Backend em Java Spring Boot com JDBC puro
- Banco de dados MySQL
- Interface web (dashboard) com estatísticas e gráficos
- CRUD para Alunos, Professores, Disciplinas, Avaliações
- Relatórios e consultas avançadas

## 🛠️ Configuração

### 1. Requisitos
- **Java 17+**
- **MySQL 8.0+**
- **Maven 3.6+**

### 2. Banco de Dados
- Crie o banco de dados no MySQL:
  ```sql
  CREATE DATABASE sistema_academico;
  ```
- Execute o script `scripts/inicializar_banco.sql` para criar todas as tabelas, funções, procedimentos e triggers.

### 3. Configuração do Projeto
- Edite o arquivo `src/main/resources/application.properties` com seus dados do MySQL:
  ```properties
  spring.datasource.url=jdbc:mysql://localhost:3306/sistema_academico?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
  spring.datasource.username=SEU_USUARIO
  spring.datasource.password=SUA_SENHA
  spring.jpa.hibernate.ddl-auto=none
  spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
  ```

## 🎯 Como Usar

### 1. Instale as dependências
- Certifique-se de ter o **Java** e o **Maven** instalados.
- Instale o **MySQL** e crie o banco conforme acima.

### 2. Compile e rode o projeto
```bash
mvn clean compile
mvn spring-boot:run
```

### 3. Acesse o dashboard
- Abra o navegador e acesse: [http://localhost:8080](http://localhost:8080)

### 4. Utilização na IDE
- Importe o projeto como **Maven Project** na sua IDE (IntelliJ, Eclipse, VS Code, etc).
- Execute a classe principal: `SistemaAcademicoApplication.java`.

### 5. Bibliotecas necessárias
- Todas as dependências estão no `pom.xml` (Spring Boot, MySQL Connector).
- O Maven baixa tudo automaticamente.

### 6. Configuração básica do `application.properties`
- Veja exemplo acima. Basta ajustar usuário e senha do MySQL.

## 📊 Estrutura do Banco

- Tabelas principais: `Aluno`, `Professor`, `Disciplina`, `Avaliacao`, `Matricula`, `Conselho`, etc.
- Funções e procedimentos para cálculos, atualizações e relatórios.
- Triggers para atualização automática de médias e logs.
- Veja o script completo em `scripts/inicializar_banco.sql`.

## 📈 Dashboard

- Interface web para CRUD e consultas
- Gráficos dinâmicos: Média vs Estudo, Estresse vs Projetos, Distribuição por Gênero, etc.
- Relatórios e navegação por professor/aluno
- Atualização em tempo real após operações

---

**Dúvidas ou problemas?**
- Consulte o código, os endpoints REST e o script SQL para referência.
- Para rodar do zero: configure o banco, ajuste o `application.properties`, execute o script SQL, rode o projeto com Maven e acesse o dashboard.
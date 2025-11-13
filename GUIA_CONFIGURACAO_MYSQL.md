# 🔧 Guia de Configuração MySQL - Sistema Acadêmico

## ⚠️ PROBLEMA IDENTIFICADO

Você tem configuração duplicada! O projeto está configurando o banco em **DOIS lugares**:
1. `application.properties` 
2. `DatabaseConfig.java`

Isso pode causar conflitos. Vamos usar apenas o `application.properties` e remover a configuração duplicada.

---

## 📋 PASSO A PASSO PARA CONFIGURAR

### 1️⃣ **Verificar Configurações do MySQL Workbench**

Na tela que você mostrou (MySQL Workbench), verifique:

- **Hostname:** `localhost` ✅ (está correto)
- **Port:** `3306` ✅ (está correto)
- **Username:** `root` ✅ (está correto)
- **Password:** Confirme se é `Lf310805@` (a mesma do application.properties)

### 2️⃣ **Testar Conexão no MySQL Workbench**

1. Clique em **"Test Connection"** no MySQL Workbench
2. Se conectar com sucesso, você verá uma mensagem verde
3. Se der erro, verifique:
   - MySQL está rodando? (Windows: Services → MySQL80)
   - Senha está correta?
   - Porta 3306 está livre?

### 3️⃣ **Criar o Banco de Dados**

No MySQL Workbench, execute este comando:

```sql
CREATE DATABASE IF NOT EXISTS sistema_academico;
USE sistema_academico;
```

Ou execute o script completo que está em `scripts/inicializar_banco.sql`

### 4️⃣ **Executar Script SQL Completo**

**Opção A - Via MySQL Workbench:**
1. Abra o arquivo `scripts/inicializar_banco.sql`
2. Copie todo o conteúdo
3. Cole no MySQL Workbench
4. Execute (Ctrl+Shift+Enter ou botão ⚡)

**Opção B - Via Linha de Comando:**
```bash
mysql -u root -p < scripts/inicializar_banco.sql
```

### 5️⃣ **Verificar application.properties**

O arquivo `src/main/resources/application.properties` deve estar assim:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/sistema_academico?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=Lf310805@
```

**⚠️ IMPORTANTE:** Se sua senha do MySQL for diferente, altere aqui também!

### 6️⃣ **Remover Configuração Duplicada**

O arquivo `DatabaseConfig.java` está duplicando a configuração. Vamos removê-la para evitar conflitos.

---

## 🔍 VERIFICAÇÕES FINAIS

### Teste 1: Verificar se o banco existe
```sql
SHOW DATABASES;
```
Deve aparecer `sistema_academico` na lista.

### Teste 2: Verificar se as tabelas foram criadas
```sql
USE sistema_academico;
SHOW TABLES;
```
Deve mostrar todas as tabelas (Aluno, Professor, Disciplina, etc.)

### Teste 3: Testar conexão do Spring Boot

1. Execute o projeto:
```bash
mvn spring-boot:run
```

2. Se der erro de conexão, verifique:
   - MySQL está rodando?
   - Banco `sistema_academico` existe?
   - Usuário e senha estão corretos?

---

## 🚨 ERROS COMUNS E SOLUÇÕES

### Erro: "Access denied for user 'root'@'localhost'"
**Solução:** Verifique a senha no `application.properties`

### Erro: "Unknown database 'sistema_academico'"
**Solução:** Execute `CREATE DATABASE sistema_academico;` no MySQL

### Erro: "Connection refused"
**Solução:** 
- Verifique se MySQL está rodando
- Verifique se a porta 3306 está correta
- No Windows: Services → MySQL80 → Start

### Erro: "Public Key Retrieval is not allowed"
**Solução:** Já está resolvido no `application.properties` com `allowPublicKeyRetrieval=true`

---

## ✅ CHECKLIST FINAL

- [ ] MySQL está rodando
- [ ] Conexão testada no MySQL Workbench com sucesso
- [ ] Banco `sistema_academico` foi criado
- [ ] Script SQL foi executado (todas as tabelas criadas)
- [ ] `application.properties` tem usuário e senha corretos
- [ ] Projeto Spring Boot inicia sem erros de conexão

---

## 📝 PRÓXIMOS PASSOS

Depois de configurar:

1. **Inserir dados de exemplo:**
   - Execute os comandos INSERT que você forneceu anteriormente
   - Ou use o endpoint `/api/criar-tabelas` do dashboard

2. **Inicializar estruturas avançadas:**
   - Acesse `http://localhost:8080`
   - Clique em "Inicializar Estruturas Avançadas"

3. **Testar funcionalidades:**
   - Use o dashboard para testar as consultas SQL
   - Teste as views, funções e procedimentos



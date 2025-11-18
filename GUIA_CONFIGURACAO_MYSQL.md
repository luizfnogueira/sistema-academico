# 🔧 Guia de Configuração MySQL - Sistema Acadêmico

## ⚠️ IMPORTANTE

O projeto está configurado para usar o banco de dados MySQL via `application.properties`. Não é necessário alterar o arquivo `DatabaseConfig.java`, pois ele apenas registra o `JdbcTemplate` como bean e não contém dados sensíveis ou duplicados.

---

## 📋 PASSO A PASSO PARA CONFIGURAR

### 1️⃣ **Verificar Configurações do MySQL Workbench**

Na tela do MySQL Workbench, verifique:

- **Hostname:** `localhost` ✅
- **Port:** `3306` ✅
- **Username:** `root` ✅
- **Password:** Confirme se é a mesma do `application.properties`

### 2️⃣ **Testar Conexão no MySQL Workbench**

1. Clique em **"Test Connection"**
2. Se conectar com sucesso, você verá uma mensagem verde
3. Se der erro, verifique:
   - MySQL está rodando? (Windows: Services → MySQL80)
   - Senha está correta?
   - Porta 3306 está livre?

### 3️⃣ **Criar o Banco de Dados**

No MySQL Workbench, execute:

```sql
CREATE DATABASE IF NOT EXISTS sistema_academico;
USE sistema_academico;
```

Ou execute o script completo em `scripts/inicializar_banco.sql`

### 4️⃣ **Executar Script SQL Completo**

**Opção A - Via MySQL Workbench:**
1. Abra o arquivo `scripts/inicializar_banco.sql`
2. Copie todo o conteúdo
3. Cole no MySQL Workbench
4. Execute (Ctrl+Shift+Enter ou botão ⚡)

---

### 5️⃣ **Configurar o Spring Boot**

No arquivo `src/main/resources/application.properties`, confirme:

```
spring.datasource.url=jdbc:mysql://localhost:3306/sistema_academico
spring.datasource.username=root
spring.datasource.password=SuaSenhaAqui
```

**Pronto!** O sistema está preparado para rodar com MySQL.



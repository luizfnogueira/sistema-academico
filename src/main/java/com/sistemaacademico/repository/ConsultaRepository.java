package com.sistemaacademico.repository;

import org.springframework.stereotype.Repository;
import com.sistemaacademico.model.Aluno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;

@Repository
public class ConsultaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    // ...existing code...

    // Criar tabela Matricula
    public void criarTabelaMatricula() {
        String sql = "CREATE TABLE IF NOT EXISTS Matricula (" +
                "Id_Matricula INT PRIMARY KEY AUTO_INCREMENT," +
                "Data DATE," +
                "Id_Aluno INT," +
                "FOREIGN KEY (Id_Aluno) REFERENCES Aluno(Id_Aluno) ON DELETE SET NULL" +
                ")";
        jdbcTemplate.execute(sql);
    }

    // Criar tabela Disciplina
    public void criarTabelaDisciplina() {
        String sql = "CREATE TABLE IF NOT EXISTS Disciplina (" +
                "Id_Disc INT PRIMARY KEY AUTO_INCREMENT," +
                "Nome VARCHAR(255) NOT NULL," +
                "Carga_Horaria INT DEFAULT 40 CHECK (Carga_Horaria BETWEEN 20 AND 120)," +
                "Id_Aluno INT," +
                "Id_Turma INT," +
                "FOREIGN KEY (Id_Aluno) REFERENCES Aluno(Id_Aluno) ON DELETE SET NULL," +
                "FOREIGN KEY (Id_Turma) REFERENCES Turma(Id_Turma) ON DELETE SET NULL" +
                ")";
        jdbcTemplate.execute(sql);
    }

    // Criar tabela Avaliacao
    public void criarTabelaAvaliacao() {
        String sql = "CREATE TABLE IF NOT EXISTS Avaliacao (" +
                "Id_Avalia INT PRIMARY KEY AUTO_INCREMENT," +
                "Valor DECIMAL(4, 2) CHECK (Valor >= 0 AND Valor <= 10)," +
                "Data DATE," +
                "Id_Aluno INT," +
                "Id_Disc INT," +
                "FOREIGN KEY (Id_Aluno) REFERENCES Aluno(Id_Aluno) ON DELETE SET NULL," +
                "FOREIGN KEY (Id_Disc) REFERENCES Disciplina(Id_Disc) ON DELETE SET NULL" +
                ")";
        jdbcTemplate.execute(sql);
    }

    // Criar tabela Pagamento
    public void criarTabelaPagamento() {
        String sql = "CREATE TABLE IF NOT EXISTS Pagamento (" +
                "Id_Pagamento INT PRIMARY KEY AUTO_INCREMENT," +
                "Status VARCHAR(50) DEFAULT 'Pendente' CHECK (Status IN ('Pendente','Pago','Atrasado'))," +
                "Valor DECIMAL(10, 2) CHECK (Valor >= 0)," +
                "Data DATE," +
                "Id_Aluno INT," +
                "FOREIGN KEY (Id_Aluno) REFERENCES Aluno(Id_Aluno) ON DELETE SET NULL" +
                ")";
        jdbcTemplate.execute(sql);
    }

    // Criar tabela Proj_Extensao
    public void criarTabelaProjExtensao() {
        String sql = "CREATE TABLE IF NOT EXISTS Proj_Extensao (" +
                "Id_Proj INT PRIMARY KEY AUTO_INCREMENT," +
                "Nome VARCHAR(255)," +
                "Descricao VARCHAR(255)," +
                "Id_Prof INT," +
                "FOREIGN KEY (Id_Prof) REFERENCES Professor(Id_Prof) ON DELETE SET NULL" +
                ")";
        jdbcTemplate.execute(sql);
    }

    // Criar tabela Conselho
    public void criarTabelaConselho() {
        String sql = "CREATE TABLE IF NOT EXISTS Conselho (" +
                "Id_Conselho INT PRIMARY KEY AUTO_INCREMENT," +
                "Descricao VARCHAR(255)," +
                "Data DATE," +
                "Id_Prof INT," +
                "FOREIGN KEY (Id_Prof) REFERENCES Professor(Id_Prof) ON DELETE SET NULL" +
                ")";
        jdbcTemplate.execute(sql);
    }

    // Criar tabela Telefone
    public void criarTabelaTelefone() {
        String sql = "CREATE TABLE IF NOT EXISTS Telefone (" +
                "Numero VARCHAR(20)," +
                "Id_Aluno INT," +
                "Id_Prof INT," +
                "PRIMARY KEY (Numero)," +
                "FOREIGN KEY (Id_Aluno) REFERENCES Aluno(Id_Aluno) ON DELETE CASCADE," +
                "FOREIGN KEY (Id_Prof) REFERENCES Professor(Id_Prof) ON DELETE CASCADE" +
                ")";
        jdbcTemplate.execute(sql);
    }

    // Criar tabela Email
    public void criarTabelaEmail() {
        String sql = "CREATE TABLE IF NOT EXISTS Email (" +
                "Email VARCHAR(255) PRIMARY KEY," +
                "Id_Aluno INT," +
                "Id_Prof INT," +
                "FOREIGN KEY (Id_Aluno) REFERENCES Aluno(Id_Aluno) ON DELETE CASCADE," +
                "FOREIGN KEY (Id_Prof) REFERENCES Professor(Id_Prof) ON DELETE CASCADE" +
                ")";
        jdbcTemplate.execute(sql);
    }
    // Criar tabela Aluno
    public void criarTabelaAluno() {
        String sql = "CREATE TABLE IF NOT EXISTS Aluno (" +
                "Id_Aluno INT PRIMARY KEY AUTO_INCREMENT," +
                "Nome VARCHAR(255) NOT NULL," +
                "Sexo CHAR(1) CHECK (Sexo IN ('M','F'))," +
                "Idade INT CHECK (Idade >= 16)," +
                "Num INT," +
                "CEP VARCHAR(9)," +
                "Rua VARCHAR(255)," +
                "Media DECIMAL(4,2)," +
                "Frequencia DECIMAL(5,2) DEFAULT 100 CHECK (Frequencia BETWEEN 0 AND 100)," +
                "Monitor INT NULL," +
                "Monitorado INT NULL," +
                "FOREIGN KEY (Monitor) REFERENCES Aluno(Id_Aluno) ON DELETE SET NULL ON UPDATE CASCADE," +
                "FOREIGN KEY (Monitorado) REFERENCES Aluno(Id_Aluno) ON DELETE CASCADE ON UPDATE CASCADE" +
                ")";
        jdbcTemplate.execute(sql);
    }

    // Criar tabela Pesquisa
    public void criarTabelaPesquisa() {
        String sql = "CREATE TABLE IF NOT EXISTS Pesquisa (" +
                "Id_Pesquisa INT PRIMARY KEY AUTO_INCREMENT," +
                "Freq_Recurso INT DEFAULT 0," +
                "Nvl_Estresse VARCHAR(255) CHECK (Nvl_Estresse IN ('Nenhum','Baixo','Moderado','Alto','Muito alto'))," +
                "Qtd_Disciplinas INT CHECK (Qtd_Disciplinas >= 1)," +
                "Freq_Estudo INT," +
                "Id_Aluno INT," +
                "FOREIGN KEY (Id_Aluno) REFERENCES Aluno(Id_Aluno) ON DELETE SET NULL" +
                ")";
        jdbcTemplate.execute(sql);
    }

    // Criar tabela Professor
    public void criarTabelaProfessor() {
        String sql = "CREATE TABLE IF NOT EXISTS Professor (" +
                "Id_Prof INT PRIMARY KEY AUTO_INCREMENT," +
                "CPF VARCHAR(14) UNIQUE," +
                "Nome VARCHAR(255) NOT NULL," +
                "Rua VARCHAR(255)," +
                "Num INT," +
                "CEP VARCHAR(9)" +
                ")";
        jdbcTemplate.execute(sql);
    }

    // Criar tabela Efetivado
    public void criarTabelaEfetivado() {
        String sql = "CREATE TABLE IF NOT EXISTS Efetivado (" +
                "Id_Prof INT PRIMARY KEY," +
                "Salario DECIMAL(10, 2) NOT NULL," +
                "Data_Concurso DATE," +
                "Regime VARCHAR(50)," +
                "FOREIGN KEY (Id_Prof) REFERENCES Professor(Id_Prof) ON UPDATE CASCADE ON DELETE CASCADE" +
                ")";
        jdbcTemplate.execute(sql);
    }

    // Criar tabela Temporario
    public void criarTabelaTemporario() {
        String sql = "CREATE TABLE IF NOT EXISTS Temporario (" +
                "Id_Prof INT PRIMARY KEY," +
                "Remuneracao DECIMAL(10, 2)," +
                "Inicio DATE," +
                "Fim DATE," +
                "FOREIGN KEY (Id_Prof) REFERENCES Professor(Id_Prof) ON UPDATE CASCADE ON DELETE CASCADE" +
                ")";
        jdbcTemplate.execute(sql);
    }

    // Buscar todos os alunos
    public List<Aluno> listarTodos() {
        String sql = "SELECT * FROM Aluno";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Aluno.class));
    }

    // Buscar por ID
    public Aluno buscarPorId(int id) {
        String sql = "SELECT * FROM Aluno WHERE Id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Aluno.class), id);
    }

    // Deletar por ID
    public void deletar(int id) {
        String sql = "DELETE FROM Aluno WHERE Id = ?";
        jdbcTemplate.update(sql, id);
    }

    // TODO: escrever consultas SQL explícitas conforme exigido pelo projeto
}

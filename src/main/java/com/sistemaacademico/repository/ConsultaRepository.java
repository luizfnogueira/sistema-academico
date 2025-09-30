package com.sistemaacademico.repository;

import org.springframework.stereotype.Repository;
import com.sistemaacademico.model.Aluno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

@Repository
public class ConsultaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String URL = "jdbc:mysql://localhost:3306/sistema_academico?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "Lf310805@";

    // Criar tabela (se precisar criar via código)
    public void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS Aluno (" +
                     "Id INT AUTO_INCREMENT PRIMARY KEY," +
                     "Nome VARCHAR(100) NOT NULL," +
                     "Idade INT," +
                     "Sexo CHAR(1)," +
                     "Media DECIMAL(4,2)," +
                     "Frequencia DECIMAL(5,2)," +
                     "Rua VARCHAR(150)," +
                     "Num INT," +
                     "CEP VARCHAR(15)," +
                     "Monitor VARCHAR(100)," +
                     "Monitorado VARCHAR(100)" +
                     ")";
        jdbcTemplate.execute(sql);
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement stmt = conn.createStatement()) {
               stmt.execute(sql);
               System.out.println("Tabela criada/verificada com sucesso!");
           } catch (Exception e) {
               e.printStackTrace();
           }
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

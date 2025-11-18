package com.sistemaacademico.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class SqlAvancadoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    
    public void criarIndices() {
        try {
            jdbcTemplate.execute("CREATE INDEX IF NOT EXISTS idx_aluno_nome ON Aluno (Nome)");
            jdbcTemplate.execute("CREATE INDEX IF NOT EXISTS idx_aluno_media ON Aluno (Media)");
        } catch (Exception e) {
        }
    }


    public List<Map<String, Object>> buscarProfessoresSemOfertas() {
        String sql = """
            SELECT p.Id_Prof, p.Nome
            FROM Professor p
            LEFT JOIN Oferta o ON p.Id_Prof = o.Id_Prof
            WHERE o.Id_Prof IS NULL
        """;
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> buscarAlunosEProfessoresComEmails() {
        String sql = """
            SELECT 
                a.Nome AS Nome_Pessoa, 
                a.Sexo, 
                e.Email,
                'Aluno' AS Tipo_Usuario
            FROM Aluno a
            LEFT JOIN Email e ON a.Id_Aluno = e.Id_Aluno
            UNION
            SELECT 
                p.Nome AS Nome_Pessoa, 
                NULL AS Sexo,
                e.Email,
                'Professor' AS Tipo_Usuario
            FROM Professor p
            LEFT JOIN Email e ON p.Id_Prof = e.Id_Prof
        """;
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> buscarAlunosAcimaDaMedia() {
        String sql = """
            SELECT Nome, Media
            FROM Aluno
            WHERE Media > (
                SELECT AVG(Media) 
                FROM Aluno
            )
        """;
        return jdbcTemplate.queryForList(sql);
    }


    public List<Map<String, Object>> buscarAlunosTurmas2023() {
        String sql = """
            SELECT *
            FROM Aluno
            WHERE Id_Aluno IN (
                SELECT Id_Aluno
                FROM Matricula
                WHERE Id_Turma IN (
                    SELECT Id_Turma
                    FROM Turma
                    WHERE Ano = 2023
                )
            )
        """;
        return jdbcTemplate.queryForList(sql);
    }

    public void criarViewDetalhesAcademicosAluno() {
        String sql = """
            CREATE OR REPLACE VIEW vw_DetalhesAcademicosAluno AS
            SELECT 
                a.Nome AS Nome_Aluno,
                a.Media AS Media_Geral,
                a.Sexo AS Sexo_Aluno,
                t.Nome AS Nome_Turma,
                t.Ano AS Ano_Turma,
                COALESCE(GROUP_CONCAT(DISTINCT tel.Numero SEPARATOR ', '), 'Sem telefone') AS Telefone_Aluno
            FROM Aluno a
            JOIN Matricula m ON a.Id_Aluno = m.Id_Aluno
            JOIN Turma t ON m.Id_Turma = t.Id_Turma
            LEFT JOIN Telefone tel ON a.Id_Aluno = tel.Id_Aluno
            GROUP BY a.Id_Aluno, a.Nome, a.Media, a.Sexo, t.Nome, t.Ano
        """;
        jdbcTemplate.execute(sql);
    }

    public List<Map<String, Object>> consultarViewDetalhesAcademicosAluno() {
        String sql = "SELECT * FROM vw_DetalhesAcademicosAluno";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> consultarViewDetalhesAcademicosAlunoPorId(int idAluno) {
        String sql = "SELECT * FROM vw_DetalhesAcademicosAluno WHERE Nome_Aluno IN (SELECT Nome FROM Aluno WHERE Id_Aluno = ?)";
        return jdbcTemplate.queryForList(sql, idAluno);
    }

    public void criarViewPerfilCompletoProfessor() {
        String sql = """
            CREATE OR REPLACE VIEW vw_PerfilCompletoProfessor AS
            SELECT 
                p.Id_Prof,
                p.Nome AS Nome_Professor,
                COALESCE(e.Regime, 'Temporário') AS Tipo_Contrato,
                d.Nome AS Disciplina_Ministrada,
                c.Descricao AS Conselho_Participante,
                pe.Nome AS Projeto_Liderado
            FROM Professor p
            LEFT JOIN Efetivado e ON p.Id_Prof = e.Id_Prof
            LEFT JOIN Oferta o ON p.Id_Prof = o.Id_Prof
            LEFT JOIN Disciplina d ON o.Id_Disc = d.Id_Disc
            LEFT JOIN Participa pa ON p.Id_Prof = pa.Id_Prof
            LEFT JOIN Conselho c ON pa.Id_Conselho = c.Id_Conselho
            LEFT JOIN Proj_Extensao pe ON p.Id_Prof = pe.Id_Prof
        """;
        jdbcTemplate.execute(sql);
    }

    public List<Map<String, Object>> consultarViewPerfilCompletoProfessor() {
        String sql = "SELECT * FROM vw_PerfilCompletoProfessor";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> consultarViewPerfilCompletoProfessorPorId(int idProf) {
        String sql = "SELECT * FROM vw_PerfilCompletoProfessor WHERE Id_Prof = ?";
        return jdbcTemplate.queryForList(sql, idProf);
    }


    public void criarFuncaoSituacaoAluno() {
        try {
            jdbcTemplate.execute("DROP FUNCTION IF EXISTS situacaoAluno");
        } catch (Exception e) {
           
        }
        
        String sql = """
            CREATE FUNCTION situacaoAluno(idAluno INT)
            RETURNS VARCHAR(50)
            DETERMINISTIC
            BEGIN
                DECLARE media DECIMAL(4,2);
                DECLARE frequencia DECIMAL(5,2);
                DECLARE situacao VARCHAR(20);
                
                SELECT a.Media, a.Frequencia INTO media, frequencia 
                FROM Aluno a
                WHERE a.Id_Aluno = idAluno;
                
                IF media >= 7.0 AND frequencia >= 75 THEN
                    SET situacao = 'Aprovado';
                ELSEIF media >= 4.0 THEN
                    SET situacao = 'Recuperação';
                ELSE
                    SET situacao = 'Reprovado';
                END IF;
                
                RETURN situacao;
            END
        """;
        jdbcTemplate.execute(sql);
    }

    public String chamarFuncaoSituacaoAluno(int idAluno) {
        String sql = "SELECT situacaoAluno(?)";
        return jdbcTemplate.queryForObject(sql, String.class, idAluno);
    }


    
    public void criarProcedimentoUpdateFrequenciaAluno() {
        try {
            jdbcTemplate.execute("DROP PROCEDURE IF EXISTS updateFrequenciaAluno");
        } catch (Exception e) {
            
        }
        
        String sql = """
            CREATE PROCEDURE updateFrequenciaAluno(IN idAluno INT, IN frequenciaNova DECIMAL(5,2))
            BEGIN
                UPDATE Aluno
                SET Frequencia = frequenciaNova
                WHERE Id_Aluno = idAluno;
            END
        """;
        jdbcTemplate.execute(sql);
    }

    public void chamarProcedimentoUpdateFrequenciaAluno(int idAluno, double frequenciaNova) {
        String sql = "CALL updateFrequenciaAluno(?, ?)";
        jdbcTemplate.update(sql, idAluno, frequenciaNova);
    }

    public void criarTabelaResumoConselhos() {
        String sql = """
            CREATE TABLE IF NOT EXISTS Resumo_Conselhos (
                Id_Prof INT PRIMARY KEY,
                Total_Conselhos INT
            )
        """;
        jdbcTemplate.execute(sql);
    }

    public void criarProcedimentoContarConselhosPorProfessor() {
        try {
            jdbcTemplate.execute("DROP PROCEDURE IF EXISTS contarConselhosPorProfessor");
        } catch (Exception e) {
        }
        
        String sql = """
            CREATE PROCEDURE contarConselhosPorProfessor()
            BEGIN
                DECLARE idProf INT;
                DECLARE total INT;
                DECLARE fim INT DEFAULT 0;
                
                DECLARE cur CURSOR FOR SELECT Id_Prof FROM Professor;
                DECLARE CONTINUE HANDLER FOR NOT FOUND SET fim = 1;
                
                OPEN cur;
                
                REPEAT
                    FETCH cur INTO idProf;
                    IF NOT fim THEN
                        SELECT COUNT(*) INTO total FROM Participa
                        WHERE Id_Prof = idProf;
                        
                        INSERT INTO Resumo_Conselhos (Id_Prof, Total_Conselhos)
                        VALUES (idProf, total)
                        ON DUPLICATE KEY UPDATE Total_Conselhos = total;
                    END IF;
                UNTIL fim END REPEAT;
                
                CLOSE cur;
            END
        """;
        jdbcTemplate.execute(sql);
    }

    public void chamarProcedimentoContarConselhosPorProfessor() {
        String sql = "CALL contarConselhosPorProfessor()";
        jdbcTemplate.execute(sql);
    }

    public List<Map<String, Object>> consultarResumoConselhos() {
        String sql = "SELECT * FROM Resumo_Conselhos";
        return jdbcTemplate.queryForList(sql);
    }
    
    public List<Map<String, Object>> consultarConselhosPorProfessor(int idProf) {
        String sql = "SELECT " +
                "c.Id_Conselho, " +
                "c.Descricao, " +
                "c.Data, " +
                "p.Nome AS Nome_Professor " +
                "FROM Conselho c " +
                "JOIN Professor p ON c.Id_Prof = p.Id_Prof " +
                "WHERE c.Id_Prof = ? " +
                "ORDER BY c.Data DESC";
        return jdbcTemplate.queryForList(sql, idProf);
    }
    
    public int criarConselhoEAtribuir(int idProf, String descricao, java.sql.Date data) {
        
        String sqlConselho = "INSERT INTO Conselho (Descricao, Data, Id_Prof) VALUES (?, ?, ?)";
        jdbcTemplate.update(sqlConselho, descricao, data, idProf);
        
        String sqlId = "SELECT LAST_INSERT_ID()";
        Integer idConselho = jdbcTemplate.queryForObject(sqlId, Integer.class);
        
        String sqlParticipa = "INSERT INTO Participa (Id_Prof, Id_Conselho) VALUES (?, ?) ON DUPLICATE KEY UPDATE Id_Prof = Id_Prof";
        return jdbcTemplate.update(sqlParticipa, idProf, idConselho);
    }


    public void criarTabelaLogPagamento() {
        String sql = """
            CREATE TABLE IF NOT EXISTS Log_Pagamento (
                Id_Log INT AUTO_INCREMENT PRIMARY KEY,
                Id_Pagamento INT,
                Status VARCHAR(50),
                Data_Registro DATETIME
            )
        """;
        jdbcTemplate.execute(sql);
    }

    public void criarTriggerLogPagamento() {
        try {
            jdbcTemplate.execute("DROP TRIGGER IF EXISTS logPagamento");
        } catch (Exception e) {
            
        }
        
        String sql = """
            CREATE TRIGGER logPagamento
            AFTER INSERT ON Pagamento
            FOR EACH ROW
            BEGIN
                INSERT INTO Log_Pagamento (Id_Pagamento, Status, Data_Registro)
                VALUES (NEW.Id_Pagamento, NEW.Status, NOW());
            END
        """;
        jdbcTemplate.execute(sql);
    }

    public List<Map<String, Object>> consultarLogPagamento() {
        String sql = """
            SELECT 
                a.Nome AS Nome_Aluno,
                lp.Id_Pagamento,
                lp.Status,
                lp.Data_Registro
            FROM Log_Pagamento lp
            JOIN Pagamento p ON lp.Id_Pagamento = p.Id_Pagamento
            LEFT JOIN Aluno a ON p.Id_Aluno = a.Id_Aluno
            ORDER BY lp.Data_Registro DESC
        """;
        return jdbcTemplate.queryForList(sql);
    }

    public void criarTriggerRecalcularMediaGeralAluno() {
        try {
            jdbcTemplate.execute("DROP TRIGGER IF EXISTS recalcularMediaGeralAluno");
        } catch (Exception e) {
            
        }
        
        String sql = """
            CREATE TRIGGER recalcularMediaGeralAluno
            AFTER INSERT ON Avaliacao
            FOR EACH ROW
            BEGIN
                DECLARE novaMedia DECIMAL(4,2);
                
                SELECT AVG(Valor) INTO novaMedia 
                FROM Avaliacao
                WHERE Id_Aluno = NEW.Id_Aluno;
                
                UPDATE Aluno a
                SET a.Media = novaMedia
                WHERE a.Id_Aluno = NEW.Id_Aluno;
            END
        """;
        jdbcTemplate.execute(sql);
    }
    
    public void criarFuncaoCalcularMediaTurma() {
        try {
            jdbcTemplate.execute("DROP FUNCTION IF EXISTS calcularMediaTurma");
        } catch (Exception e) {
        }
        
        String sql = "CREATE FUNCTION calcularMediaTurma(idTurma INT) " +
                "RETURNS DECIMAL(4,2) " +
                "DETERMINISTIC " +
                "READS SQL DATA " +
                "BEGIN " +
                "DECLARE mediaTurma DECIMAL(4,2); " +
                "SELECT COALESCE(AVG(a.Media), 0) INTO mediaTurma " +
                "FROM Aluno a " +
                "JOIN Matricula m ON a.Id_Aluno = m.Id_Aluno " +
                "WHERE m.Id_Turma = idTurma " +
                "AND a.Media IS NOT NULL " +
                "AND a.Media > 0; " +
                "RETURN mediaTurma; " +
                "END";
        try {
            jdbcTemplate.execute(sql);
        } catch (Exception e) {
            System.err.println("Erro ao criar função calcularMediaTurma: " + e.getMessage());
        }
    }
    
    public Double chamarFuncaoCalcularMediaTurma(int idTurma) {
        String sql = "SELECT COALESCE(AVG(a.Media), 0.0) as media " +
                "FROM Aluno a " +
                "JOIN Matricula m ON a.Id_Aluno = m.Id_Aluno " +
                "WHERE m.Id_Turma = ? AND a.Media IS NOT NULL AND a.Media > 0";
        try {
            Double media = jdbcTemplate.queryForObject(sql, Double.class, idTurma);
            return media != null ? media : 0.0;
        } catch (Exception e) {
            System.err.println("Erro ao calcular média da turma: " + e.getMessage());
            return 0.0;
        }
    }

    
    public void inicializarEstruturasAvancadas() {
        try {
            criarIndices();
            criarTabelaResumoConselhos();
            criarTabelaLogPagamento();
            criarViewDetalhesAcademicosAluno();
            criarViewPerfilCompletoProfessor();
            criarFuncaoSituacaoAluno();
            criarFuncaoCalcularMediaTurma();
            criarProcedimentoUpdateFrequenciaAluno();
            criarProcedimentoContarConselhosPorProfessor();
            criarTriggerLogPagamento();
            criarTriggerRecalcularMediaGeralAluno();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inicializar estruturas avançadas: " + e.getMessage(), e);
        }
    }
}


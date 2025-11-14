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

    // ========== CRIAÇÃO DE ÍNDICES ==========
    
    public void criarIndices() {
        try {
            jdbcTemplate.execute("CREATE INDEX IF NOT EXISTS idx_aluno_nome ON Aluno (Nome)");
            jdbcTemplate.execute("CREATE INDEX IF NOT EXISTS idx_aluno_media ON Aluno (Media)");
        } catch (Exception e) {
            // Índices podem já existir, ignorar erro
        }
    }

    // ========== CONSULTAS SQL ==========
    
    // 1. Professores sem ofertas
    public List<Map<String, Object>> buscarProfessoresSemOfertas() {
        String sql = """
            SELECT p.Id_Prof, p.Nome
            FROM Professor p
            LEFT JOIN Oferta o ON p.Id_Prof = o.Id_Prof
            WHERE o.Id_Prof IS NULL
        """;
        return jdbcTemplate.queryForList(sql);
    }

    // 2. Lista de alunos e professores com emails (UNION)
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

    // 3. Alunos com média acima da média geral
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

    // 4. Alunos matriculados em turmas de 2023
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

    // ========== VIEWS ==========
    
    // Criar view vw_DetalhesAcademicosAluno (MODIFICADA: removido nome_disciplina, Nota_específica, nome_professor; adicionado telefone e sexo)
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

    // Consultar view vw_DetalhesAcademicosAluno
    public List<Map<String, Object>> consultarViewDetalhesAcademicosAluno() {
        String sql = "SELECT * FROM vw_DetalhesAcademicosAluno";
        return jdbcTemplate.queryForList(sql);
    }

    // Consultar view por aluno específico
    public List<Map<String, Object>> consultarViewDetalhesAcademicosAlunoPorId(int idAluno) {
        String sql = "SELECT * FROM vw_DetalhesAcademicosAluno WHERE Nome_Aluno IN (SELECT Nome FROM Aluno WHERE Id_Aluno = ?)";
        return jdbcTemplate.queryForList(sql, idAluno);
    }

    // Criar view vw_PerfilCompletoProfessor
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

    // Consultar view vw_PerfilCompletoProfessor
    public List<Map<String, Object>> consultarViewPerfilCompletoProfessor() {
        String sql = "SELECT * FROM vw_PerfilCompletoProfessor";
        return jdbcTemplate.queryForList(sql);
    }

    // Consultar view por professor específico
    public List<Map<String, Object>> consultarViewPerfilCompletoProfessorPorId(int idProf) {
        String sql = "SELECT * FROM vw_PerfilCompletoProfessor WHERE Id_Prof = ?";
        return jdbcTemplate.queryForList(sql, idProf);
    }

    // ========== FUNÇÕES ==========
    
    // Criar função situacaoAluno
    public void criarFuncaoSituacaoAluno() {
        try {
            jdbcTemplate.execute("DROP FUNCTION IF EXISTS situacaoAluno");
        } catch (Exception e) {
            // Ignorar se não existir
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

    // Chamar função situacaoAluno
    public String chamarFuncaoSituacaoAluno(int idAluno) {
        String sql = "SELECT situacaoAluno(?)";
        return jdbcTemplate.queryForObject(sql, String.class, idAluno);
    }

    // Função calcularMediaDisciplina REMOVIDA conforme solicitado

    // ========== PROCEDIMENTOS ==========
    
    // Criar procedimento updateFrequenciaAluno
    public void criarProcedimentoUpdateFrequenciaAluno() {
        try {
            jdbcTemplate.execute("DROP PROCEDURE IF EXISTS updateFrequenciaAluno");
        } catch (Exception e) {
            // Ignorar se não existir
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

    // Chamar procedimento updateFrequenciaAluno
    public void chamarProcedimentoUpdateFrequenciaAluno(int idAluno, double frequenciaNova) {
        String sql = "CALL updateFrequenciaAluno(?, ?)";
        jdbcTemplate.update(sql, idAluno, frequenciaNova);
    }

    // Criar tabela Resumo_Conselhos
    public void criarTabelaResumoConselhos() {
        String sql = """
            CREATE TABLE IF NOT EXISTS Resumo_Conselhos (
                Id_Prof INT PRIMARY KEY,
                Total_Conselhos INT
            )
        """;
        jdbcTemplate.execute(sql);
    }

    // Criar procedimento contarConselhosPorProfessor
    public void criarProcedimentoContarConselhosPorProfessor() {
        try {
            jdbcTemplate.execute("DROP PROCEDURE IF EXISTS contarConselhosPorProfessor");
        } catch (Exception e) {
            // Ignorar se não existir
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

    // Chamar procedimento contarConselhosPorProfessor
    public void chamarProcedimentoContarConselhosPorProfessor() {
        String sql = "CALL contarConselhosPorProfessor()";
        jdbcTemplate.execute(sql);
    }

    // Consultar Resumo_Conselhos
    public List<Map<String, Object>> consultarResumoConselhos() {
        String sql = "SELECT * FROM Resumo_Conselhos";
        return jdbcTemplate.queryForList(sql);
    }
    
    // Consultar conselhos por Id_professor específico
    public List<Map<String, Object>> consultarConselhosPorProfessor(int idProf) {
        String sql = """
            SELECT 
                c.Id_Conselho,
                c.Descricao,
                c.Data,
                p.Nome AS Nome_Professor
            FROM Conselho c
            JOIN Participa pa ON c.Id_Conselho = pa.Id_Conselho
            JOIN Professor p ON pa.Id_Prof = p.Id_Prof
            WHERE pa.Id_Prof = ?
        """;
        return jdbcTemplate.queryForList(sql, idProf);
    }
    
    // Criar conselho e atribuir a professor
    public int criarConselhoEAtribuir(int idProf, String descricao, java.sql.Date data) {
        // Primeiro criar o conselho
        String sqlConselho = "INSERT INTO Conselho (Descricao, Data, Id_Prof) VALUES (?, ?, ?)";
        jdbcTemplate.update(sqlConselho, descricao, data, idProf);
        
        // Obter o ID do conselho criado
        String sqlId = "SELECT LAST_INSERT_ID()";
        Integer idConselho = jdbcTemplate.queryForObject(sqlId, Integer.class);
        
        // Atribuir ao professor através da tabela Participa
        String sqlParticipa = "INSERT INTO Participa (Id_Prof, Id_Conselho) VALUES (?, ?) ON DUPLICATE KEY UPDATE Id_Prof = Id_Prof";
        return jdbcTemplate.update(sqlParticipa, idProf, idConselho);
    }

    // ========== TRIGGERS ==========
    
    // Criar tabela Log_Pagamento
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

    // Criar trigger logPagamento
    public void criarTriggerLogPagamento() {
        try {
            jdbcTemplate.execute("DROP TRIGGER IF EXISTS logPagamento");
        } catch (Exception e) {
            // Ignorar se não existir
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

    // Consultar Log_Pagamento (MODIFICADO: mostrar nome_aluno ao invés de Id_log)
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

    // Criar trigger recalcularMediaGeralAluno
    public void criarTriggerRecalcularMediaGeralAluno() {
        try {
            jdbcTemplate.execute("DROP TRIGGER IF EXISTS recalcularMediaGeralAluno");
        } catch (Exception e) {
            // Ignorar se não existir
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
    
    // Criar procedimento calcularMediaTurma
    public void criarProcedimentoCalcularMediaTurma() {
        try {
            jdbcTemplate.execute("DROP PROCEDURE IF EXISTS calcularMediaTurma");
        } catch (Exception e) {
            // Ignorar se não existir
        }
        
        String sql = """
            CREATE PROCEDURE calcularMediaTurma(IN idTurma INT)
            BEGIN
                SELECT COALESCE(AVG(a.Media), 0) AS mediaTurma
                FROM Aluno a
                JOIN Matricula m ON a.Id_Aluno = m.Id_Aluno
                WHERE m.Id_Turma = idTurma
                AND a.Media IS NOT NULL
                AND a.Media > 0;
            END
        """;
        jdbcTemplate.execute(sql);
    }
    
    // Chamar procedimento calcularMediaTurma
    public Double chamarProcedimentoCalcularMediaTurma(int idTurma) {
        // Usar query direta ao invés de procedure com OUT parameter
        String sql = """
            SELECT COALESCE(AVG(a.Media), 0) AS mediaTurma
            FROM Aluno a
            JOIN Matricula m ON a.Id_Aluno = m.Id_Aluno
            WHERE m.Id_Turma = ?
            AND a.Media IS NOT NULL
            AND a.Media > 0
        """;
        try {
            return jdbcTemplate.queryForObject(sql, Double.class, idTurma);
        } catch (Exception e) {
            return 0.0;
        }
    }

    // ========== MÉTODO PARA INICIALIZAR TUDO ==========
    
    public void inicializarEstruturasAvancadas() {
        try {
            criarIndices();
            criarTabelaResumoConselhos();
            criarTabelaLogPagamento();
            criarViewDetalhesAcademicosAluno();
            criarViewPerfilCompletoProfessor();
            criarFuncaoSituacaoAluno();
            // Função calcularMediaDisciplina removida
            criarProcedimentoUpdateFrequenciaAluno();
            criarProcedimentoCalcularMediaTurma();
            criarProcedimentoContarConselhosPorProfessor();
            criarTriggerLogPagamento();
            criarTriggerRecalcularMediaGeralAluno();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inicializar estruturas avançadas: " + e.getMessage(), e);
        }
    }
}


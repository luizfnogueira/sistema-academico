package com.sistemaacademico.repository;

import org.springframework.stereotype.Repository;
import com.sistemaacademico.model.Aluno;
import com.sistemaacademico.model.Disciplina;
import com.sistemaacademico.dto.ConsultaResultadoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;
import java.util.Map;

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

    // ========== OPERAÇÕES CRUD PARA ALUNOS ==========
    
    // Inserir novo aluno
    public int inserirAluno(Aluno aluno) {
        String sql = "INSERT INTO Aluno (Nome, Sexo, Idade, Num, CEP, Rua, Media, Frequencia, Monitor, Monitorado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, 
            aluno.getNome(), 
            aluno.getSexo(), 
            aluno.getIdade(), 
            aluno.getNum(), 
            aluno.getCep(), 
            aluno.getRua(), 
            aluno.getMedia(), 
            aluno.getFrequencia(), 
            aluno.getMonitor(), 
            aluno.getMonitorado());
    }

    // Buscar todos os alunos
    public List<Aluno> listarTodosAlunos() {
        String sql = "SELECT * FROM Aluno ORDER BY Nome";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Aluno.class));
    }

    // Buscar aluno por ID
    public Aluno buscarAlunoPorId(int id) {
        String sql = "SELECT * FROM Aluno WHERE Id_Aluno = ?";
        try {
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Aluno.class), id);
        } catch (Exception e) {
            return null;
        }
    }

    // Atualizar aluno
    public int atualizarAluno(Aluno aluno) {
        String sql = "UPDATE Aluno SET Nome = ?, Sexo = ?, Idade = ?, Num = ?, CEP = ?, Rua = ?, Media = ?, Frequencia = ?, Monitor = ?, Monitorado = ? WHERE Id_Aluno = ?";
        return jdbcTemplate.update(sql, 
            aluno.getNome(), 
            aluno.getSexo(), 
            aluno.getIdade(), 
            aluno.getNum(), 
            aluno.getCep(), 
            aluno.getRua(), 
            aluno.getMedia(), 
            aluno.getFrequencia(), 
            aluno.getMonitor(), 
            aluno.getMonitorado(),
            aluno.getIdAluno());
    }

    // Deletar aluno por ID
    public int deletarAluno(int id) {
        String sql = "DELETE FROM Aluno WHERE Id_Aluno = ?";
        return jdbcTemplate.update(sql, id);
    }

    // ========== OPERAÇÕES CRUD PARA DISCIPLINAS ==========
    
    // Inserir nova disciplina
    public int inserirDisciplina(Disciplina disciplina) {
        String sql = "INSERT INTO Disciplina (Nome, Carga_Horaria, Id_Aluno, Id_Turma) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, 
            disciplina.getNome(), 
            disciplina.getCargaHoraria(), 
            disciplina.getIdAluno(), 
            disciplina.getIdTurma());
    }

    // Buscar todas as disciplinas
    public List<Disciplina> listarTodasDisciplinas() {
        String sql = "SELECT * FROM Disciplina ORDER BY Nome";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Disciplina.class));
    }

    // Buscar disciplina por ID
    public Disciplina buscarDisciplinaPorId(int id) {
        String sql = "SELECT * FROM Disciplina WHERE Id_Disc = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Disciplina.class), id);
        } catch (Exception e) {
            return null;
        }
    }

    // Atualizar disciplina
    public int atualizarDisciplina(Disciplina disciplina) {
        String sql = "UPDATE Disciplina SET Nome = ?, Carga_Horaria = ?, Id_Aluno = ?, Id_Turma = ? WHERE Id_Disc = ?";
        return jdbcTemplate.update(sql, 
            disciplina.getNome(), 
            disciplina.getCargaHoraria(), 
            disciplina.getIdAluno(), 
            disciplina.getIdTurma(),
            disciplina.getIdDisc());
    }

    // Deletar disciplina por ID
    public int deletarDisciplina(int id) {
        String sql = "DELETE FROM Disciplina WHERE Id_Disc = ?";
        return jdbcTemplate.update(sql, id);
    }

    // ========== CONSULTAS ESTATÍSTICAS PARA DASHBOARD ==========
    
    // Total de alunos cadastrados
    public int contarTotalAlunos() {
        String sql = "SELECT COUNT(*) FROM Aluno";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    // Total de disciplinas cadastradas
    public int contarTotalDisciplinas() {
        String sql = "SELECT COUNT(*) FROM Disciplina";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    // Média geral de notas dos alunos
    public Double calcularMediaGeralNotas() {
        String sql = "SELECT AVG(Media) FROM Aluno WHERE Media > 0";
        return jdbcTemplate.queryForObject(sql, Double.class);
    }

    // Alunos com frequência abaixo de 75%
    public int contarAlunosComFrequenciaBaixa() {
        String sql = "SELECT COUNT(*) FROM Aluno WHERE Frequencia < 75";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    // Distribuição por sexo
    public List<ConsultaResultadoDTO> obterDistribuicaoPorSexo() {
        String sql = "SELECT Sexo as descricao, COUNT(*) as valor FROM Aluno GROUP BY Sexo";
        return jdbcTemplate.query(sql, (rs, rowNum) -> 
            new ConsultaResultadoDTO(rs.getString("descricao"), rs.getInt("valor")));
    }

    // Top 5 disciplinas com maior carga horária
    public List<ConsultaResultadoDTO> obterTopDisciplinasCargaHoraria() {
        String sql = "SELECT Nome as descricao, Carga_Horaria as valor FROM Disciplina ORDER BY Carga_Horaria DESC LIMIT 5";
        return jdbcTemplate.query(sql, (rs, rowNum) -> 
            new ConsultaResultadoDTO(rs.getString("descricao"), rs.getInt("valor")));
    }

    // Alunos por faixa etária
    public List<ConsultaResultadoDTO> obterDistribuicaoPorIdade() {
        String sql = """
            SELECT 
                CASE 
                    WHEN Idade < 20 THEN 'Menos de 20 anos'
                    WHEN Idade BETWEEN 20 AND 25 THEN '20-25 anos'
                    WHEN Idade BETWEEN 26 AND 30 THEN '26-30 anos'
                    ELSE 'Mais de 30 anos'
                END as descricao,
                COUNT(*) as valor
            FROM Aluno 
            GROUP BY 
                CASE 
                    WHEN Idade < 20 THEN 'Menos de 20 anos'
                    WHEN Idade BETWEEN 20 AND 25 THEN '20-25 anos'
                    WHEN Idade BETWEEN 26 AND 30 THEN '26-30 anos'
                    ELSE 'Mais de 30 anos'
                END
            ORDER BY valor DESC
        """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> 
            new ConsultaResultadoDTO(rs.getString("descricao"), rs.getInt("valor")));
    }

    // Taxa de aprovação (alunos com média >= 7)
    public Double calcularTaxaAprovacao() {
        String sql = """
            SELECT 
                (COUNT(CASE WHEN Media >= 7 THEN 1 END) * 100.0 / COUNT(*)) as taxa_aprovacao
            FROM Aluno 
            WHERE Media > 0
        """;
        return jdbcTemplate.queryForObject(sql, Double.class);
    }

    // Alunos com melhor desempenho (top 10)
    public List<Aluno> obterTopAlunos() {
        String sql = "SELECT * FROM Aluno WHERE Media > 0 ORDER BY Media DESC LIMIT 10";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Aluno.class));
    }

    // ========== CONSULTAS COMPLEXAS PARA ETAPA 03 ==========
    
    // CONSULTA 1: Alunos e suas Disciplinas com Médias (COM JOIN)
    public List<Map<String, Object>> obterAlunosComDisciplinas() {
        String sql = """
            SELECT 
                a.Nome as Nome_Aluno,
                a.Media as Media_Geral,
                d.Nome as Nome_Disciplina,
                d.Carga_Horaria,
                av.Valor as Nota_Disciplina,
                av.Data as Data_Avaliacao
            FROM Aluno a
            LEFT JOIN Disciplina d ON a.Id_Aluno = d.Id_Aluno
            LEFT JOIN Avaliacao av ON a.Id_Aluno = av.Id_Aluno AND d.Id_Disc = av.Id_Disc
            WHERE a.Media > 0
            ORDER BY a.Media DESC, d.Nome
        """;
        return jdbcTemplate.queryForList(sql);
    }

    // CONSULTA 2: Distribuição por Faixa Etária e Sexo (ESTATÍSTICA)
    public List<Map<String, Object>> obterDistribuicaoPorIdadeESexo() {
        String sql = """
            SELECT 
                CASE 
                    WHEN a.Idade < 20 THEN 'Menos de 20 anos'
                    WHEN a.Idade BETWEEN 20 AND 25 THEN '20-25 anos'
                    WHEN a.Idade BETWEEN 26 AND 30 THEN '26-30 anos'
                    ELSE 'Mais de 30 anos'
                END as Faixa_Etaria,
                a.Sexo,
                COUNT(*) as Quantidade,
                AVG(a.Media) as Media_Idade,
                AVG(a.Frequencia) as Frequencia_Media
            FROM Aluno a
            WHERE a.Media > 0
            GROUP BY 
                CASE 
                    WHEN a.Idade < 20 THEN 'Menos de 20 anos'
                    WHEN a.Idade BETWEEN 20 AND 25 THEN '20-25 anos'
                    WHEN a.Idade BETWEEN 26 AND 30 THEN '26-30 anos'
                    ELSE 'Mais de 30 anos'
                END,
                a.Sexo
            ORDER BY Faixa_Etaria, a.Sexo
        """;
        return jdbcTemplate.queryForList(sql);
    }

    // CONSULTA 3: Análise de Desempenho por Disciplina (COMPLEXA COM JOIN)
    public List<Map<String, Object>> obterAnaliseDesempenhoDisciplinas() {
        String sql = """
            SELECT 
                d.Nome as Disciplina,
                d.Carga_Horaria,
                COUNT(DISTINCT a.Id_Aluno) as Total_Alunos,
                COUNT(av.Id_Avalia) as Total_Avaliacoes,
                AVG(av.Valor) as Media_Disciplina,
                MAX(av.Valor) as Maior_Nota,
                MIN(av.Valor) as Menor_Nota,
                COUNT(CASE WHEN av.Valor >= 7 THEN 1 END) as Aprovados,
                COUNT(CASE WHEN av.Valor < 7 THEN 1 END) as Reprovados,
                ROUND((COUNT(CASE WHEN av.Valor >= 7 THEN 1 END) * 100.0 / COUNT(av.Id_Avalia)), 2) as Taxa_Aprovacao
            FROM Disciplina d
            LEFT JOIN Aluno a ON d.Id_Aluno = a.Id_Aluno
            LEFT JOIN Avaliacao av ON d.Id_Disc = av.Id_Disc
            GROUP BY d.Id_Disc, d.Nome, d.Carga_Horaria
            HAVING COUNT(av.Id_Avalia) > 0
            ORDER BY Media_Disciplina DESC
        """;
        return jdbcTemplate.queryForList(sql);
    }

    // CONSULTA 4: Situação Acadêmica Completa (COMPLEXA COM JOIN)
    public List<Map<String, Object>> obterSituacaoAcademicaCompleta() {
        String sql = """
            SELECT 
                a.Id_Aluno,
                a.Nome,
                a.Sexo,
                a.Idade,
                a.Media as Media_Geral,
                a.Frequencia,
                CASE 
                    WHEN a.Media >= 7 AND a.Frequencia >= 75 THEN 'APROVADO'
                    WHEN a.Media < 7 AND a.Frequencia >= 75 THEN 'REPROVADO POR NOTA'
                    WHEN a.Media >= 7 AND a.Frequencia < 75 THEN 'REPROVADO POR FREQUÊNCIA'
                    ELSE 'REPROVADO POR NOTA E FREQUÊNCIA'
                END as Situacao_Academica,
                COUNT(DISTINCT d.Id_Disc) as Total_Disciplinas,
                COUNT(av.Id_Avalia) as Total_Avaliacoes,
                COALESCE(SUM(p.Valor), 0) as Total_Pagamentos,
                CASE 
                    WHEN p.Status = 'Pago' THEN 'EM DIA'
                    WHEN p.Status = 'Pendente' THEN 'PENDENTE'
                    ELSE 'EM ATRASO'
                END as Situacao_Financeira
            FROM Aluno a
            LEFT JOIN Disciplina d ON a.Id_Aluno = d.Id_Aluno
            LEFT JOIN Avaliacao av ON a.Id_Aluno = av.Id_Aluno
            LEFT JOIN Pagamento p ON a.Id_Aluno = p.Id_Aluno
            GROUP BY a.Id_Aluno, a.Nome, a.Sexo, a.Idade, a.Media, a.Frequencia, p.Status
            ORDER BY a.Media DESC, a.Frequencia DESC
        """;
        return jdbcTemplate.queryForList(sql);
    }

    // CONSULTA 5: Ranking de Professores (BÔNUS - COMPLEXA COM JOIN)
    public List<Map<String, Object>> obterRankingProfessores() {
        String sql = """
            SELECT 
                p.Nome as Nome_Professor,
                p.CPF,
                COUNT(DISTINCT d.Id_Disc) as Disciplinas_Ministradas,
                COUNT(DISTINCT a.Id_Aluno) as Alunos_Atendidos,
                AVG(a.Media) as Media_Alunos,
                AVG(a.Frequencia) as Frequencia_Media_Alunos,
                COUNT(av.Id_Avalia) as Total_Avaliacoes,
                AVG(av.Valor) as Media_Avaliacoes,
                COUNT(CASE WHEN av.Valor >= 7 THEN 1 END) as Aprovacoes,
                ROUND((COUNT(CASE WHEN av.Valor >= 7 THEN 1 END) * 100.0 / COUNT(av.Id_Avalia)), 2) as Taxa_Aprovacao_Professor
            FROM Professor p
            LEFT JOIN Disciplina d ON p.Id_Prof = d.Id_Prof
            LEFT JOIN Aluno a ON d.Id_Aluno = a.Id_Aluno
            LEFT JOIN Avaliacao av ON d.Id_Disc = av.Id_Disc
            WHERE p.Id_Prof IS NOT NULL
            GROUP BY p.Id_Prof, p.Nome, p.CPF
            HAVING COUNT(av.Id_Avalia) > 0
            ORDER BY Taxa_Aprovacao_Professor DESC, Media_Avaliacoes DESC
        """;
        return jdbcTemplate.queryForList(sql);
    }
}

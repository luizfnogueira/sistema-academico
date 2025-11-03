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

    // ========== CRIAÇÃO DE TABELAS (EM ORDEM DE DEPENDÊNCIA) ==========

    // Grupo 1: Tabelas sem dependências externas (exceto si mesmas)
    public void criarTabelaAluno() {
        String sql = "CREATE TABLE IF NOT EXISTS Aluno (" +
                "Id_Aluno INT PRIMARY KEY AUTO_INCREMENT," +
                "Nome VARCHAR(255) NOT NULL," +
                "Sexo CHAR(1) CHECK (Sexo IN ('M','F'))," +
                "Data_Nasc DATE NOT NULL," + // Adicionado
                "Idade INT CHECK (Idade >= 16)," +
                "Num INT," +
                "CEP VARCHAR(9)," +
                "Rua VARCHAR(255)," +
                "Media DECIMAL(4,2)," +
                "Frequencia DECIMAL(5,2) DEFAULT 100 CHECK (Frequencia BETWEEN 0 AND 100)" +
                // Colunas Monitor e Monitorado removidas
                ")";
        jdbcTemplate.execute(sql);
    }

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

    public void criarTabelaTurma() {
        String sql = "CREATE TABLE IF NOT EXISTS Turma (" +
                "Id_Turma INT PRIMARY KEY AUTO_INCREMENT," +
                "Nome VARCHAR(100) NOT NULL," +
                "Ano INT NOT NULL" +
                ")";
        jdbcTemplate.execute(sql);
    }

    public void criarTabelaRecurso() {
        String sql = "CREATE TABLE IF NOT EXISTS Recurso (" +
                "Id_Recurso INT PRIMARY KEY AUTO_INCREMENT," +
                "Tipo VARCHAR(100) DEFAULT 'Geral'," +
                "Localizacao VARCHAR(255)" +
                ")";
        jdbcTemplate.execute(sql);
    }

    public void criarTabelaDisciplina() {
        String sql = "CREATE TABLE IF NOT EXISTS Disciplina (" +
                "Id_Disc INT PRIMARY KEY AUTO_INCREMENT," +
                "Nome VARCHAR(255) NOT NULL," +
                "Carga_Horaria INT DEFAULT 40 CHECK (Carga_Horaria BETWEEN 20 AND 120)" +
                // Colunas Id_Aluno e Id_Turma removidas
                ")";
        jdbcTemplate.execute(sql);
    }

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

    // Grupo 2: Tabelas que dependem do Grupo 1

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
    
    public void criarTabelaProjExtensao() {
        String sql = "CREATE TABLE IF NOT EXISTS Proj_Extensao (" +
                "Id_Proj INT PRIMARY KEY AUTO_INCREMENT," +
                "Nome VARCHAR(255)," +
                "Descricao VARCHAR(255)," +
                "Id_Prof INT," +
                // Corrigido para "Opção 2": referencia Professor, não Efetivado
                "FOREIGN KEY (Id_Prof) REFERENCES Professor(Id_Prof) ON DELETE SET NULL" +
                ")";
        jdbcTemplate.execute(sql);
    }

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

    public void criarTabelaMatricula() {
        String sql = "CREATE TABLE IF NOT EXISTS Matricula (" +
                "Id_Matricula INT PRIMARY KEY AUTO_INCREMENT," +
                "Data DATE," +
                "Id_Aluno INT," +
                "Id_Turma INT," + // Adicionado
                "FOREIGN KEY (Id_Aluno) REFERENCES Aluno(Id_Aluno) ON DELETE SET NULL," +
                "FOREIGN KEY (Id_Turma) REFERENCES Turma(Id_Turma) ON DELETE SET NULL" + // Adicionado
                ")";
        jdbcTemplate.execute(sql);
    }

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

    // Grupo 3: Tabelas de Ligação (dependem do Grupo 1 e 2)
    
    public void criarTabelaMonitora() {
        String sql = "CREATE TABLE IF NOT EXISTS Monitora (" +
                "Id_Monitor INT," +
                "Id_Monitorado INT," +
                "PRIMARY KEY (Id_Monitor, Id_Monitorado)," +
                "FOREIGN KEY (Id_Monitor) REFERENCES Aluno(Id_Aluno) ON DELETE CASCADE ON UPDATE CASCADE," +
                "FOREIGN KEY (Id_Monitorado) REFERENCES Aluno(Id_Aluno) ON DELETE CASCADE ON UPDATE CASCADE" +
                ")";
        jdbcTemplate.execute(sql);
    }

    public void criarTabelaOferta() {
        String sql = "CREATE TABLE IF NOT EXISTS Oferta (" +
                "Id_Prof INT," +
                "Id_Turma INT," +
                "Id_Disc INT," +
                "PRIMARY KEY (Id_Prof, Id_Turma, Id_Disc)," +
                "FOREIGN KEY (Id_Prof) REFERENCES Professor(Id_Prof) ON DELETE CASCADE," +
                "FOREIGN KEY (Id_Turma) REFERENCES Turma(Id_Turma) ON DELETE CASCADE," +
                "FOREIGN KEY (Id_Disc) REFERENCES Disciplina(Id_Disc) ON DELETE CASCADE" +
                ")";
        jdbcTemplate.execute(sql);
    }

    public void criarTabelaUtiliza() {
        String sql = "CREATE TABLE IF NOT EXISTS Utiliza (" +
                "Id_Turma INT," +
                "Id_Recurso INT," +
                "Data DATE NOT NULL," +
                "Hora TIME," +
                "Observacao VARCHAR(255)," +
                "PRIMARY KEY (Id_Turma, Id_Recurso, Data)," +
                "FOREIGN KEY (Id_Turma) REFERENCES Turma(Id_Turma)," +
                "FOREIGN KEY (Id_Recurso) REFERENCES Recurso(Id_Recurso)" +
                ")";
        jdbcTemplate.execute(sql);
    }

    public void criarTabelaParticipa() {
        String sql = "CREATE TABLE IF NOT EXISTS Participa (" +
                "Id_Prof INT," +
                "Id_Conselho INT," +
                "PRIMARY KEY (Id_Prof, Id_Conselho)," +
                "FOREIGN KEY (Id_Prof) REFERENCES Professor(Id_Prof) ON DELETE CASCADE," +
                "FOREIGN KEY (Id_Conselho) REFERENCES Conselho(Id_Conselho) ON DELETE CASCADE" +
                ")";
        jdbcTemplate.execute(sql);
    }

    // ========== OPERAÇÕES CRUD PARA ALUNOS (Corrigido) ==========
    
    // Inserir novo aluno
    public int inserirAluno(Aluno aluno) {
        String sql = "INSERT INTO Aluno (Nome, Sexo, Data_Nasc, Idade, Num, CEP, Rua, Media, Frequencia) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, 
            aluno.getNome(), 
            aluno.getSexo(), 
            aluno.getDataNasc(), // Adicionado
            aluno.getIdade(), 
            aluno.getNum(), 
            aluno.getCep(), 
            aluno.getRua(), 
            aluno.getMedia(), 
            aluno.getFrequencia()
            // monitor e monitorado removidos
        );
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
        // Nota: Esta query de update é simplificada.
        // Em um sistema real, você faria updates parciais (só o que mudou).
        String sql = "UPDATE Aluno SET Nome = ?, Sexo = ?, Data_Nasc = ?, Idade = ?, Num = ?, CEP = ?, Rua = ?, Media = ?, Frequencia = ? WHERE Id_Aluno = ?";
        return jdbcTemplate.update(sql, 
            aluno.getNome(), 
            aluno.getSexo(), 
            aluno.getDataNasc(), // Adicionado
            aluno.getIdade(), 
            aluno.getNum(), 
            aluno.getCep(), 
            aluno.getRua(), 
            aluno.getMedia(), 
            aluno.getFrequencia(),
            aluno.getIdAluno()
            // monitor e monitorado removidos
        );
    }

    // Deletar aluno por ID
    public int deletarAluno(int id) {
        String sql = "DELETE FROM Aluno WHERE Id_Aluno = ?";
        return jdbcTemplate.update(sql, id);
    }

    // ========== OPERAÇÕES CRUD PARA DISCIPLINAS (Corrigido) ==========
    
    // Inserir nova disciplina
    public int inserirDisciplina(Disciplina disciplina) {
        String sql = "INSERT INTO Disciplina (Nome, Carga_Horaria) VALUES (?, ?)";
        return jdbcTemplate.update(sql, 
            disciplina.getNome(), 
            disciplina.getCargaHoraria()
            // idAluno e idTurma removidos
        );
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
        String sql = "UPDATE Disciplina SET Nome = ?, Carga_Horaria = ? WHERE Id_Disc = ?";
        return jdbcTemplate.update(sql, 
            disciplina.getNome(), 
            disciplina.getCargaHoraria(),
            disciplina.getIdDisc()
            // idAluno e idTurma removidos
        );
    }

    // Deletar disciplina por ID
    public int deletarDisciplina(int id) {
        String sql = "DELETE FROM Disciplina WHERE Id_Disc = ?";
        return jdbcTemplate.update(sql, id);
    }

    // ========== CONSULTAS ESTATÍSTICAS (Sem alterações) ==========
    
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

    // ... (Restante dos métodos de consulta estatística, que parecem corretos e 
    // devem funcionar se as tabelas referenciadas (Aluno, Disciplina, Avaliacao, Pagamento, Pesquisa) 
    // estiverem populadas com dados) ...
    
    // (Cole o restante dos seus métodos de consulta aqui... 
    // obterDistribuicaoPorSexo, obterTopDisciplinasCargaHoraria, etc.)
    
    // ========== COLE O RESTANTE DOS SEUS MÉTODOS DE CONSULTA AQUI ==========
    // (Os métodos de consulta complexos e de gráficos que você já tinha)
    
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
            JOIN Matricula m ON a.Id_Aluno = m.Id_Aluno
            JOIN Turma t ON m.Id_Turma = t.Id_Turma
            JOIN Oferta o ON t.Id_Turma = o.Id_Turma
            JOIN Disciplina d ON o.Id_Disc = d.Id_Disc
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
            LEFT JOIN Avaliacao av ON d.Id_Disc = av.Id_Disc
            LEFT JOIN Aluno a ON av.Id_Aluno = a.Id_Aluno
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
                COUNT(DISTINCT m.Id_Turma) as Total_Turmas,
                COUNT(av.Id_Avalia) as Total_Avaliacoes,
                COALESCE(SUM(p.Valor), 0) as Total_Pagamentos,
                (SELECT GROUP_CONCAT(DISTINCT p.Status SEPARATOR ', ') 
                 FROM Pagamento p 
                 WHERE p.Id_Aluno = a.Id_Aluno) as Situacao_Financeira
            FROM Aluno a
            LEFT JOIN Matricula m ON a.Id_Aluno = m.Id_Aluno
            LEFT JOIN Avaliacao av ON a.Id_Aluno = av.Id_Aluno
            LEFT JOIN Pagamento p ON a.Id_Aluno = p.Id_Aluno
            GROUP BY a.Id_Aluno, a.Nome, a.Sexo, a.Idade, a.Media, a.Frequencia
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
                COUNT(DISTINCT o.Id_Disc) as Disciplinas_Ministradas,
                COUNT(DISTINCT m.Id_Aluno) as Alunos_Atendidos,
                AVG(a.Media) as Media_Alunos,
                AVG(a.Frequencia) as Frequencia_Media_Alunos,
                COUNT(av.Id_Avalia) as Total_Avaliacoes,
                AVG(av.Valor) as Media_Avaliacoes,
                COUNT(CASE WHEN av.Valor >= 7 THEN 1 END) as Aprovacoes,
                ROUND((COUNT(CASE WHEN av.Valor >= 7 THEN 1 END) * 100.0 / COUNT(av.Id_Avalia)), 2) as Taxa_Aprovacao_Professor
            FROM Professor p
            LEFT JOIN Oferta o ON p.Id_Prof = o.Id_Prof
            LEFT JOIN Turma t ON o.Id_Turma = t.Id_Turma
            LEFT JOIN Matricula m ON t.Id_Turma = m.Id_Turma
            LEFT JOIN Aluno a ON m.Id_Aluno = a.Id_Aluno
            LEFT JOIN Avaliacao av ON a.Id_Aluno = av.Id_Aluno AND o.Id_Disc = av.Id_Disc
            WHERE p.Id_Prof IS NOT NULL
            GROUP BY p.Id_Prof, p.Nome, p.CPF
            HAVING COUNT(av.Id_Avalia) > 0
            ORDER BY Taxa_Aprovacao_Professor DESC, Media_Avaliacoes DESC
        """;
        return jdbcTemplate.queryForList(sql);
    }

    // ========== MÉTODOS FALTANTES PARA GRÁFICOS ==========
    
    public List<Map<String, Object>> obterMediaPorFrequenciaEstudo() {
        String sql = """
            SELECT 
                p.Freq_Estudo as x,
                a.Media as y
            FROM Aluno a
            LEFT JOIN Pesquisa p ON a.Id_Aluno = p.Id_Aluno
            WHERE a.Media > 0 AND p.Freq_Estudo IS NOT NULL
            ORDER BY p.Freq_Estudo
        """;
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> obterEstresseVsProjetos() {
        String sql = """
            SELECT 
                pe.Id_Proj as x,
                p.Nvl_Estresse as y
            FROM Pesquisa p
            LEFT JOIN Proj_Extensao pe ON p.Id_Aluno = pe.Id_Prof
            WHERE p.Nvl_Estresse IS NOT NULL
        """;
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> obterSuporteProfVsProjetos() {
        String sql = """
            SELECT 
                COUNT(pe.Id_Proj) as x,
                AVG(a.Media) as y
            FROM Aluno a
            LEFT JOIN Proj_Extensao pe ON a.Id_Aluno = pe.Id_Prof
            GROUP BY a.Id_Aluno
            HAVING COUNT(pe.Id_Proj) > 0
        """;
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> obterFrequenciaVsDisciplinas() {
        String sql = """
            SELECT 
                p.Qtd_Disciplinas as x,
                a.Frequencia as y
            FROM Aluno a
            LEFT JOIN Pesquisa p ON a.Id_Aluno = p.Id_Aluno
            WHERE p.Qtd_Disciplinas IS NOT NULL
            GROUP BY a.Id_Aluno, a.Frequencia, p.Qtd_Disciplinas
        """;
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> obterValorVsMedia() {
        String sql = """
            SELECT 
                SUM(p.Valor) as x,
                a.Media as y
            FROM Aluno a
            LEFT JOIN Pagamento p ON a.Id_Aluno = p.Id_Aluno
            WHERE a.Media > 0
            GROUP BY a.Id_Aluno, a.Media
        """;
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> obterDistribuicaoEmail() {
        String sql = """
            SELECT 
                'Com Email' as descricao,
                COUNT(DISTINCT a.Id_Aluno) as valor
            FROM Aluno a
            INNER JOIN Email e ON a.Id_Aluno = e.Id_Aluno
            UNION ALL
            SELECT 
                'Sem Email' as descricao,
                COUNT(a.Id_Aluno) as valor
            FROM Aluno a
            LEFT JOIN Email e ON a.Id_Aluno = e.Id_Aluno
            WHERE e.Id_Aluno IS NULL
        """;
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> obterDistribuicaoPorGenero() {
        String sql = """
            SELECT 
                Sexo as descricao,
                COUNT(*) as valor
            FROM Aluno
            GROUP BY Sexo
        """;
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> obterDistribuicaoTelefone() {
        String sql = """
            SELECT 
                'Com Telefone' as descricao,
                COUNT(DISTINCT a.Id_Aluno) as valor
            FROM Aluno a
            INNER JOIN Telefone t ON a.Id_Aluno = t.Id_Aluno
            UNION ALL
            SELECT 
                'Sem Telefone' as descricao,
                COUNT(a.Id_Aluno) as valor
            FROM Aluno a
            LEFT JOIN Telefone t ON a.Id_Aluno = t.Id_Aluno
            WHERE t.Id_Aluno IS NULL
        """;
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> obterDistribuicaoProjetos() {
        String sql = """
            SELECT 
                'Com Projetos' as descricao,
                COUNT(DISTINCT a.Id_Aluno) as valor
            FROM Aluno a
            INNER JOIN Proj_Extensao pe ON a.Id_Aluno = pe.Id_Prof
            UNION ALL
            SELECT 
                'Sem Projetos' as descricao,
                COUNT(a.Id_Aluno) as valor
            FROM Aluno a
            LEFT JOIN Proj_Extensao pe ON a.Id_Aluno = pe.Id_Prof
            WHERE pe.Id_Prof IS NULL
        """;
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> obterDistribuicaoFreqEstudo() {
        String sql = """
            SELECT 
                CASE 
                    WHEN p.Freq_Estudo < 2 THEN 'Baixa'
                    WHEN p.Freq_Estudo BETWEEN 2 AND 4 THEN 'Média'
                    ELSE 'Alta'
                END as descricao,
                COUNT(*) as valor
            FROM Pesquisa p
            WHERE p.Freq_Estudo IS NOT NULL
            GROUP BY 
                CASE 
                    WHEN p.Freq_Estudo < 2 THEN 'Baixa'
                    WHEN p.Freq_Estudo BETWEEN 2 AND 4 THEN 'Média'
                    ELSE 'Alta'
                END
        """;
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> obterDistribuicaoIdadeGrafico() {
        String sql = """
            SELECT 
                CASE 
                    WHEN Idade < 20 THEN 'Menos de 20'
                    WHEN Idade BETWEEN 20 AND 25 THEN '20-25'
                    WHEN Idade BETWEEN 26 AND 30 THEN '26-30'
                    ELSE 'Mais de 30'
                END as descricao,
                COUNT(*) as valor
            FROM Aluno
            GROUP BY 
                CASE 
                    WHEN Idade < 20 THEN 'Menos de 20'
                    WHEN Idade BETWEEN 20 AND 25 THEN '20-25'
                    WHEN Idade BETWEEN 26 AND 30 THEN '26-30'
                    ELSE 'Mais de 30'
                END
        """;
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> obterDistribuicaoUsoRecursos() {
        String sql = """
            SELECT 
                CASE 
                    WHEN p.Freq_Recurso = 0 THEN 'Nunca'
                    WHEN p.Freq_Recurso BETWEEN 1 AND 3 THEN 'Raramente'
                    WHEN p.Freq_Recurso BETWEEN 4 AND 6 THEN 'Frequentemente'
                    ELSE 'Sempre'
                END as descricao,
                COUNT(*) as valor
            FROM Pesquisa p
            WHERE p.Freq_Recurso IS NOT NULL
            GROUP BY 
                CASE 
                    WHEN p.Freq_Recurso = 0 THEN 'Nunca'
                    WHEN p.Freq_Recurso BETWEEN 1 AND 3 THEN 'Raramente'
                    WHEN p.Freq_Recurso BETWEEN 4 AND 6 THEN 'Frequentemente'
                    ELSE 'Sempre'
                END
        """;
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> obterDadosMonitoria() {
        String sql = """
            SELECT 
                'Monitor' as descricao,
                COUNT(DISTINCT Id_Monitor) as valor
            FROM Monitora
            UNION ALL
            SELECT 
                'Monitorado' as descricao,
                COUNT(DISTINCT Id_Monitorado) as valor
            FROM Monitora
            WHERE Id_Monitorado NOT IN (SELECT DISTINCT Id_Monitor FROM Monitora)
            UNION ALL
            SELECT 
                'Não Participa' as descricao,
                (SELECT COUNT(*) FROM Aluno) - 
                (SELECT COUNT(DISTINCT Id_Monitor) + COUNT(DISTINCT Id_Monitorado) FROM Monitora WHERE Id_Monitorado NOT IN (SELECT DISTINCT Id_Monitor FROM Monitora)) as valor
            FROM Aluno
            LIMIT 1
        """;
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> obterFreqRecursosVsEstudo() {
        String sql = """
            SELECT 
                p.Freq_Recurso as x,
                p.Freq_Estudo as y,
                a.Sexo as genero
            FROM Pesquisa p
            INNER JOIN Aluno a ON p.Id_Aluno = a.Id_Aluno
            WHERE p.Freq_Recurso IS NOT NULL AND p.Freq_Estudo IS NOT NULL
        """;
        return jdbcTemplate.queryForList(sql);
    }
}
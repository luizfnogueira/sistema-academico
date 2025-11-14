-- ============================================================================
-- SCRIPT COMPLETO DE INICIALIZAÇÃO DO BANCO DE DADOS
-- Sistema Acadêmico - MySQL
-- ============================================================================

-- 1. CRIAR O BANCO DE DADOS (se não existir)
CREATE DATABASE IF NOT EXISTS sistema_academico;
USE sistema_academico;

-- 2. REMOVER TABELAS EXISTENTES (se necessário - CUIDADO: apaga dados!)
-- Descomente as linhas abaixo apenas se quiser recriar tudo do zero
-- DROP DATABASE IF EXISTS sistema_academico;
-- CREATE DATABASE sistema_academico;
-- USE sistema_academico;

-- ============================================================================
-- 3. CRIAR TODAS AS TABELAS (em ordem de dependência)
-- ============================================================================

-- Tabela Aluno
CREATE TABLE IF NOT EXISTS Aluno (
    Id_Aluno INT PRIMARY KEY AUTO_INCREMENT,
    Nome VARCHAR(255) NOT NULL,
    Sexo CHAR(1) CHECK (Sexo IN ('M','F')),
    Data_Nasc DATE NOT NULL, 
    Idade INT CHECK (Idade >= 16),
    Num INT,
    CEP VARCHAR(9),
    Rua VARCHAR(255),	
    Media DECIMAL(4,2),
    Frequencia DECIMAL(5,2) DEFAULT 100 CHECK (Frequencia BETWEEN 0 AND 100)
);

-- Tabela Dependente
CREATE TABLE IF NOT EXISTS Dependente (
    Nome VARCHAR(100) NOT NULL,
    Data_Nasc DATE NOT NULL,
    Parentesco VARCHAR(50),
    Id_Aluno INT,
    PRIMARY KEY (Id_Aluno, Nome),
    FOREIGN KEY (Id_Aluno) REFERENCES Aluno(Id_Aluno)
        ON DELETE CASCADE
);

-- Tabela Monitora
CREATE TABLE IF NOT EXISTS Monitora (
    Id_Monitor INT,
    Id_Monitorado INT,
    PRIMARY KEY (Id_Monitor, Id_Monitorado),
    FOREIGN KEY (Id_Monitor) REFERENCES Aluno(Id_Aluno)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (Id_Monitorado) REFERENCES Aluno(Id_Aluno)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- Tabela Pesquisa
CREATE TABLE IF NOT EXISTS Pesquisa (
    Id_Pesquisa INT PRIMARY KEY AUTO_INCREMENT,
    Freq_Recurso INT DEFAULT 0,
    Nvl_Estresse VARCHAR(255) CHECK (Nvl_Estresse IN ('Nenhum','Baixo','Moderado','Alto','Muito alto')),
    Qtd_Disciplinas INT CHECK (Qtd_Disciplinas >= 1),
    Freq_Estudo INT,
    Id_Aluno INT,
    FOREIGN KEY (Id_Aluno) REFERENCES Aluno(Id_Aluno)
        ON DELETE SET NULL
);

-- Tabela Professor
CREATE TABLE IF NOT EXISTS Professor (
    Id_Prof INT PRIMARY KEY AUTO_INCREMENT,
    CPF VARCHAR(14) UNIQUE,
    Nome VARCHAR(255) NOT NULL,
    Rua VARCHAR(255),
    Num INT,
    CEP VARCHAR(9)
);

-- Tabela Efetivado
CREATE TABLE IF NOT EXISTS Efetivado (
    Id_Prof INT PRIMARY KEY,
    Salario DECIMAL(10, 2) NOT NULL,
    Data_Concurso DATE,
    Regime VARCHAR(50),
    FOREIGN KEY (Id_Prof) REFERENCES Professor(Id_Prof)
        ON UPDATE CASCADE ON DELETE CASCADE
);

-- Tabela Temporario
CREATE TABLE IF NOT EXISTS Temporario (
    Id_Prof INT PRIMARY KEY,
    Remuneracao DECIMAL(10, 2),
    Inicio DATE,
    Fim DATE,
    FOREIGN KEY (Id_Prof) REFERENCES Professor(Id_Prof)
        ON UPDATE CASCADE ON DELETE CASCADE
);

-- Tabela Turma
CREATE TABLE IF NOT EXISTS Turma (
    Id_Turma INT PRIMARY KEY AUTO_INCREMENT,
    Nome VARCHAR(100) NOT NULL,
    Ano INT NOT NULL
);

-- Tabela Recurso
CREATE TABLE IF NOT EXISTS Recurso (
    Id_Recurso INT PRIMARY KEY AUTO_INCREMENT,
    Tipo VARCHAR(100) DEFAULT 'Geral',
    Localizacao VARCHAR(255)
);

-- Tabela Disciplina
CREATE TABLE IF NOT EXISTS Disciplina (
    Id_Disc INT PRIMARY KEY AUTO_INCREMENT,
    Nome VARCHAR(255) NOT NULL,
    Carga_Horaria INT DEFAULT 40 CHECK (Carga_Horaria BETWEEN 20 AND 120)
);

-- Tabela Oferta
CREATE TABLE IF NOT EXISTS Oferta (
    Id_Prof INT,
    Id_Turma INT,
    Id_Disc INT,
    PRIMARY KEY (Id_Prof, Id_Turma, Id_Disc),
    FOREIGN KEY (Id_Prof) REFERENCES Professor(Id_Prof)
        ON DELETE CASCADE,
    FOREIGN KEY (Id_Turma) REFERENCES Turma(Id_Turma)
        ON DELETE CASCADE,
    FOREIGN KEY (Id_Disc) REFERENCES Disciplina(Id_Disc)
        ON DELETE CASCADE
);

-- Tabela Matricula
CREATE TABLE IF NOT EXISTS Matricula (
    Id_Matricula INT PRIMARY KEY AUTO_INCREMENT,
    Data DATE,
    Id_Aluno INT,
    Id_Turma INT,
    FOREIGN KEY (Id_Aluno) REFERENCES Aluno(Id_Aluno)
        ON DELETE SET NULL,
    FOREIGN KEY (Id_Turma) REFERENCES Turma(Id_Turma)
        ON DELETE SET NULL
);

-- Tabela Avaliacao
CREATE TABLE IF NOT EXISTS Avaliacao (
    Id_Avalia INT PRIMARY KEY AUTO_INCREMENT,
    Valor DECIMAL(4, 2) CHECK (Valor >= 0 AND Valor <= 10),
    Data DATE,
    Id_Aluno INT,
    Id_Disc INT,
    FOREIGN KEY (Id_Aluno) REFERENCES Aluno(Id_Aluno)
        ON DELETE SET NULL,
    FOREIGN KEY (Id_Disc) REFERENCES Disciplina(Id_Disc)
        ON DELETE SET NULL
);

-- Tabela Utiliza
CREATE TABLE IF NOT EXISTS Utiliza (
    Id_Turma INT,
    Id_Recurso INT,
    Data DATE NOT NULL,
    Hora TIME,
    Observacao VARCHAR(255),
    PRIMARY KEY (Id_Turma, Id_Recurso, Data),
    FOREIGN KEY (Id_Turma) REFERENCES Turma(Id_Turma),
    FOREIGN KEY (Id_Recurso) REFERENCES Recurso(Id_Recurso)
);

-- Tabela Telefone
CREATE TABLE IF NOT EXISTS Telefone (
    Numero VARCHAR(20),
    Id_Aluno INT,
    Id_Prof INT,
    PRIMARY KEY (Numero),
    FOREIGN KEY (Id_Aluno) REFERENCES Aluno(Id_Aluno)
        ON DELETE CASCADE,
    FOREIGN KEY (Id_Prof) REFERENCES Professor(Id_Prof)
        ON DELETE CASCADE
);

-- Tabela Email
CREATE TABLE IF NOT EXISTS Email (
    Email VARCHAR(255) PRIMARY KEY,
    Id_Aluno INT,
    Id_Prof INT,
    FOREIGN KEY (Id_Aluno) REFERENCES Aluno(Id_Aluno)
        ON DELETE CASCADE,
    FOREIGN KEY (Id_Prof) REFERENCES Professor(Id_Prof)
        ON DELETE CASCADE
);

-- Tabela Pagamento
CREATE TABLE IF NOT EXISTS Pagamento (
    Id_Pagamento INT PRIMARY KEY AUTO_INCREMENT,
    Status VARCHAR(50) DEFAULT 'Pendente' CHECK (Status IN ('Pendente','Pago','Atrasado')),
    Valor DECIMAL(10, 2) CHECK (Valor >= 0),
    Data DATE,
    Id_Aluno INT,
    FOREIGN KEY (Id_Aluno) REFERENCES Aluno(Id_Aluno)
        ON DELETE SET NULL
);

-- Tabela Proj_Extensao
CREATE TABLE IF NOT EXISTS Proj_Extensao (
    Id_Proj INT PRIMARY KEY AUTO_INCREMENT,
    Nome VARCHAR(255),
    Descricao VARCHAR(255),
    Id_Prof INT,
    CONSTRAINT fk_proj_professor
        FOREIGN KEY (Id_Prof) REFERENCES Professor(Id_Prof)
        ON DELETE SET NULL
);

-- Tabela Conselho
CREATE TABLE IF NOT EXISTS Conselho (
    Id_Conselho INT PRIMARY KEY AUTO_INCREMENT,
    Descricao VARCHAR(255),
    Data DATE,
    Id_Prof INT,
    FOREIGN KEY (Id_Prof) REFERENCES Professor(Id_Prof)
        ON DELETE SET NULL
);

-- Tabela Participa
CREATE TABLE IF NOT EXISTS Participa (
    Id_Prof INT,
    Id_Conselho INT,
    PRIMARY KEY (Id_Prof, Id_Conselho),
    FOREIGN KEY (Id_Prof) REFERENCES Professor(Id_Prof)
        ON DELETE CASCADE,
    FOREIGN KEY (Id_Conselho) REFERENCES Conselho(Id_Conselho)
        ON DELETE CASCADE
);

-- ============================================================================
-- 4. CRIAR ÍNDICES
-- ============================================================================

CREATE INDEX IF NOT EXISTS idx_aluno_nome ON Aluno (Nome);
CREATE INDEX IF NOT EXISTS idx_aluno_media ON Aluno (Media);

-- ============================================================================
-- 5. CRIAR TABELAS AUXILIARES (para triggers e procedimentos)
-- ============================================================================

-- Tabela Resumo_Conselhos
CREATE TABLE IF NOT EXISTS Resumo_Conselhos (
    Id_Prof INT PRIMARY KEY,
    Total_Conselhos INT
);

-- Tabela Log_Pagamento
CREATE TABLE IF NOT EXISTS Log_Pagamento (
    Id_Log INT AUTO_INCREMENT PRIMARY KEY,
    Id_Pagamento INT,
    Status VARCHAR(50),
    Data_Registro DATETIME
);

-- ============================================================================
-- 6. CRIAR VIEWS
-- ============================================================================

-- View: Detalhes Acadêmicos do Aluno (MODIFICADA: removido nome_disciplina, Nota_específica, nome_professor; adicionado telefone e sexo)
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
GROUP BY a.Id_Aluno, a.Nome, a.Media, a.Sexo, t.Nome, t.Ano;

-- View: Perfil Completo do Professor
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
LEFT JOIN Proj_Extensao pe ON p.Id_Prof = pe.Id_Prof;

-- ============================================================================
-- 7. CRIAR FUNÇÕES
-- ============================================================================

DROP FUNCTION IF EXISTS situacaoAluno;

DELIMITER $$

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
END$$

DELIMITER ;

-- Função calcularMediaDisciplina REMOVIDA conforme solicitado

-- ============================================================================
-- 8. CRIAR PROCEDIMENTOS
-- ============================================================================

DROP PROCEDURE IF EXISTS updateFrequenciaAluno;

DELIMITER $$

CREATE PROCEDURE updateFrequenciaAluno(IN idAluno INT, IN frequenciaNova DECIMAL(5,2))
BEGIN
    UPDATE Aluno
    SET Frequencia = frequenciaNova
    WHERE Id_Aluno = idAluno;
END$$

DELIMITER ;

DROP PROCEDURE IF EXISTS contarConselhosPorProfessor;

DELIMITER $$

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
END$$

DELIMITER ;

DROP PROCEDURE IF EXISTS calcularMediaTurma;

DELIMITER $$

CREATE PROCEDURE calcularMediaTurma(IN idTurma INT)
BEGIN
    SELECT COALESCE(AVG(a.Media), 0) AS mediaTurma
    FROM Aluno a
    JOIN Matricula m ON a.Id_Aluno = m.Id_Aluno
    WHERE m.Id_Turma = idTurma
    AND a.Media IS NOT NULL
    AND a.Media > 0;
END$$

DELIMITER ;

-- ============================================================================
-- 9. CRIAR TRIGGERS
-- ============================================================================

DROP TRIGGER IF EXISTS logPagamento;

DELIMITER $$

CREATE TRIGGER logPagamento
AFTER INSERT ON Pagamento
FOR EACH ROW
BEGIN
    INSERT INTO Log_Pagamento (Id_Pagamento, Status, Data_Registro)
    VALUES (NEW.Id_Pagamento, NEW.Status, NOW());
END$$

DELIMITER ;

DROP TRIGGER IF EXISTS recalcularMediaGeralAluno;

DELIMITER $$

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
END$$

DELIMITER ;

-- ============================================================================
-- FIM DO SCRIPT
-- ============================================================================

SELECT 'Banco de dados inicializado com sucesso!' AS Status;



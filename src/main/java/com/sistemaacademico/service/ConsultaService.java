package com.sistemaacademico.service;

import com.sistemaacademico.model.Aluno;
import com.sistemaacademico.model.Disciplina;
import com.sistemaacademico.model.Professor;
import com.sistemaacademico.dto.ConsultaResultadoDTO;
import com.sistemaacademico.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    // ========== SERVIÇOS PARA ALUNOS (Corrigido) ==========
    
    public Aluno criarAluno(Aluno aluno) {
        int linhasAfetadas = consultaRepository.inserirAluno(aluno);
        if (linhasAfetadas > 0) {
            // Nota: O ID auto-incrementado não é retornado aqui facilmente sem JDBC avançado.
            // Retornar o objeto de entrada é suficiente para o CRUD básico.
            return aluno;
        }
        throw new RuntimeException("Erro ao criar aluno");
    }

    public List<Aluno> listarAlunos() {
        return consultaRepository.listarTodosAlunos();
    }

    public Aluno buscarAlunoPorId(int id) {
        return consultaRepository.buscarAlunoPorId(id);
    }

    public Aluno atualizarAluno(Aluno aluno) {
        // Buscar aluno existente para preencher campos não fornecidos
        Aluno alunoExistente = consultaRepository.buscarAlunoPorId(aluno.getIdAluno());
        if (alunoExistente == null) {
            throw new RuntimeException("Aluno não encontrado");
        }
        
        // Mesclar dados: usar valores fornecidos ou manter os existentes
        if (aluno.getNome() == null || aluno.getNome().trim().isEmpty()) {
            aluno.setNome(alunoExistente.getNome());
        }
        if (aluno.getSexo() == null || aluno.getSexo().trim().isEmpty()) {
            aluno.setSexo(alunoExistente.getSexo());
        }
        if (aluno.getDataNasc() == null) {
            aluno.setDataNasc(alunoExistente.getDataNasc());
        }
        if (aluno.getIdade() == 0) {
            aluno.setIdade(alunoExistente.getIdade());
        }
        if (aluno.getNum() == 0) {
            aluno.setNum(alunoExistente.getNum());
        }
        if (aluno.getCep() == null || aluno.getCep().trim().isEmpty()) {
            aluno.setCep(alunoExistente.getCep());
        }
        if (aluno.getRua() == null || aluno.getRua().trim().isEmpty()) {
            aluno.setRua(alunoExistente.getRua());
        }
        if (aluno.getMedia() == 0.0) {
            aluno.setMedia(alunoExistente.getMedia());
        }
        if (aluno.getFrequencia() == 0.0) {
            aluno.setFrequencia(alunoExistente.getFrequencia());
        }
        
        int linhasAfetadas = consultaRepository.atualizarAluno(aluno);
        if (linhasAfetadas > 0) {
            return aluno;
        }
        throw new RuntimeException("Erro ao atualizar aluno");
    }

    public boolean deletarAluno(int id) {
        int linhasAfetadas = consultaRepository.deletarAluno(id);
        return linhasAfetadas > 0;
    }

    // ========== SERVIÇOS PARA DISCIPLINAS (Corrigido) ==========
    
    public Disciplina criarDisciplina(Disciplina disciplina) {
        int linhasAfetadas = consultaRepository.inserirDisciplina(disciplina);
        if (linhasAfetadas > 0) {
            return disciplina;
        }
        throw new RuntimeException("Erro ao criar disciplina");
    }

    public List<Disciplina> listarDisciplinas() {
        return consultaRepository.listarTodasDisciplinas();
    }

    public Disciplina buscarDisciplinaPorId(int id) {
        return consultaRepository.buscarDisciplinaPorId(id);
    }

    public Disciplina atualizarDisciplina(Disciplina disciplina) {
        int linhasAfetadas = consultaRepository.atualizarDisciplina(disciplina);
        if (linhasAfetadas > 0) {
            return disciplina;
        }
        throw new RuntimeException("Erro ao atualizar disciplina");
    }

    public boolean deletarDisciplina(int id) {
        int linhasAfetadas = consultaRepository.deletarDisciplina(id);
        return linhasAfetadas > 0;
    }

    // ========== SERVIÇOS PARA AVALIAÇÕES ==========
    
    public com.sistemaacademico.model.Avaliacao criarAvaliacao(com.sistemaacademico.model.Avaliacao avaliacao) {
        int linhasAfetadas = consultaRepository.inserirAvaliacao(avaliacao);
        if (linhasAfetadas > 0) {
            return avaliacao;
        }
        throw new RuntimeException("Erro ao criar avaliação");
    }

    public List<com.sistemaacademico.model.Avaliacao> listarAvaliacoes() {
        return consultaRepository.listarTodasAvaliacoes();
    }

    public com.sistemaacademico.model.Avaliacao buscarAvaliacaoPorId(int id) {
        return consultaRepository.buscarAvaliacaoPorId(id);
    }

    public com.sistemaacademico.model.Avaliacao atualizarAvaliacao(com.sistemaacademico.model.Avaliacao avaliacao) {
        // Buscar avaliação existente para preencher campos não fornecidos
        com.sistemaacademico.model.Avaliacao avaliacaoExistente = consultaRepository.buscarAvaliacaoPorId(avaliacao.getIdAvalia());
        if (avaliacaoExistente == null) {
            throw new RuntimeException("Avaliação não encontrada");
        }
        
        // Mesclar dados: usar valores fornecidos ou manter os existentes
        if (avaliacao.getValor() == 0.0) {
            avaliacao.setValor(avaliacaoExistente.getValor());
        }
        if (avaliacao.getData() == null) {
            avaliacao.setData(avaliacaoExistente.getData());
        }
        if (avaliacao.getIdAluno() == 0) {
            avaliacao.setIdAluno(avaliacaoExistente.getIdAluno());
        }
        if (avaliacao.getIdDisc() == 0) {
            avaliacao.setIdDisc(avaliacaoExistente.getIdDisc());
        }
        
        int linhasAfetadas = consultaRepository.atualizarAvaliacao(avaliacao);
        if (linhasAfetadas > 0) {
            return avaliacao;
        }
        throw new RuntimeException("Erro ao atualizar avaliação");
    }

    public boolean deletarAvaliacao(int id) {
        int linhasAfetadas = consultaRepository.deletarAvaliacao(id);
        return linhasAfetadas > 0;
    }

    // ========== SERVIÇOS PARA PROFESSORES ==========
    
    public Professor criarProfessor(Professor professor) {
        int linhasAfetadas = consultaRepository.inserirProfessor(professor);
        if (linhasAfetadas > 0) {
            return professor;
        }
        throw new RuntimeException("Erro ao criar professor");
    }

    public List<Professor> listarProfessores() {
        return consultaRepository.listarTodosProfessores();
    }

    public Professor buscarProfessorPorId(int id) {
        return consultaRepository.buscarProfessorPorId(id);
    }

    public Professor atualizarProfessor(Professor professor) {
        // Buscar professor existente para preencher campos não fornecidos
        Professor professorExistente = consultaRepository.buscarProfessorPorId(professor.getIdProf());
        if (professorExistente == null) {
            throw new RuntimeException("Professor não encontrado");
        }
        
        // Mesclar dados: usar valores fornecidos ou manter os existentes
        if (professor.getNome() == null || professor.getNome().trim().isEmpty()) {
            professor.setNome(professorExistente.getNome());
        }
        if (professor.getCpf() == null || professor.getCpf().trim().isEmpty()) {
            professor.setCpf(professorExistente.getCpf());
        }
        if (professor.getRua() == null || professor.getRua().trim().isEmpty()) {
            professor.setRua(professorExistente.getRua());
        }
        if (professor.getNum() == 0) {
            professor.setNum(professorExistente.getNum());
        }
        if (professor.getCep() == null || professor.getCep().trim().isEmpty()) {
            professor.setCep(professorExistente.getCep());
        }
        
        int linhasAfetadas = consultaRepository.atualizarProfessor(professor);
        if (linhasAfetadas > 0) {
            return professor;
        }
        throw new RuntimeException("Erro ao atualizar professor");
    }

    public boolean deletarProfessor(int id) {
        int linhasAfetadas = consultaRepository.deletarProfessor(id);
        return linhasAfetadas > 0;
    }

    public boolean validarProfessor(Professor professor) {
        if (professor.getNome() == null || professor.getNome().trim().isEmpty()) {
            return false;
        }
        return true;
    }

    // ========== SERVIÇOS PARA DASHBOARD ==========
    
    public Map<String, Object> obterEstatisticasGerais() {
        Map<String, Object> estatisticas = new HashMap<>();
        
        estatisticas.put("totalAlunos", consultaRepository.contarTotalAlunos());
        estatisticas.put("totalDisciplinas", consultaRepository.contarTotalDisciplinas());
        estatisticas.put("mediaGeralNotas", consultaRepository.calcularMediaGeralNotas());
        estatisticas.put("alunosFrequenciaBaixa", consultaRepository.contarAlunosComFrequenciaBaixa());
        estatisticas.put("taxaAprovacao", consultaRepository.calcularTaxaAprovacao());
        
        return estatisticas;
    }

    public List<ConsultaResultadoDTO> obterDistribuicaoPorSexo() {
        return consultaRepository.obterDistribuicaoPorSexo();
    }

    public List<ConsultaResultadoDTO> obterDistribuicaoPorIdade() {
        return consultaRepository.obterDistribuicaoPorIdade();
    }

    public List<ConsultaResultadoDTO> obterTopDisciplinasCargaHoraria() {
        return consultaRepository.obterTopDisciplinasCargaHoraria();
    }

    public List<Aluno> obterTopAlunos() {
        return consultaRepository.obterTopAlunos();
    }

    // ========== CONSULTAS COMPLEXAS PARA ETAPA 03 ==========
    
    public List<Map<String, Object>> obterAlunosComDisciplinas() {
        return consultaRepository.obterAlunosComDisciplinas();
    }

    public List<Map<String, Object>> obterDistribuicaoPorIdadeESexo() {
        return consultaRepository.obterDistribuicaoPorIdadeESexo();
    }

    public List<Map<String, Object>> obterAnaliseDesempenhoDisciplinas() {
        return consultaRepository.obterAnaliseDesempenhoDisciplinas();
    }

    public List<Map<String, Object>> obterSituacaoAcademicaCompleta() {
        return consultaRepository.obterSituacaoAcademicaCompleta();
    }

    public List<Map<String, Object>> obterRankingProfessores() {
        return consultaRepository.obterRankingProfessores();
    }

    // ========== SERVIÇOS DE VALIDAÇÃO (Ajustados) ==========
    
    public boolean validarAluno(Aluno aluno) {
        if (aluno.getNome() == null || aluno.getNome().trim().isEmpty()) {
            return false;
        }
        if (aluno.getIdade() < 16) {
            return false;
        }
        // A validação de Sexo 'M'/'F' é feita pelo CHECK do banco,
        // mas poderia ser feita aqui também.
        // if (aluno.getSexo() == null || (!aluno.getSexo().equals("M") && !aluno.getSexo().equals("F"))) {
        //     return false;
        // }
        // A validação de Data_Nasc também seria importante.
        return true;
    }

    public boolean validarDisciplina(Disciplina disciplina) {
        if (disciplina.getNome() == null || disciplina.getNome().trim().isEmpty()) {
            return false;
        }
        if (disciplina.getCargaHoraria() < 20 || disciplina.getCargaHoraria() > 120) {
            return false;
        }
        return true;
    }

    // ========== MÉTODOS PARA GRÁFICOS (Sem alterações) ==========
    
    public List<Map<String, Object>> obterMediaPorFrequenciaEstudo() {
        return consultaRepository.obterMediaPorFrequenciaEstudo();
    }

    public List<Map<String, Object>> obterEstresseVsProjetos() {
        return consultaRepository.obterEstresseVsProjetos();
    }

    public List<Map<String, Object>> obterSuporteProfVsProjetos() {
        return consultaRepository.obterSuporteProfVsProjetos();
    }

    public List<Map<String, Object>> obterFrequenciaVsDisciplinas() {
        return consultaRepository.obterFrequenciaVsDisciplinas();
    }

    public List<Map<String, Object>> obterValorVsMedia() {
        return consultaRepository.obterValorVsMedia();
    }

    public List<Map<String, Object>> obterDistribuicaoEmail() {
        return consultaRepository.obterDistribuicaoEmail();
    }

    public List<Map<String, Object>> obterDistribuicaoPorGenero() {
        return consultaRepository.obterDistribuicaoPorGenero();
    }

    public List<Map<String, Object>> obterDistribuicaoTelefone() {
        return consultaRepository.obterDistribuicaoTelefone();
    }

    public List<Map<String, Object>> obterDistribuicaoProjetos() {
        return consultaRepository.obterDistribuicaoProjetos();
    }

    public List<Map<String, Object>> obterDistribuicaoFreqEstudo() {
        return consultaRepository.obterDistribuicaoFreqEstudo();
    }

    public List<Map<String, Object>> obterDistribuicaoIdadeGrafico() {
        return consultaRepository.obterDistribuicaoIdadeGrafico();
    }

    public List<Map<String, Object>> obterDistribuicaoUsoRecursos() {
        return consultaRepository.obterDistribuicaoUsoRecursos();
    }

    public List<Map<String, Object>> obterDadosMonitoria() {
        return consultaRepository.obterDadosMonitoria();
    }

    public List<Map<String, Object>> obterFreqRecursosVsEstudo() {
        return consultaRepository.obterFreqRecursosVsEstudo();
    }

    // Método para consultas complexas
    public List<Map<String, Object>> obterConsultaComplexa(String tipoConsulta) {
        switch (tipoConsulta) {
            case "alunos-disciplinas":
                return consultaRepository.obterAlunosComDisciplinas();
            case "distribuicao-idade-sexo":
                return consultaRepository.obterDistribuicaoPorIdadeESexo();
            case "analise-desempenho":
                return consultaRepository.obterAnaliseDesempenhoDisciplinas();
            case "situacao-academica":
                return consultaRepository.obterSituacaoAcademicaCompleta();
            case "ranking-professores":
                return consultaRepository.obterRankingProfessores();
            default:
                return Collections.emptyList();
        }
    }
}
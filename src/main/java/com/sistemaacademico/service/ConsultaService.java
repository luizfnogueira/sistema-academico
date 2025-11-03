package com.sistemaacademico.service;

import com.sistemaacademico.model.Aluno;
import com.sistemaacademico.model.Disciplina;
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
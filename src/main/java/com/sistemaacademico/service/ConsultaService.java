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

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    // ========== SERVIÇOS PARA ALUNOS ==========
    
    public Aluno criarAluno(Aluno aluno) {
        int linhasAfetadas = consultaRepository.inserirAluno(aluno);
        if (linhasAfetadas > 0) {
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

    // ========== SERVIÇOS PARA DISCIPLINAS ==========
    
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

    // ========== SERVIÇOS DE VALIDAÇÃO ==========
    
    public boolean validarAluno(Aluno aluno) {
        if (aluno.getNome() == null || aluno.getNome().trim().isEmpty()) {
            return false;
        }
        if (aluno.getIdade() < 16) {
            return false;
        }
        if (aluno.getSexo() == null || (!aluno.getSexo().equals("M") && !aluno.getSexo().equals("F"))) {
            return false;
        }
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
}

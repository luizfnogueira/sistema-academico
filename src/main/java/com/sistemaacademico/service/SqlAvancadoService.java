package com.sistemaacademico.service;

import com.sistemaacademico.repository.SqlAvancadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SqlAvancadoService {

    @Autowired
    private SqlAvancadoRepository sqlAvancadoRepository;

    // ========== SERVIÇOS DE INICIALIZAÇÃO ==========
    
    public void inicializarEstruturasAvancadas() {
        sqlAvancadoRepository.inicializarEstruturasAvancadas();
    }

    // ========== SERVIÇOS DE CONSULTAS ==========
    
    public List<Map<String, Object>> buscarProfessoresSemOfertas() {
        return sqlAvancadoRepository.buscarProfessoresSemOfertas();
    }

    public List<Map<String, Object>> buscarAlunosEProfessoresComEmails() {
        return sqlAvancadoRepository.buscarAlunosEProfessoresComEmails();
    }

    public List<Map<String, Object>> buscarAlunosAcimaDaMedia() {
        return sqlAvancadoRepository.buscarAlunosAcimaDaMedia();
    }

    public List<Map<String, Object>> buscarAlunosTurmas2023() {
        return sqlAvancadoRepository.buscarAlunosTurmas2023();
    }

    // ========== SERVIÇOS DE VIEWS ==========
    
    public List<Map<String, Object>> consultarDetalhesAcademicosAluno() {
        return sqlAvancadoRepository.consultarViewDetalhesAcademicosAluno();
    }

    public List<Map<String, Object>> consultarDetalhesAcademicosAlunoPorId(int idAluno) {
        return sqlAvancadoRepository.consultarViewDetalhesAcademicosAlunoPorId(idAluno);
    }

    public List<Map<String, Object>> consultarPerfilCompletoProfessor() {
        return sqlAvancadoRepository.consultarViewPerfilCompletoProfessor();
    }

    public List<Map<String, Object>> consultarPerfilCompletoProfessorPorId(int idProf) {
        return sqlAvancadoRepository.consultarViewPerfilCompletoProfessorPorId(idProf);
    }

    // ========== SERVIÇOS DE FUNÇÕES ==========
    
    public String obterSituacaoAluno(int idAluno) {
        return sqlAvancadoRepository.chamarFuncaoSituacaoAluno(idAluno);
    }

    // Função calcularMediaDisciplina removida

    // ========== SERVIÇOS DE PROCEDIMENTOS ==========
    
    public void atualizarFrequenciaAluno(int idAluno, double frequenciaNova) {
        sqlAvancadoRepository.chamarProcedimentoUpdateFrequenciaAluno(idAluno, frequenciaNova);
    }

    public void contarConselhosPorProfessor() {
        sqlAvancadoRepository.chamarProcedimentoContarConselhosPorProfessor();
    }

    public List<Map<String, Object>> consultarResumoConselhos() {
        return sqlAvancadoRepository.consultarResumoConselhos();
    }

    // ========== SERVIÇOS DE LOGS ==========
    
    public List<Map<String, Object>> consultarLogPagamento() {
        return sqlAvancadoRepository.consultarLogPagamento();
    }
    
    // ========== SERVIÇOS DE CONSELHOS ==========
    
    public List<Map<String, Object>> consultarConselhosPorProfessor(int idProf) {
        return sqlAvancadoRepository.consultarConselhosPorProfessor(idProf);
    }
    
    public int criarConselhoEAtribuir(int idProf, String descricao, java.sql.Date data) {
        return sqlAvancadoRepository.criarConselhoEAtribuir(idProf, descricao, data);
    }
    
    // ========== SERVIÇOS DE PROCEDIMENTOS ==========
    
    public Double calcularMediaTurma(int idTurma) {
        return sqlAvancadoRepository.chamarProcedimentoCalcularMediaTurma(idTurma);
    }
}



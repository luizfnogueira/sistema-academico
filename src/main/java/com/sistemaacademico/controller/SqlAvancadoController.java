package com.sistemaacademico.controller;

import com.sistemaacademico.service.SqlAvancadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sql-avancado")
public class SqlAvancadoController {

    @Autowired
    private SqlAvancadoService sqlAvancadoService;

    // ========== ENDPOINT DE INICIALIZAÇÃO ==========
    
    @PostMapping("/inicializar")
    public ResponseEntity<Map<String, Object>> inicializarEstruturas() {
        Map<String, Object> response = new HashMap<>();
        try {
            sqlAvancadoService.inicializarEstruturasAvancadas();
            response.put("success", true);
            response.put("message", "Estruturas avançadas inicializadas com sucesso!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Erro ao inicializar: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // ========== ENDPOINTS DE CONSULTAS ==========
    
    @GetMapping("/professores-sem-ofertas")
    public ResponseEntity<List<Map<String, Object>>> professoresSemOfertas() {
        return ResponseEntity.ok(sqlAvancadoService.buscarProfessoresSemOfertas());
    }

    @GetMapping("/alunos-professores-emails")
    public ResponseEntity<List<Map<String, Object>>> alunosEProfessoresComEmails() {
        return ResponseEntity.ok(sqlAvancadoService.buscarAlunosEProfessoresComEmails());
    }

    @GetMapping("/alunos-acima-media")
    public ResponseEntity<List<Map<String, Object>>> alunosAcimaDaMedia() {
        return ResponseEntity.ok(sqlAvancadoService.buscarAlunosAcimaDaMedia());
    }

    @GetMapping("/alunos-turmas-2023")
    public ResponseEntity<List<Map<String, Object>>> alunosTurmas2023() {
        return ResponseEntity.ok(sqlAvancadoService.buscarAlunosTurmas2023());
    }

    // ========== ENDPOINTS DE VIEWS ==========
    
    @GetMapping("/view/detalhes-academicos-aluno")
    public ResponseEntity<List<Map<String, Object>>> detalhesAcademicosAluno() {
        return ResponseEntity.ok(sqlAvancadoService.consultarDetalhesAcademicosAluno());
    }

    @GetMapping("/view/detalhes-academicos-aluno/{idAluno}")
    public ResponseEntity<List<Map<String, Object>>> detalhesAcademicosAlunoPorId(@PathVariable int idAluno) {
        return ResponseEntity.ok(sqlAvancadoService.consultarDetalhesAcademicosAlunoPorId(idAluno));
    }

    @GetMapping("/view/perfil-completo-professor")
    public ResponseEntity<List<Map<String, Object>>> perfilCompletoProfessor() {
        return ResponseEntity.ok(sqlAvancadoService.consultarPerfilCompletoProfessor());
    }

    @GetMapping("/view/perfil-completo-professor/{idProf}")
    public ResponseEntity<List<Map<String, Object>>> perfilCompletoProfessorPorId(@PathVariable int idProf) {
        return ResponseEntity.ok(sqlAvancadoService.consultarPerfilCompletoProfessorPorId(idProf));
    }

    // ========== ENDPOINTS DE FUNÇÕES ==========
    
    @GetMapping("/funcao/situacao-aluno/{idAluno}")
    public ResponseEntity<Map<String, Object>> situacaoAluno(@PathVariable int idAluno) {
        Map<String, Object> response = new HashMap<>();
        try {
            String situacao = sqlAvancadoService.obterSituacaoAluno(idAluno);
            response.put("idAluno", idAluno);
            response.put("situacao", situacao);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/funcao/media-disciplina")
    public ResponseEntity<Map<String, Object>> calcularMediaDisciplina(
            @RequestParam int idDisciplina,
            @RequestParam int idAluno) {
        Map<String, Object> response = new HashMap<>();
        try {
            Double media = sqlAvancadoService.calcularMediaDisciplina(idDisciplina, idAluno);
            response.put("idDisciplina", idDisciplina);
            response.put("idAluno", idAluno);
            response.put("media", media);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // ========== ENDPOINTS DE PROCEDIMENTOS ==========
    
    @PutMapping("/procedimento/atualizar-frequencia")
    public ResponseEntity<Map<String, Object>> atualizarFrequencia(
            @RequestParam int idAluno,
            @RequestParam double frequenciaNova) {
        Map<String, Object> response = new HashMap<>();
        try {
            sqlAvancadoService.atualizarFrequenciaAluno(idAluno, frequenciaNova);
            response.put("success", true);
            response.put("message", "Frequência atualizada com sucesso!");
            response.put("idAluno", idAluno);
            response.put("frequenciaNova", frequenciaNova);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/procedimento/contar-conselhos")
    public ResponseEntity<Map<String, Object>> contarConselhos() {
        Map<String, Object> response = new HashMap<>();
        try {
            sqlAvancadoService.contarConselhosPorProfessor();
            response.put("success", true);
            response.put("message", "Conselhos contados com sucesso!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/resumo-conselhos")
    public ResponseEntity<List<Map<String, Object>>> resumoConselhos() {
        return ResponseEntity.ok(sqlAvancadoService.consultarResumoConselhos());
    }

    // ========== ENDPOINTS DE LOGS ==========
    
    @GetMapping("/log-pagamento")
    public ResponseEntity<List<Map<String, Object>>> logPagamento() {
        return ResponseEntity.ok(sqlAvancadoService.consultarLogPagamento());
    }
}



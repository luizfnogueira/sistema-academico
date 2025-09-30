package com.sistemaacademico.controller;

import com.sistemaacademico.model.Aluno;
import com.sistemaacademico.model.Disciplina;
import com.sistemaacademico.service.ConsultaService;
import com.sistemaacademico.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AlunoController {

    @Autowired
    private ConsultaService consultaService;

    @Autowired
    private ConsultaRepository consultaRepository;

    // ========== ENDPOINTS PARA CRIAÇÃO DE TABELAS ==========

    @GetMapping("/criar-tabelas")
    public ResponseEntity<String> criarTabelas() {
        try {
        consultaRepository.criarTabelaAluno();
        consultaRepository.criarTabelaProfessor();
        consultaRepository.criarTabelaEfetivado();
        consultaRepository.criarTabelaTemporario();
        consultaRepository.criarTabelaPesquisa();
        consultaRepository.criarTabelaMatricula();
        consultaRepository.criarTabelaDisciplina();
        consultaRepository.criarTabelaAvaliacao();
        consultaRepository.criarTabelaPagamento();
        consultaRepository.criarTabelaProjExtensao();
        consultaRepository.criarTabelaConselho();
        consultaRepository.criarTabelaTelefone();
        consultaRepository.criarTabelaEmail();
            return ResponseEntity.ok("Tabelas criadas com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar tabelas: " + e.getMessage());
        }
    }

    // ========== ENDPOINTS CRUD PARA ALUNOS ==========
    
    @PostMapping("/alunos")
    public ResponseEntity<?> criarAluno(@RequestBody Aluno aluno) {
        try {
            if (!consultaService.validarAluno(aluno)) {
                return ResponseEntity.badRequest().body("Dados do aluno inválidos");
            }
            Aluno alunoCriado = consultaService.criarAluno(aluno);
            return ResponseEntity.status(HttpStatus.CREATED).body(alunoCriado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar aluno: " + e.getMessage());
        }
    }

    @GetMapping("/alunos")
    public ResponseEntity<List<Aluno>> listarAlunos() {
        try {
            List<Aluno> alunos = consultaService.listarAlunos();
            return ResponseEntity.ok(alunos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/alunos/{id}")
    public ResponseEntity<Aluno> buscarAlunoPorId(@PathVariable int id) {
        try {
            Aluno aluno = consultaService.buscarAlunoPorId(id);
            if (aluno != null) {
                return ResponseEntity.ok(aluno);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/alunos/{id}")
    public ResponseEntity<?> atualizarAluno(@PathVariable int id, @RequestBody Aluno aluno) {
        try {
            if (!consultaService.validarAluno(aluno)) {
                return ResponseEntity.badRequest().body("Dados do aluno inválidos");
            }
            aluno.setIdAluno(id);
            Aluno alunoAtualizado = consultaService.atualizarAluno(aluno);
            return ResponseEntity.ok(alunoAtualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar aluno: " + e.getMessage());
        }
    }

    @DeleteMapping("/alunos/{id}")
    public ResponseEntity<String> deletarAluno(@PathVariable int id) {
        try {
            boolean deletado = consultaService.deletarAluno(id);
            if (deletado) {
                return ResponseEntity.ok("Aluno deletado com sucesso");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar aluno: " + e.getMessage());
        }
    }

    // ========== ENDPOINTS CRUD PARA DISCIPLINAS ==========
    
    @PostMapping("/disciplinas")
    public ResponseEntity<?> criarDisciplina(@RequestBody Disciplina disciplina) {
        try {
            if (!consultaService.validarDisciplina(disciplina)) {
                return ResponseEntity.badRequest().body("Dados da disciplina inválidos");
            }
            Disciplina disciplinaCriada = consultaService.criarDisciplina(disciplina);
            return ResponseEntity.status(HttpStatus.CREATED).body(disciplinaCriada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar disciplina: " + e.getMessage());
        }
    }

    @GetMapping("/disciplinas")
    public ResponseEntity<List<Disciplina>> listarDisciplinas() {
        try {
            List<Disciplina> disciplinas = consultaService.listarDisciplinas();
            return ResponseEntity.ok(disciplinas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/disciplinas/{id}")
    public ResponseEntity<Disciplina> buscarDisciplinaPorId(@PathVariable int id) {
        try {
            Disciplina disciplina = consultaService.buscarDisciplinaPorId(id);
            if (disciplina != null) {
                return ResponseEntity.ok(disciplina);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/disciplinas/{id}")
    public ResponseEntity<?> atualizarDisciplina(@PathVariable int id, @RequestBody Disciplina disciplina) {
        try {
            if (!consultaService.validarDisciplina(disciplina)) {
                return ResponseEntity.badRequest().body("Dados da disciplina inválidos");
            }
            disciplina.setIdDisc(id);
            Disciplina disciplinaAtualizada = consultaService.atualizarDisciplina(disciplina);
            return ResponseEntity.ok(disciplinaAtualizada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar disciplina: " + e.getMessage());
        }
    }

    @DeleteMapping("/disciplinas/{id}")
    public ResponseEntity<String> deletarDisciplina(@PathVariable int id) {
        try {
            boolean deletado = consultaService.deletarDisciplina(id);
            if (deletado) {
                return ResponseEntity.ok("Disciplina deletada com sucesso");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar disciplina: " + e.getMessage());
        }
    }

    // ========== ENDPOINTS PARA DASHBOARD ==========
    
    @GetMapping("/dashboard/estatisticas")
    public ResponseEntity<Map<String, Object>> obterEstatisticasGerais() {
        try {
            Map<String, Object> estatisticas = consultaService.obterEstatisticasGerais();
            return ResponseEntity.ok(estatisticas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/dashboard/distribuicao-sexo")
    public ResponseEntity<List<com.sistemaacademico.dto.ConsultaResultadoDTO>> obterDistribuicaoPorSexo() {
        try {
            List<com.sistemaacademico.dto.ConsultaResultadoDTO> distribuicao = consultaService.obterDistribuicaoPorSexo();
            return ResponseEntity.ok(distribuicao);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/dashboard/distribuicao-idade")
    public ResponseEntity<List<com.sistemaacademico.dto.ConsultaResultadoDTO>> obterDistribuicaoPorIdade() {
        try {
            List<com.sistemaacademico.dto.ConsultaResultadoDTO> distribuicao = consultaService.obterDistribuicaoPorIdade();
            return ResponseEntity.ok(distribuicao);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/dashboard/top-disciplinas")
    public ResponseEntity<List<com.sistemaacademico.dto.ConsultaResultadoDTO>> obterTopDisciplinas() {
        try {
            List<com.sistemaacademico.dto.ConsultaResultadoDTO> disciplinas = consultaService.obterTopDisciplinasCargaHoraria();
            return ResponseEntity.ok(disciplinas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/dashboard/top-alunos")
    public ResponseEntity<List<Aluno>> obterTopAlunos() {
        try {
            List<Aluno> alunos = consultaService.obterTopAlunos();
            return ResponseEntity.ok(alunos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ========== ENDPOINTS PARA CONSULTAS COMPLEXAS (ETAPA 03) ==========
    
    @GetMapping("/consultas/alunos-disciplinas")
    public ResponseEntity<List<Map<String, Object>>> obterAlunosComDisciplinas() {
        try {
            List<Map<String, Object>> resultado = consultaService.obterAlunosComDisciplinas();
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/consultas/distribuicao-idade-sexo")
    public ResponseEntity<List<Map<String, Object>>> obterDistribuicaoPorIdadeESexo() {
        try {
            List<Map<String, Object>> resultado = consultaService.obterDistribuicaoPorIdadeESexo();
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/consultas/analise-disciplinas")
    public ResponseEntity<List<Map<String, Object>>> obterAnaliseDesempenhoDisciplinas() {
        try {
            List<Map<String, Object>> resultado = consultaService.obterAnaliseDesempenhoDisciplinas();
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/consultas/situacao-academica")
    public ResponseEntity<List<Map<String, Object>>> obterSituacaoAcademicaCompleta() {
        try {
            List<Map<String, Object>> resultado = consultaService.obterSituacaoAcademicaCompleta();
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/consultas/ranking-professores")
    public ResponseEntity<List<Map<String, Object>>> obterRankingProfessores() {
        try {
            List<Map<String, Object>> resultado = consultaService.obterRankingProfessores();
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

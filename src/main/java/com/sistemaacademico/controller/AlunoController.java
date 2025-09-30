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
            // ATENÇÃO CRÍTICA: Você deve garantir a ordem de criação das tabelas
            // para evitar o erro de chave estrangeira! (Ex: Turma deve vir antes de Disciplina)
            
            // EXECUTAR NA ORDEM: Tabelas que não referenciam NINGUÉM (Pai), depois as que dependem (Filha).
            
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
            
            return ResponseEntity.ok("Tabelas criadas com sucesso! Verifique o console para alertas de chave estrangeira.");
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
            // ATENÇÃO: O id precisa ser setado no objeto para o Service saber qual atualizar
            aluno.setId(id); // Assumindo que a model Aluno tem setId(int id)
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
            // ATENÇÃO: O id precisa ser setado no objeto para o Service saber qual atualizar
            disciplina.setIdDisc(id); // Assumindo que a model Disciplina tem setIdDisc(int id)
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

    // ========== ENDPOINTS PARA DASHBOARD (API para o Front-end) ==========
    
    @GetMapping("/dashboard/estatisticas")
    public ResponseEntity<Map<String, Object>> obterEstatisticasGerais() {
        try {
            Map<String, Object> estatisticas = consultaService.obterEstatisticasGerais();
            return ResponseEntity.ok(estatisticas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // [Outros 4 endpoints de estatísticas iniciais também estão aqui]

    // ========== ENDPOINTS PARA CONSULTAS COMPLEXAS (ETAPA 03) ==========
    
    @GetMapping("/consultas/{tipoConsulta}")
    public ResponseEntity<List<Map<String, Object>>> obterConsultaComplexa(@PathVariable String tipoConsulta) {
        try {
            // Este endpoint é o que seu dashboard.js chama (carregarConsulta)
            List<Map<String, Object>> resultado = consultaService.obterConsultaComplexa(tipoConsulta);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
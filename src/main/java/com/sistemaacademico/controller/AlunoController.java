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

    // ========== ENDPOINTS PARA CRIAÇÃO DE TABELAS (ORDEM CORRIGIDA) ==========

    @GetMapping("/criar-tabelas")
    public ResponseEntity<String> criarTabelas() {
        try {
            // Ordem de criação corrigida para evitar erros de Foreign Key
            
            // Grupo 1: Sem dependências
            consultaRepository.criarTabelaAluno();
            consultaRepository.criarTabelaProfessor();
            consultaRepository.criarTabelaTurma();
            consultaRepository.criarTabelaRecurso();
            consultaRepository.criarTabelaDisciplina();

            // Grupo 2: Dependem do Grupo 1
            consultaRepository.criarTabelaConselho(); // Depende de Professor
            consultaRepository.criarTabelaEfetivado(); // Depende de Professor
            consultaRepository.criarTabelaTemporario(); // Depende de Professor
            consultaRepository.criarTabelaProjExtensao(); // Depende de Professor (Opção 2)
            consultaRepository.criarTabelaPesquisa(); // Depende de Aluno
            consultaRepository.criarTabelaPagamento(); // Depende de Aluno
            consultaRepository.criarTabelaTelefone(); // Depende de Aluno, Professor
            consultaRepository.criarTabelaEmail(); // Depende de Aluno, Professor
            consultaRepository.criarTabelaMatricula(); // Depende de Aluno, Turma
            consultaRepository.criarTabelaAvaliacao(); // Depende de Aluno, Disciplina

            // Grupo 3: Dependem do Grupo 1 e 2 (Tabelas de Ligação)
            consultaRepository.criarTabelaMonitora(); // Depende de Aluno
            consultaRepository.criarTabelaOferta(); // Depende de Professor, Turma, Disciplina
            consultaRepository.criarTabelaUtiliza(); // Depende de Turma, Recurso
            consultaRepository.criarTabelaParticipa(); // Depende de Professor, Conselho
            
            return ResponseEntity.ok("Todas as 19 tabelas foram criadas ou verificadas com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro grave ao criar tabelas: " + e.getMessage());
        }
    }

    // ========== ENDPOINTS CRUD PARA ALUNOS (Corrigidos) ==========
    
    @PostMapping("/alunos")
    public ResponseEntity<?> criarAluno(@RequestBody Aluno aluno) {
        try {
            // A validação no service precisa ser ajustada para Data_Nasc se necessário
            // if (!consultaService.validarAluno(aluno)) {
            //     return ResponseEntity.badRequest().body("Dados do aluno inválidos");
            // }
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
            // if (!consultaService.validarAluno(aluno)) {
            //     return ResponseEntity.badRequest().body("Dados do aluno inválidos");
            // }
            aluno.setIdAluno(id); // Usa o setIdAluno
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

    // ========== ENDPOINTS CRUD PARA DISCIPLINAS (Corrigidos) ==========
    
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
            disciplina.setIdDisc(id); // Usa o setIdDisc
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

    // ========== ENDPOINTS PARA CONSULTAS COMPLEXAS (ETAPA 03) ==========
    
    @GetMapping("/consultas/{tipoConsulta}")
    public ResponseEntity<List<Map<String, Object>>> obterConsultaComplexa(@PathVariable String tipoConsulta) {
        try {
            List<Map<String, Object>> resultado = consultaService.obterConsultaComplexa(tipoConsulta);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
package com.sistemaacademico.controller;

import com.sistemaacademico.model.Aluno;
import com.sistemaacademico.model.Disciplina;
import com.sistemaacademico.model.Professor;
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
            // Validações detalhadas
            if (aluno.getNome() == null || aluno.getNome().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Erro: Nome do aluno é obrigatório");
            }
            if (aluno.getDataNasc() == null) {
                return ResponseEntity.badRequest().body("Erro: Data de nascimento é obrigatória");
            }
            if (aluno.getIdade() < 16) {
                return ResponseEntity.badRequest().body("Erro: Idade mínima é 16 anos");
            }
            if (aluno.getSexo() == null || (!aluno.getSexo().equals("M") && !aluno.getSexo().equals("F"))) {
                return ResponseEntity.badRequest().body("Erro: Sexo deve ser 'M' ou 'F'");
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
            // Validações detalhadas
            if (disciplina.getNome() == null || disciplina.getNome().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Erro: Nome da disciplina é obrigatório");
            }
            if (disciplina.getCargaHoraria() < 20 || disciplina.getCargaHoraria() > 120) {
                return ResponseEntity.badRequest().body("Erro: Carga horária deve estar entre 20 e 120 horas");
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

    // ========== ENDPOINTS CRUD PARA AVALIAÇÕES ==========
    
    @PostMapping("/avaliacoes")
    public ResponseEntity<?> criarAvaliacao(@RequestBody com.sistemaacademico.model.Avaliacao avaliacao) {
        try {
            // Validações
            if (avaliacao.getValor() < 0 || avaliacao.getValor() > 10) {
                return ResponseEntity.badRequest().body("Erro: Valor da avaliação deve estar entre 0 e 10");
            }
            if (avaliacao.getIdAluno() <= 0) {
                return ResponseEntity.badRequest().body("Erro: ID do Aluno é obrigatório");
            }
            if (avaliacao.getIdDisc() <= 0) {
                return ResponseEntity.badRequest().body("Erro: ID da Disciplina é obrigatório");
            }
            if (avaliacao.getData() == null) {
                return ResponseEntity.badRequest().body("Erro: Data da avaliação é obrigatória");
            }
            
            com.sistemaacademico.model.Avaliacao avaliacaoCriada = consultaService.criarAvaliacao(avaliacao);
            return ResponseEntity.status(HttpStatus.CREATED).body(avaliacaoCriada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar avaliação: " + e.getMessage());
        }
    }

    @GetMapping("/avaliacoes")
    public ResponseEntity<List<com.sistemaacademico.model.Avaliacao>> listarAvaliacoes() {
        try {
            List<com.sistemaacademico.model.Avaliacao> avaliacoes = consultaService.listarAvaliacoes();
            return ResponseEntity.ok(avaliacoes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/avaliacoes/{id}")
    public ResponseEntity<com.sistemaacademico.model.Avaliacao> buscarAvaliacaoPorId(@PathVariable int id) {
        try {
            com.sistemaacademico.model.Avaliacao avaliacao = consultaService.buscarAvaliacaoPorId(id);
            if (avaliacao != null) {
                return ResponseEntity.ok(avaliacao);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/avaliacoes/{id}")
    public ResponseEntity<?> atualizarAvaliacao(@PathVariable int id, @RequestBody com.sistemaacademico.model.Avaliacao avaliacao) {
        try {
            // Validações
            if (avaliacao.getValor() != 0.0 && (avaliacao.getValor() < 0 || avaliacao.getValor() > 10)) {
                return ResponseEntity.badRequest().body("Erro: Valor da avaliação deve estar entre 0 e 10");
            }
            
            avaliacao.setIdAvalia(id);
            com.sistemaacademico.model.Avaliacao avaliacaoAtualizada = consultaService.atualizarAvaliacao(avaliacao);
            return ResponseEntity.ok(avaliacaoAtualizada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar avaliação: " + e.getMessage());
        }
    }

    @DeleteMapping("/avaliacoes/{id}")
    public ResponseEntity<String> deletarAvaliacao(@PathVariable int id) {
        try {
            boolean deletado = consultaService.deletarAvaliacao(id);
            if (deletado) {
                return ResponseEntity.ok("Avaliação deletada com sucesso");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar avaliação: " + e.getMessage());
        }
    }

    // ========== ENDPOINTS PARA MATRÍCULAS ==========
    
    @PostMapping("/matriculas")
    public ResponseEntity<?> criarMatricula(@RequestBody Map<String, Object> matricula) {
        try {
            int idAluno = Integer.parseInt(matricula.get("idAluno").toString());
            int idTurma = Integer.parseInt(matricula.get("idTurma").toString());
            String dataStr = matricula.get("data") != null ? matricula.get("data").toString() : null;
            
            java.sql.Date data = dataStr != null ? java.sql.Date.valueOf(dataStr) : new java.sql.Date(System.currentTimeMillis());
            
            consultaRepository.criarMatricula(idAluno, idTurma, data);
            return ResponseEntity.status(HttpStatus.CREATED).body("Matrícula criada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar matrícula: " + e.getMessage());
        }
    }

    // ========== ENDPOINTS PARA DEPENDENTES ==========
    
    @PostMapping("/dependentes")
    public ResponseEntity<?> criarDependente(@RequestBody Map<String, Object> dependente) {
        try {
            int idAluno = Integer.parseInt(dependente.get("idAluno").toString());
            String nome = dependente.get("nome").toString();
            String dataNascStr = dependente.get("dataNasc").toString();
            String parentesco = dependente.get("parentesco") != null ? dependente.get("parentesco").toString() : null;
            
            java.sql.Date dataNasc = java.sql.Date.valueOf(dataNascStr);
            
            consultaRepository.criarDependente(idAluno, nome, dataNasc, parentesco);
            return ResponseEntity.status(HttpStatus.CREATED).body("Dependente criado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar dependente: " + e.getMessage());
        }
    }

    // ========== ENDPOINTS CRUD PARA PROFESSORES ==========
    
    @PostMapping("/professores")
    public ResponseEntity<?> criarProfessor(@RequestBody Professor professor) {
        try {
            // Validações detalhadas
            if (professor.getNome() == null || professor.getNome().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Erro: Nome do professor é obrigatório");
            }
            
            Professor professorCriado = consultaService.criarProfessor(professor);
            return ResponseEntity.status(HttpStatus.CREATED).body(professorCriado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar professor: " + e.getMessage());
        }
    }

    @GetMapping("/professores")
    public ResponseEntity<List<Professor>> listarProfessores() {
        try {
            List<Professor> professores = consultaService.listarProfessores();
            return ResponseEntity.ok(professores);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/professores/{id}")
    public ResponseEntity<Professor> buscarProfessorPorId(@PathVariable int id) {
        try {
            Professor professor = consultaService.buscarProfessorPorId(id);
            if (professor != null) {
                return ResponseEntity.ok(professor);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/professores/{id}")
    public ResponseEntity<?> atualizarProfessor(@PathVariable int id, @RequestBody Professor professor) {
        try {
            professor.setIdProf(id);
            Professor professorAtualizado = consultaService.atualizarProfessor(professor);
            // Validar após mesclar com dados existentes
            if (!consultaService.validarProfessor(professorAtualizado)) {
                return ResponseEntity.badRequest().body("Dados do professor inválidos");
            }
            return ResponseEntity.ok(professorAtualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar professor: " + e.getMessage());
        }
    }

    @DeleteMapping("/professores/{id}")
    public ResponseEntity<String> deletarProfessor(@PathVariable int id) {
        try {
            boolean deletado = consultaService.deletarProfessor(id);
            if (deletado) {
                return ResponseEntity.ok("Professor deletado com sucesso");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar professor: " + e.getMessage());
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
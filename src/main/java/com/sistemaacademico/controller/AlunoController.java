package com.sistemaacademico.controller;

import com.sistemaacademico.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlunoController {

    @Autowired
    private ConsultaRepository consultaRepository;

    @GetMapping("/criar-tabelas")
    public String criarTabelas() {
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
        return "Tabelas criadas!";
    }
}

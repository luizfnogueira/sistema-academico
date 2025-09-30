package com.sistemaacademico.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("pageTitle", "Painel Acadêmico");
        model.addAttribute("consultas", new String[]{
                "Alunos por curso",
                "Notas pendentes de revisão",
                "Taxa de conclusão por semestre",
                "Média de notas por disciplina"
        });
        return "index";
    }

}

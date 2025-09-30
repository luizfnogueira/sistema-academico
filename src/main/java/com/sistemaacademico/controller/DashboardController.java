package com.sistemaacademico.controller;

import com.sistemaacademico.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class DashboardController {

    @Autowired
    private ConsultaService consultaService;

    @GetMapping("/")
    public String index(Model model) {
        try {
            // Buscar estatísticas reais do banco de dados
            Map<String, Object> estatisticas = consultaService.obterEstatisticasGerais();
            
            model.addAttribute("pageTitle", "Painel Acadêmico");
            model.addAttribute("estatisticas", estatisticas);
            model.addAttribute("consultas", new String[]{
                    "Total de alunos cadastrados",
                    "Média geral de notas",
                    "Taxa de aprovação",
                    "Alunos com frequência baixa",
                    "Distribuição por sexo",
                    "Distribuição por idade"
            });
            
            // Adicionar dados para gráficos
            model.addAttribute("distribuicaoSexo", consultaService.obterDistribuicaoPorSexo());
            model.addAttribute("distribuicaoIdade", consultaService.obterDistribuicaoPorIdade());
            model.addAttribute("topDisciplinas", consultaService.obterTopDisciplinasCargaHoraria());
            model.addAttribute("topAlunos", consultaService.obterTopAlunos());
            
        } catch (Exception e) {
            // Em caso de erro, usar dados padrão
            model.addAttribute("pageTitle", "Painel Acadêmico");
            model.addAttribute("estatisticas", Map.of(
                "totalAlunos", 0,
                "totalDisciplinas", 0,
                "mediaGeralNotas", 0.0,
                "alunosFrequenciaBaixa", 0,
                "taxaAprovacao", 0.0
            ));
            model.addAttribute("consultas", new String[]{
                    "Erro ao carregar dados",
                    "Verifique a conexão com o banco",
                    "Execute /api/criar-tabelas primeiro"
            });
            model.addAttribute("erro", "Erro ao carregar dados: " + e.getMessage());
        }
        
        return "index";
    }
}

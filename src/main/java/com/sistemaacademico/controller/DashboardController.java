package com.sistemaacademico.controller;

import com.sistemaacademico.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class DashboardController {

    @Autowired
    private ConsultaService consultaService;

    @GetMapping("/")
    public String index(Model model) {
        try {
            // BUSCAR ESTATÍSTICAS GERAIS E DADOS INICIAIS
            Map<String, Object> estatisticas = consultaService.obterEstatisticasGerais();
            
            // DADOS PARA OS 14 GRÁFICOS DE PESQUISA (NOVOS)
            
            // Gráficos Iniciais (REMOVIDOS)
            // Os dados de Aluno/Disciplina para tabelas simples serão mantidos na seção estatísticas,
            // mas os gráficos foram removidos.
            
            // Gráficos de Pesquisa (14) - MANTIDOS
            model.addAttribute("frequenciaEstudoVsMedia", consultaService.obterMediaPorFrequenciaEstudo()); // Item 1
            model.addAttribute("estresseVsProjetos", consultaService.obterEstresseVsProjetos());         // Item 2
            model.addAttribute("suporteProfVsProjetos", consultaService.obterSuporteProfVsProjetos());   // Item 3
            model.addAttribute("frequenciaVsDisciplinas", consultaService.obterFrequenciaVsDisciplinas()); // Item 4
            model.addAttribute("valorVsMedia", consultaService.obterValorVsMedia());                    // Item 5
            model.addAttribute("distribuicaoEmail", consultaService.obterDistribuicaoEmail());          // Item 6
            model.addAttribute("distribuicaoGenero", consultaService.obterDistribuicaoPorGenero());       // Item 7 (Usado no Gráfico de Gênero)
            model.addAttribute("distribuicaoTelefone", consultaService.obterDistribuicaoTelefone());     // Item 8
            model.addAttribute("distribuicaoProjetos", consultaService.obterDistribuicaoProjetos());     // Item 9
            model.addAttribute("distribuicaoFreqEstudo", consultaService.obterDistribuicaoFreqEstudo()); // Item 10
            model.addAttribute("distribuicaoIdadeGrafico", consultaService.obterDistribuicaoIdadeGrafico()); // Item 11 (Pizza)
            model.addAttribute("distribuicaoRecursos", consultaService.obterDistribuicaoUsoRecursos());  // Item 12
            model.addAttribute("dadosMonitoria", consultaService.obterDadosMonitoria());                // Item 13
            model.addAttribute("freqRecursosVsEstudo", consultaService.obterFreqRecursosVsEstudo());     // Item 14

            // ATRIBUTOS GERAIS PARA O THYMELEAF
            model.addAttribute("pageTitle", "Painel Acadêmico");
            model.addAttribute("estatisticas", estatisticas);
            model.addAttribute("consultas", new String[]{
                    "Total de alunos cadastrados", "Média geral de notas", "Taxa de aprovação",
                    "Alunos com frequência baixa", "Distribuição por sexo", "Distribuição por idade"
            });
            
        } catch (Exception e) {
            // Em caso de erro, usar dados padrão e exibir mensagem de erro no HTML
            model.addAttribute("pageTitle", "Painel Acadêmico (ERRO DE CONEXÃO)");
            model.addAttribute("estatisticas", Collections.emptyMap());
            model.addAttribute("erro", "Erro ao carregar dados: " + e.getMessage());
            model.addAttribute("consultas", new String[]{
                    "Erro ao carregar dados", "Verifique a conexão com o banco", "Execute /api/criar-tabelas primeiro"
            });
            
            // É fundamental retornar listas vazias para evitar NullPointerException no JS/Thymeleaf
            model.addAttribute("frequenciaEstudoVsMedia", Collections.emptyList());
            model.addAttribute("estresseVsProjetos", Collections.emptyList());
            model.addAttribute("suporteProfVsProjetos", Collections.emptyList());
            model.addAttribute("frequenciaVsDisciplinas", Collections.emptyList());
            model.addAttribute("valorVsMedia", Collections.emptyList());
            model.addAttribute("distribuicaoEmail", Collections.emptyList());
            model.addAttribute("distribuicaoGenero", Collections.emptyList());
            model.addAttribute("distribuicaoTelefone", Collections.emptyList());
            model.addAttribute("distribuicaoProjetos", Collections.emptyList());
            model.addAttribute("distribuicaoFreqEstudo", Collections.emptyList());
            model.addAttribute("distribuicaoIdadeGrafico", Collections.emptyList());
            model.addAttribute("distribuicaoRecursos", Collections.emptyList());
            model.addAttribute("dadosMonitoria", Collections.emptyList());
            model.addAttribute("freqRecursosVsEstudo", Collections.emptyList());
        }
        
        return "index";
    }
}
// Scripts do dashboard - integração com API REST
document.addEventListener("DOMContentLoaded", () => {
    console.log("Dashboard inicializado - conectando com API REST");
    
    // Conectar formulários com endpoints
    setupFormHandlers();
    
    // Inicializar gráficos
    initializeCharts();
});

// =========================================================================
// FUNÇÕES DE CRUD E AUXILIARES (MANTIDAS)
// ... (Mantenha todas as suas funções originais de CRUD e auxiliares aqui) ...
// =========================================================================


// =========================================================================
// FUNÇÕES PARA GRÁFICOS (REMOVIDAS AS INICIAIS)
// =========================================================================

function initializeCharts() {
    setTimeout(() => {
        
        // GRÁFICOS INICIAIS REMOVIDOS:
        // createSexoChart(); 
        // createIdadeChart();
        // createDisciplinasChart();
        // createMediasChart();
        
        // Gráficos de Pesquisa (MANTIDOS)
        createMediaVsEstudoChart();      // Item 1 (Scatter)
        createEstresseVsProjetosChart();  // Item 2 (Scatter)
        createSuporteProfVsProjetosChart(); // Item 3 (Scatter)
        createFrequenciaVsDisciplinasChart(); // Item 4 (Scatter)
        createValorVsMediaChart();        // Item 5 (Scatter)
        createIdadeEspecificaChart();     // Item 11 (Setor)
        createRecursosChart();            // Item 12 (Setor)
        createMonitoriaChart();           // Item 13 (Setor)
        createFreqRecursosVsEstudoChart(); // Item 14 (Scatter/Bubble)
        createGeneroChart();              // Item 7 (Barra)

    }, 1000);
}

// ------------------- GRÁFICOS ESTATÍSTICOS INICIAIS (REMOVIDOS) -------------------
// As funções createSexoChart, createIdadeChart, createDisciplinasChart e createMediasChart
// foram removidas deste arquivo.

// ------------------- GRÁFICOS DE PESQUISA (MANTIDOS) -------------------

// Item 1: Desempenho vs. Esforço (Média vs. Estudo)
function createMediaVsEstudoChart() {
    const ctx = document.getElementById('mediaVsEstudoChart');
    if (!ctx) return;
    const data = window.frequenciaEstudoVsMedia || [{ x: 2, y: 8.5 }, { x: 5, y: 8.0 }]; 

    new Chart(ctx, {
        type: 'scatter',
        data: {
            datasets: [{
                label: 'Média Geral',
                data: data,
                backgroundColor: 'rgba(0, 255, 0, 0.7)',
                pointRadius: 6,
                pointStyle: 'rect'
            }]
        },
        options: {
            responsive: true,
            scales: {
                x: { type: 'linear', position: 'bottom', title: { display: true, text: 'Quantas a frequência você estuda por dia?' } },
                y: { title: { display: true, text: 'Qual sua nota da média geral (0 a 10)' } }
            }
        }
    });
}
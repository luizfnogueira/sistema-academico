// Scripts do dashboard - integração com API REST
document.addEventListener("DOMContentLoaded", () => {
    console.log("Dashboard inicializado - conectando com API REST");
    
    // Conectar formulários com endpoints
    setupFormHandlers();
    
    // Inicializar gráficos
    initializeCharts();
});

// =========================================================================
// FUNÇÕES DE CRUD E AUXILIARES
// =========================================================================

function setupFormHandlers() {
    // Configurar todos os formulários
    const forms = document.querySelectorAll('form[data-action]');
    forms.forEach(form => {
        form.addEventListener('submit', handleFormSubmit);
    });
}

async function handleFormSubmit(event) {
    event.preventDefault();
    const form = event.target;
    const action = form.getAttribute('data-action');
    const formData = new FormData(form);
    
    try {
        let response;
        const data = Object.fromEntries(formData.entries());
        
        switch (action) {
            case 'create-aluno':
                response = await fetch('/api/alunos', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(data)
                });
                break;
                
            case 'update-aluno':
                const alunoId = data.id;
                delete data.id;
                response = await fetch(`/api/alunos/${alunoId}`, {
                    method: 'PUT',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(data)
                });
                break;
                
            case 'delete-aluno':
                response = await fetch(`/api/alunos/${data.id}`, {
                    method: 'DELETE'
                });
                break;
                
            case 'create-disciplina':
                response = await fetch('/api/disciplinas', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(data)
                });
                break;
                
            case 'update-disciplina':
                const disciplinaId = data.id;
                delete data.id;
                response = await fetch(`/api/disciplinas/${disciplinaId}`, {
                    method: 'PUT',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(data)
                });
                break;
                
            case 'delete-disciplina':
                response = await fetch(`/api/disciplinas/${data.id}`, {
                    method: 'DELETE'
                });
                break;
        }
        
        if (response.ok) {
            alert('Operação realizada com sucesso!');
            form.reset();
            // Recarregar página para atualizar estatísticas
            setTimeout(() => location.reload(), 1000);
        } else {
            const error = await response.text();
            alert('Erro: ' + error);
        }
        
    } catch (error) {
        console.error('Erro na requisição:', error);
        alert('Erro de conexão: ' + error.message);
    }
}

// Função para criar tabelas
async function criarTabelas() {
    const resultadoDiv = document.getElementById('resultado-criacao');
    resultadoDiv.innerHTML = '<p>🔄 Criando tabelas...</p>';
    
    try {
        const response = await fetch('/api/criar-tabelas');
        const resultado = await response.text();
        
        if (response.ok) {
            resultadoDiv.innerHTML = '<p style="color: green;">✅ ' + resultado + '</p>';
            // Recarregar página após 2 segundos
            setTimeout(() => location.reload(), 2000);
        } else {
            resultadoDiv.innerHTML = '<p style="color: red;">❌ Erro: ' + resultado + '</p>';
        }
    } catch (error) {
        resultadoDiv.innerHTML = '<p style="color: red;">❌ Erro de conexão: ' + error.message + '</p>';
    }
}


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
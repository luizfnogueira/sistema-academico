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

// =========================================================================
// FUNÇÕES SQL AVANÇADAS
// =========================================================================

// Função auxiliar para exibir resultados em tabela
function exibirResultadosTabela(elementId, dados, titulo) {
    const div = document.getElementById(elementId);
    if (!div) return;
    
    if (!dados || dados.length === 0) {
        div.innerHTML = '<p style="color: orange;">⚠️ Nenhum resultado encontrado.</p>';
        return;
    }
    
    let html = `<h4>${titulo}</h4><table style="width: 100%; border-collapse: collapse; margin-top: 1rem;"><thead><tr>`;
    
    // Cabeçalho da tabela
    const keys = Object.keys(dados[0]);
    keys.forEach(key => {
        html += `<th style="border: 1px solid #ddd; padding: 8px; background-color: #f2f2f2;">${key}</th>`;
    });
    html += '</tr></thead><tbody>';
    
    // Dados da tabela
    dados.forEach(row => {
        html += '<tr>';
        keys.forEach(key => {
            html += `<td style="border: 1px solid #ddd; padding: 8px;">${row[key] !== null ? row[key] : '-'}</td>`;
        });
        html += '</tr>';
    });
    
    html += '</tbody></table>';
    div.innerHTML = html;
}

// Inicializar estruturas avançadas
async function inicializarEstruturasAvancadas() {
    const resultadoDiv = document.getElementById('resultado-avancado');
    resultadoDiv.innerHTML = '<p>🔄 Inicializando estruturas avançadas...</p>';
    
    try {
        const response = await fetch('/api/sql-avancado/inicializar', {
            method: 'POST'
        });
        const resultado = await response.json();
        
        if (resultado.success) {
            resultadoDiv.innerHTML = '<p style="color: green;">✅ ' + resultado.message + '</p>';
        } else {
            resultadoDiv.innerHTML = '<p style="color: red;">❌ Erro: ' + resultado.message + '</p>';
        }
    } catch (error) {
        resultadoDiv.innerHTML = '<p style="color: red;">❌ Erro de conexão: ' + error.message + '</p>';
    }
}

// ========== CONSULTAS SQL ==========

async function consultarProfessoresSemOfertas() {
    const div = document.getElementById('resultado-consultas');
    div.innerHTML = '<p>🔄 Consultando...</p>';
    
    try {
        const response = await fetch('/api/sql-avancado/professores-sem-ofertas');
        const dados = await response.json();
        exibirResultadosTabela('resultado-consultas', dados, 'Professores sem Ofertas');
    } catch (error) {
        div.innerHTML = '<p style="color: red;">❌ Erro: ' + error.message + '</p>';
    }
}

async function consultarAlunosEProfessoresEmails() {
    const div = document.getElementById('resultado-consultas');
    div.innerHTML = '<p>🔄 Consultando...</p>';
    
    try {
        const response = await fetch('/api/sql-avancado/alunos-professores-emails');
        const dados = await response.json();
        exibirResultadosTabela('resultado-consultas', dados, 'Alunos e Professores com Emails');
    } catch (error) {
        div.innerHTML = '<p style="color: red;">❌ Erro: ' + error.message + '</p>';
    }
}

async function consultarAlunosAcimaMedia() {
    const div = document.getElementById('resultado-consultas');
    div.innerHTML = '<p>🔄 Consultando...</p>';
    
    try {
        const response = await fetch('/api/sql-avancado/alunos-acima-media');
        const dados = await response.json();
        exibirResultadosTabela('resultado-consultas', dados, 'Alunos com Média Acima da Média Geral');
    } catch (error) {
        div.innerHTML = '<p style="color: red;">❌ Erro: ' + error.message + '</p>';
    }
}

async function consultarAlunosTurmas2023() {
    const div = document.getElementById('resultado-consultas');
    div.innerHTML = '<p>🔄 Consultando...</p>';
    
    try {
        const response = await fetch('/api/sql-avancado/alunos-turmas-2023');
        const dados = await response.json();
        exibirResultadosTabela('resultado-consultas', dados, 'Alunos Matriculados em Turmas de 2023');
    } catch (error) {
        div.innerHTML = '<p style="color: red;">❌ Erro: ' + error.message + '</p>';
    }
}

// ========== VIEWS ==========

async function consultarViewDetalhesAcademicos() {
    const div = document.getElementById('resultado-view-aluno');
    div.innerHTML = '<p>🔄 Consultando...</p>';
    
    try {
        const response = await fetch('/api/sql-avancado/view/detalhes-academicos-aluno');
        const dados = await response.json();
        exibirResultadosTabela('resultado-view-aluno', dados, 'Detalhes Acadêmicos dos Alunos');
    } catch (error) {
        div.innerHTML = '<p style="color: red;">❌ Erro: ' + error.message + '</p>';
    }
}

async function consultarViewDetalhesAcademicosPorId() {
    const idAluno = document.getElementById('idAlunoView').value;
    if (!idAluno) {
        alert('Por favor, informe o ID do aluno');
        return;
    }
    
    const div = document.getElementById('resultado-view-aluno');
    div.innerHTML = '<p>🔄 Consultando...</p>';
    
    try {
        const response = await fetch(`/api/sql-avancado/view/detalhes-academicos-aluno/${idAluno}`);
        const dados = await response.json();
        exibirResultadosTabela('resultado-view-aluno', dados, `Detalhes Acadêmicos do Aluno ID ${idAluno}`);
    } catch (error) {
        div.innerHTML = '<p style="color: red;">❌ Erro: ' + error.message + '</p>';
    }
}

async function consultarViewPerfilProfessor() {
    const div = document.getElementById('resultado-view-professor');
    div.innerHTML = '<p>🔄 Consultando...</p>';
    
    try {
        const response = await fetch('/api/sql-avancado/view/perfil-completo-professor');
        const dados = await response.json();
        exibirResultadosTabela('resultado-view-professor', dados, 'Perfil Completo dos Professores');
    } catch (error) {
        div.innerHTML = '<p style="color: red;">❌ Erro: ' + error.message + '</p>';
    }
}

async function consultarViewPerfilProfessorPorId() {
    const idProf = document.getElementById('idProfView').value;
    if (!idProf) {
        alert('Por favor, informe o ID do professor');
        return;
    }
    
    const div = document.getElementById('resultado-view-professor');
    div.innerHTML = '<p>🔄 Consultando...</p>';
    
    try {
        const response = await fetch(`/api/sql-avancado/view/perfil-completo-professor/${idProf}`);
        const dados = await response.json();
        exibirResultadosTabela('resultado-view-professor', dados, `Perfil Completo do Professor ID ${idProf}`);
    } catch (error) {
        div.innerHTML = '<p style="color: red;">❌ Erro: ' + error.message + '</p>';
    }
}

// ========== FUNÇÕES ==========

async function consultarSituacaoAluno() {
    const idAluno = document.getElementById('idAlunoFuncao').value;
    if (!idAluno) {
        alert('Por favor, informe o ID do aluno');
        return;
    }
    
    const div = document.getElementById('resultado-funcao-situacao');
    div.innerHTML = '<p>🔄 Consultando...</p>';
    
    try {
        const response = await fetch(`/api/sql-avancado/funcao/situacao-aluno/${idAluno}`);
        const resultado = await response.json();
        
        if (resultado.error) {
            div.innerHTML = '<p style="color: red;">❌ Erro: ' + resultado.error + '</p>';
        } else {
            div.innerHTML = `<p style="color: green;"><strong>Situação do Aluno ID ${resultado.idAluno}:</strong> ${resultado.situacao}</p>`;
        }
    } catch (error) {
        div.innerHTML = '<p style="color: red;">❌ Erro: ' + error.message + '</p>';
    }
}

async function calcularMediaDisciplina() {
    const idDisciplina = document.getElementById('idDisciplinaFuncao').value;
    const idAluno = document.getElementById('idAlunoMediaFuncao').value;
    
    if (!idDisciplina || !idAluno) {
        alert('Por favor, informe o ID da disciplina e do aluno');
        return;
    }
    
    const div = document.getElementById('resultado-funcao-media');
    div.innerHTML = '<p>🔄 Calculando...</p>';
    
    try {
        const response = await fetch(`/api/sql-avancado/funcao/media-disciplina?idDisciplina=${idDisciplina}&idAluno=${idAluno}`);
        const resultado = await response.json();
        
        if (resultado.error) {
            div.innerHTML = '<p style="color: red;">❌ Erro: ' + resultado.error + '</p>';
        } else {
            div.innerHTML = `<p style="color: green;"><strong>Média do Aluno ID ${resultado.idAluno} na Disciplina ID ${resultado.idDisciplina}:</strong> ${resultado.media.toFixed(2)}</p>`;
        }
    } catch (error) {
        div.innerHTML = '<p style="color: red;">❌ Erro: ' + error.message + '</p>';
    }
}

// ========== PROCEDIMENTOS ==========

async function atualizarFrequenciaAluno() {
    const idAluno = document.getElementById('idAlunoProcedimento').value;
    const frequenciaNova = document.getElementById('frequenciaNova').value;
    
    if (!idAluno || !frequenciaNova) {
        alert('Por favor, informe o ID do aluno e a nova frequência');
        return;
    }
    
    const div = document.getElementById('resultado-procedimento-frequencia');
    div.innerHTML = '<p>🔄 Atualizando...</p>';
    
    try {
        const response = await fetch(`/api/sql-avancado/procedimento/atualizar-frequencia?idAluno=${idAluno}&frequenciaNova=${frequenciaNova}`, {
            method: 'PUT'
        });
        const resultado = await response.json();
        
        if (resultado.success) {
            div.innerHTML = `<p style="color: green;">✅ ${resultado.message}</p><p>Frequência do Aluno ID ${resultado.idAluno} atualizada para ${resultado.frequenciaNova}%</p>`;
        } else {
            div.innerHTML = '<p style="color: red;">❌ Erro: ' + resultado.error + '</p>';
        }
    } catch (error) {
        div.innerHTML = '<p style="color: red;">❌ Erro: ' + error.message + '</p>';
    }
}

async function contarConselhosPorProfessor() {
    const div = document.getElementById('resultado-procedimento-conselhos');
    div.innerHTML = '<p>🔄 Executando procedimento...</p>';
    
    try {
        const response = await fetch('/api/sql-avancado/procedimento/contar-conselhos', {
            method: 'POST'
        });
        const resultado = await response.json();
        
        if (resultado.success) {
            div.innerHTML = '<p style="color: green;">✅ ' + resultado.message + '</p>';
            // Atualizar resumo automaticamente
            setTimeout(consultarResumoConselhos, 1000);
        } else {
            div.innerHTML = '<p style="color: red;">❌ Erro: ' + resultado.error + '</p>';
        }
    } catch (error) {
        div.innerHTML = '<p style="color: red;">❌ Erro: ' + error.message + '</p>';
    }
}

async function consultarResumoConselhos() {
    const div = document.getElementById('resultado-procedimento-conselhos');
    div.innerHTML = '<p>🔄 Consultando...</p>';
    
    try {
        const response = await fetch('/api/sql-avancado/resumo-conselhos');
        const dados = await response.json();
        exibirResultadosTabela('resultado-procedimento-conselhos', dados, 'Resumo de Conselhos por Professor');
    } catch (error) {
        div.innerHTML = '<p style="color: red;">❌ Erro: ' + error.message + '</p>';
    }
}

// ========== LOGS ==========

async function consultarLogPagamento() {
    const div = document.getElementById('resultado-log-pagamento');
    div.innerHTML = '<p>🔄 Consultando...</p>';
    
    try {
        const response = await fetch('/api/sql-avancado/log-pagamento');
        const dados = await response.json();
        exibirResultadosTabela('resultado-log-pagamento', dados, 'Log de Pagamentos');
    } catch (error) {
        div.innerHTML = '<p style="color: red;">❌ Erro: ' + error.message + '</p>';
    }
}
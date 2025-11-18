document.addEventListener("DOMContentLoaded", () => {
    console.log("Dashboard inicializado - conectando com API REST");
    
    
    setupFormHandlers();
    
    
    initializeCharts();
});

 

function setupFormHandlers() {
    
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
        let data = Object.fromEntries(formData.entries());
        
        
        if (action === 'create-aluno' || action === 'update-aluno') {
            if (data.idade) data.idade = parseInt(data.idade);
            if (data.num) data.num = parseInt(data.num);
            if (data.media) data.media = parseFloat(data.media);
            if (data.frequencia) data.frequencia = parseFloat(data.frequencia);
        }
        
        
        if (action === 'create-professor' || action === 'update-professor') {
            if (data.num) data.num = parseInt(data.num);
        }
        
        
        if (action === 'create-avaliacao' || action === 'update-avaliacao') {
            if (data.valor) data.valor = parseFloat(data.valor);
            if (data.idAluno) data.idAluno = parseInt(data.idAluno);
            if (data.idDisc) data.idDisc = parseInt(data.idDisc);
        }
        
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
                
            case 'create-professor':
                response = await fetch('/api/professores', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(data)
                });
                break;
                
            case 'update-professor':
                const professorId = data.id;
                delete data.id;
                response = await fetch(`/api/professores/${professorId}`, {
                    method: 'PUT',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(data)
                });
                break;
                
            case 'delete-professor':
                response = await fetch(`/api/professores/${data.id}`, {
                    method: 'DELETE'
                });
                break;
                
            case 'create-avaliacao':
                response = await fetch('/api/avaliacoes', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(data)
                });
                break;
                
            case 'update-avaliacao':
                const avaliacaoId = data.id;
                delete data.id;
                response = await fetch(`/api/avaliacoes/${avaliacaoId}`, {
                    method: 'PUT',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(data)
                });
                break;
                
            case 'delete-avaliacao':
                response = await fetch(`/api/avaliacoes/${data.id}`, {
                    method: 'DELETE'
                });
                break;
                
            case 'atualizar-frequencia-aluno':
                response = await fetch('/api/sql-avancado/procedimento/atualizar-frequencia?idAluno=' + data.idAluno + '&frequenciaNova=' + data.frequenciaNova, {
                    method: 'PUT'
                });
                break;
        }
        
        if (response.ok) {
            const responseData = await response.json().catch(() => ({ message: 'Operação realizada com sucesso!' }));
            
            
            alert('Operação realizada com sucesso!');
            form.reset();
            
            document.dispatchEvent(new Event('formSuccess'));
            
            setTimeout(() => atualizarGrafos(), 500);
        } else {
            const error = await response.text();
            alert('Erro: ' + error);
        }
        
    } catch (error) {
        console.error('Erro na requisição:', error);
        alert('Erro de conexão: ' + error.message);
    }
}

 
async function criarTabelas() {
    const resultadoDiv = document.getElementById('resultado-criacao');
    resultadoDiv.innerHTML = '<p>🔄 Criando tabelas...</p>';
    
    try {
        const response = await fetch('/api/criar-tabelas');
        const resultado = await response.text();
        
        if (response.ok) {
            resultadoDiv.innerHTML = '<p style="color: green;">✅ ' + resultado + '</p>';
            
            setTimeout(() => location.reload(), 2000);
        } else {
            resultadoDiv.innerHTML = '<p style="color: red;">❌ Erro: ' + resultado + '</p>';
        }
    } catch (error) {
        resultadoDiv.innerHTML = '<p style="color: red;">❌ Erro de conexão: ' + error.message + '</p>';
    }
}


 

 
const chartInstances = {};

 
async function atualizarGrafos() {
    try {
        const response = await fetch('/api/dashboard/estatisticas');
        const estatisticas = await response.json();
        
        
        await carregarDadosGraficos();
        
        
        Object.keys(chartInstances).forEach(chartId => {
            if (chartInstances[chartId]) {
                chartInstances[chartId].update();
            }
        });
    } catch (error) {
        console.error('Erro ao atualizar gráficos:', error);
    }
}

// Carregar dados dos gráficos do servidor
async function carregarDadosGraficos() {
    try {
        const response = await fetch('/api/dashboard/graficos');
        const dados = await response.json();
        
        // Atualizar variáveis globais
        window.frequenciaEstudoVsMedia = dados.frequenciaEstudoVsMedia || [];
        window.estresseVsProjetos = dados.estresseVsProjetos || [];
        window.suporteProfVsProjetos = dados.suporteProfVsProjetos || [];
        window.frequenciaVsDisciplinas = dados.frequenciaVsDisciplinas || [];
        window.valorVsMedia = dados.valorVsMedia || [];
        window.distribuicaoGenero = dados.distribuicaoGenero || [];
        window.distribuicaoIdadeGrafico = dados.distribuicaoIdadeGrafico || [];
        window.distribuicaoRecursos = dados.distribuicaoRecursos || [];
        window.dadosMonitoria = dados.dadosMonitoria || [];
        window.freqRecursosVsEstudo = dados.freqRecursosVsEstudo || [];
        
        // Recriar gráficos com novos dados
        recriarTodosGraficos();
    } catch (error) {
        console.error('Erro ao carregar dados dos gráficos:', error);
    }
}

function recriarTodosGraficos() {
    createMediaVsEstudoChart();
    createEstresseVsProjetosChart();
    createSuporteProfVsProjetosChart();
    createFrequenciaVsDisciplinasChart();
    createValorVsMediaChart();
    createFreqRecursosVsEstudoChart();
    createGeneroChart();
}

function initializeCharts() {
    // Carregar dados e criar gráficos
    carregarDadosGraficos();
    
    // Configurar atualização automática a cada 30 segundos
    setInterval(atualizarGrafos, 30000);
    
    // Atualizar após operações CRUD
    document.addEventListener('formSuccess', () => {
        setTimeout(atualizarGrafos, 1000);
    });
}

// ------------------- GRÁFICOS DE PESQUISA -------------------

// Item 1: Desempenho vs. Esforço (Média vs. Estudo)
function createMediaVsEstudoChart() {
    const ctx = document.getElementById('mediaVsEstudoChart');
    if (!ctx) return;
    
    // Destruir gráfico existente se houver
    if (chartInstances['mediaVsEstudoChart']) {
        chartInstances['mediaVsEstudoChart'].destroy();
    }
    
    const rawData = window.frequenciaEstudoVsMedia || [];
    const data = Array.isArray(rawData) ? rawData.map(item => ({
        x: item.Freq_Estudo || item.x || 0,
        y: item.Media || item.y || 0
    })) : [{ x: 2, y: 8.5 }, { x: 5, y: 8.0 }];

    chartInstances['mediaVsEstudoChart'] = new Chart(ctx, {
        type: 'scatter',
        data: {
            datasets: [{
                label: 'Média Geral',
                data: data,
                backgroundColor: 'rgba(54, 162, 235, 0.7)',
                borderColor: 'rgba(54, 162, 235, 1)',
                pointRadius: 6,
                pointHoverRadius: 8
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: true,
            plugins: {
                legend: { display: true }
            },
            scales: {
                x: { 
                    type: 'linear', 
                    position: 'bottom', 
                    title: { display: true, text: 'Frequência de Estudo por Dia' },
                    beginAtZero: true
                },
                y: { 
                    title: { display: true, text: 'Média Geral (0 a 10)' },
                    beginAtZero: true,
                    max: 10
                }
            }
        }
    });
}

// Item 2: Estresse vs. Projetos
function createEstresseVsProjetosChart() {
    const ctx = document.getElementById('estresseVsProjetosChart');
    if (!ctx) return;
    if (chartInstances['estresseVsProjetosChart']) {
        chartInstances['estresseVsProjetosChart'].destroy();
    }
    const rawData = window.estresseVsProjetos || [];
    // Se os dados vierem como array de objetos, converter para barras
    let labels = rawData.map(item => item.Nvl_Estresse || item.x || '');
    let values = rawData.map(item => item.Projetos || item.y || 0);
    if (labels.length > 0 && values.length > 0) {
        chartInstances['estresseVsProjetosChart'] = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Estresse vs Projetos',
                    data: values,
                    backgroundColor: 'rgba(255, 99, 132, 0.7)',
                    borderColor: 'rgba(255, 99, 132, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: true,
                scales: {
                    x: { title: { display: true, text: 'Nível de Estresse' } },
                    y: { title: { display: true, text: 'Quantidade de Projetos' }, beginAtZero: true }
                }
            }
        });
    } else {
        // fallback para scatter
        chartInstances['estresseVsProjetosChart'] = new Chart(ctx, {
            type: 'scatter',
            data: {
                datasets: [{
                    label: 'Estresse vs Projetos',
                    data: [{ x: 1, y: 2 }, { x: 2, y: 3 }],
                    backgroundColor: 'rgba(255, 99, 132, 0.7)',
                    borderColor: 'rgba(255, 99, 132, 1)',
                    pointRadius: 6
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: true,
                scales: {
                    x: { title: { display: true, text: 'Nível de Estresse' } },
                    y: { title: { display: true, text: 'Quantidade de Projetos' } }
                }
            }
        });
    }
}

// Item 3: Suporte Prof. vs. Projetos
function createSuporteProfVsProjetosChart() {
    const ctx = document.getElementById('suporteProfVsProjetosChart');
    if (!ctx) return;
    
    if (chartInstances['suporteProfVsProjetosChart']) {
        chartInstances['suporteProfVsProjetosChart'].destroy();
    }
    
    const rawData = window.suporteProfVsProjetos || [];
    const data = Array.isArray(rawData) ? rawData.map(item => ({
        x: item.Suporte || item.x || 0,
        y: item.Projetos || item.y || 0
    })) : [{ x: 1, y: 2 }];

    chartInstances['suporteProfVsProjetosChart'] = new Chart(ctx, {
        type: 'scatter',
        data: {
            datasets: [{
                label: 'Suporte Prof. vs Projetos',
                data: data,
                backgroundColor: 'rgba(75, 192, 192, 0.7)',
                borderColor: 'rgba(75, 192, 192, 1)',
                pointRadius: 6
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: true,
            scales: {
                x: { title: { display: true, text: 'Suporte do Professor' } },
                y: { title: { display: true, text: 'Quantidade de Projetos' } }
            }
        }
    });
}

// Item 4: Frequência vs. Disciplinas
function createFrequenciaVsDisciplinasChart() {
    const ctx = document.getElementById('frequenciaVsDisciplinasChart');
    if (!ctx) return;
    
    if (chartInstances['frequenciaVsDisciplinasChart']) {
        chartInstances['frequenciaVsDisciplinasChart'].destroy();
    }
    
    const rawData = window.frequenciaVsDisciplinas || [];
    const data = Array.isArray(rawData) ? rawData.map(item => ({
        x: item.Qtd_Disciplinas || item.x || 0,
        y: item.Frequencia || item.y || 0
    })) : [{ x: 3, y: 85 }];

    chartInstances['frequenciaVsDisciplinasChart'] = new Chart(ctx, {
        type: 'scatter',
        data: {
            datasets: [{
                label: 'Frequência vs Disciplinas',
                data: data,
                backgroundColor: 'rgba(153, 102, 255, 0.7)',
                borderColor: 'rgba(153, 102, 255, 1)',
                pointRadius: 6
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: true,
            scales: {
                x: { title: { display: true, text: 'Quantidade de Disciplinas' } },
                y: { title: { display: true, text: 'Frequência (%)' } }
            }
        }
    });
}

// Item 5: Valor vs. Média Geral
function createValorVsMediaChart() {
    const ctx = document.getElementById('valorVsMediaChart');
    if (!ctx) return;
    
    if (chartInstances['valorVsMediaChart']) {
        chartInstances['valorVsMediaChart'].destroy();
    }
    
    const rawData = window.valorVsMedia || [];
    const data = Array.isArray(rawData) ? rawData.map(item => ({
        x: item.Valor || item.x || 0,
        y: item.Media || item.y || 0
    })) : [{ x: 7.5, y: 8.0 }];

    chartInstances['valorVsMediaChart'] = new Chart(ctx, {
        type: 'scatter',
        data: {
            datasets: [{
                label: 'Valor vs Média',
                data: data,
                backgroundColor: 'rgba(255, 206, 86, 0.7)',
                borderColor: 'rgba(255, 206, 86, 1)',
                pointRadius: 6
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: true,
            scales: {
                x: { title: { display: true, text: 'Valor' } },
                y: { title: { display: true, text: 'Média Geral' } }
            }
        }
    });
}

// Item 7: Distribuição por Gênero (Barra)
function createGeneroChart() {
    const ctx = document.getElementById('generoChart');
    if (!ctx) return;
    
    if (chartInstances['generoChart']) {
        chartInstances['generoChart'].destroy();
    }
    
    const rawData = window.distribuicaoGenero || [];
    let labels = rawData.map(item => item.Sexo || item.label || 'N/A');
    let values = rawData.map(item => item.Total || item.value || 0);
    if (labels.length > 0 && values.length > 0) {
        chartInstances['generoChart'] = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Distribuição por Gênero',
                    data: values,
                    backgroundColor: ['rgba(54, 162, 235, 0.7)', 'rgba(255, 99, 132, 0.7)'],
                    borderColor: ['rgba(54, 162, 235, 1)', 'rgba(255, 99, 132, 1)'],
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: true,
                scales: {
                    y: { beginAtZero: true, title: { display: true, text: 'Quantidade' } }
                }
            }
        });
    } else {
        // fallback para barras zeradas
        chartInstances['generoChart'] = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: ['M', 'F'],
                datasets: [{
                    label: 'Distribuição por Gênero',
                    data: [0, 0],
                    backgroundColor: ['rgba(54, 162, 235, 0.7)', 'rgba(255, 99, 132, 0.7)'],
                    borderColor: ['rgba(54, 162, 235, 1)', 'rgba(255, 99, 132, 1)'],
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: true,
                scales: {
                    y: { beginAtZero: true, title: { display: true, text: 'Quantidade' } }
                }
            }
        });
    }
}

// Item 14: Recursos vs. Estudo (Scatter)
function createFreqRecursosVsEstudoChart() {
    const ctx = document.getElementById('freqRecursosVsEstudoChart');
    if (!ctx) return;
    
    if (chartInstances['freqRecursosVsEstudoChart']) {
        chartInstances['freqRecursosVsEstudoChart'].destroy();
    }
    
    const rawData = window.freqRecursosVsEstudo || [];
    const data = Array.isArray(rawData) ? rawData.map(item => ({
        x: item.Freq_Recurso || item.x || 0,
        y: item.Freq_Estudo || item.y || 0
    })) : [{ x: 2, y: 3 }];

    chartInstances['freqRecursosVsEstudoChart'] = new Chart(ctx, {
        type: 'scatter',
        data: {
            datasets: [{
                label: 'Recursos vs Estudo',
                data: data,
                backgroundColor: 'rgba(255, 159, 64, 0.7)',
                borderColor: 'rgba(255, 159, 64, 1)',
                pointRadius: 6
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: true,
            scales: {
                x: { title: { display: true, text: 'Frequência de Recursos' } },
                y: { title: { display: true, text: 'Frequência de Estudo' } }
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
    if (!Array.isArray(dados) || dados.length === 0) {
        div.innerHTML = '<p style="color: orange;">⚠️ Nenhum resultado encontrado.</p>';
        return;
    }
    let html = `<h4>${titulo}</h4><table style="width: 100%; border-collapse: collapse; margin-top: 1rem;"><thead><tr>`;
    const keys = Object.keys(dados[0] || {});
    keys.forEach(key => {
        html += `<th style="border: 1px solid #ddd; padding: 8px; background-color: #f2f2f2;">${key}</th>`;
    });
    html += '</tr></thead><tbody>';
    dados.forEach(row => {
        html += '<tr>';
        keys.forEach(key => {
            html += `<td style="border: 1px solid #ddd; padding: 8px;">${row[key] !== null && row[key] !== undefined ? row[key] : '-'}</td>`;
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

// Função calcularMediaDisciplina removida conforme solicitado

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

// ========== PROCEDIMENTOS ===========

async function criarConselho() {
    const idProf = document.getElementById('idProfConselho').value;
    const descricao = document.getElementById('descricaoConselho').value;
    let data = document.getElementById('dataConselho').value;
    const div = document.getElementById('resultado-criar-conselho');
    div.innerHTML = '<p>🔄 Criando conselho...</p>';
    if (!idProf || !descricao) {
        div.innerHTML = '<p style="color: orange;">⚠️ Informe o ID do professor e a descrição.</p>';
        return;
    }
    if (!data) {
        // Se não informado, usa data atual
        data = new Date().toISOString().slice(0, 10);
    }
    try {
        const response = await fetch('/api/sql-avancado/procedimento/criar-conselho', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ idProf: parseInt(idProf), descricao, data })
        });
        const resultado = await response.json();
        if (resultado.success) {
            div.innerHTML = `<p style="color: green;">✅ ${resultado.message}</p><p>ID Conselho: ${resultado.idConselho}</p>`;
        } else {
            div.innerHTML = `<p style="color: red;">❌ Erro: ${resultado.error || 'Não foi possível criar o conselho.'}</p>`;
        }
    } catch (error) {
        div.innerHTML = `<p style="color: red;">❌ Erro de conexão: ${error.message}</p>`;
    }
}

// ========== LOGS E AUDITORIA ===========

async function consultarLogPagamento() {
    const div = document.getElementById('resultado-log-pagamento');
    div.innerHTML = '<p>🔄 Consultando log de pagamentos...</p>';
    try {
        const response = await fetch('/api/sql-avancado/log/pagamento');
        const dados = await response.json();
        exibirResultadosTabela('resultado-log-pagamento', dados, 'Log de Pagamentos');
    } catch (error) {
        div.innerHTML = `<p style="color: red;">❌ Erro: ${error.message}</p>`;
    }
}
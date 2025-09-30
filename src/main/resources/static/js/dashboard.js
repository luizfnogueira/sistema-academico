// Scripts do dashboard - integração com API REST
document.addEventListener("DOMContentLoaded", () => {
    console.log("Dashboard inicializado - conectando com API REST");
    
    // Conectar formulários com endpoints
    setupFormHandlers();
    
    // Carregar dados iniciais
    loadDashboardData();
    
    // Inicializar gráficos
    initializeCharts();
});

function setupFormHandlers() {
    // Formulário de criação de aluno
    const createAlunoForm = document.querySelector('[data-action="create-aluno"]');
    if (createAlunoForm) {
        createAlunoForm.addEventListener('submit', handleCreateAluno);
    }

    // Formulário de atualização de aluno
    const updateAlunoForm = document.querySelector('[data-action="update-aluno"]');
    if (updateAlunoForm) {
        updateAlunoForm.addEventListener('submit', handleUpdateAluno);
    }

    // Formulário de exclusão de aluno
    const deleteAlunoForm = document.querySelector('[data-action="delete-aluno"]');
    if (deleteAlunoForm) {
        deleteAlunoForm.addEventListener('submit', handleDeleteAluno);
    }

    // Formulário de criação de disciplina
    const createDisciplinaForm = document.querySelector('[data-action="create-disciplina"]');
    if (createDisciplinaForm) {
        createDisciplinaForm.addEventListener('submit', handleCreateDisciplina);
    }

    // Formulário de atualização de disciplina
    const updateDisciplinaForm = document.querySelector('[data-action="update-disciplina"]');
    if (updateDisciplinaForm) {
        updateDisciplinaForm.addEventListener('submit', handleUpdateDisciplina);
    }

    // Formulário de exclusão de disciplina
    const deleteDisciplinaForm = document.querySelector('[data-action="delete-disciplina"]');
    if (deleteDisciplinaForm) {
        deleteDisciplinaForm.addEventListener('submit', handleDeleteDisciplina);
    }
}

// ========== HANDLERS PARA ALUNOS ==========

async function handleCreateAluno(event) {
    event.preventDefault();
    const formData = new FormData(event.target);
    
    const aluno = {
        nome: formData.get('nome'),
        idade: parseInt(formData.get('idade')) || 18,
        sexo: 'M', // Padrão
        rua: formData.get('rua') || '',
        num: parseInt(formData.get('num')) || 0,
        cep: formData.get('cep') || '',
        media: 0.0,
        frequencia: 100.0
    };

    try {
        const response = await fetch('/api/alunos', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(aluno)
        });

        if (response.ok) {
            showMessage('Aluno criado com sucesso!', 'success');
            event.target.reset();
            loadDashboardData();
        } else {
            const error = await response.text();
            showMessage('Erro ao criar aluno: ' + error, 'error');
        }
    } catch (error) {
        showMessage('Erro de conexão: ' + error.message, 'error');
    }
}

async function handleUpdateAluno(event) {
    event.preventDefault();
    const formData = new FormData(event.target);
    const id = formData.get('id');
    
    if (!id) {
        showMessage('ID do aluno é obrigatório', 'error');
        return;
    }

    const aluno = {
        nome: formData.get('nome') || '',
        idade: parseInt(formData.get('idade')) || 18,
        sexo: formData.get('sexo') || 'M',
        rua: formData.get('rua') || '',
        num: parseInt(formData.get('num')) || 0,
        cep: formData.get('cep') || '',
        media: parseFloat(formData.get('media')) || 0.0,
        frequencia: parseFloat(formData.get('frequencia')) || 100.0
    };

    try {
        const response = await fetch(`/api/alunos/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(aluno)
        });

        if (response.ok) {
            showMessage('Aluno atualizado com sucesso!', 'success');
            event.target.reset();
            loadDashboardData();
        } else {
            const error = await response.text();
            showMessage('Erro ao atualizar aluno: ' + error, 'error');
        }
    } catch (error) {
        showMessage('Erro de conexão: ' + error.message, 'error');
    }
}

async function handleDeleteAluno(event) {
    event.preventDefault();
    const formData = new FormData(event.target);
    const id = formData.get('id');
    
    if (!id) {
        showMessage('ID do aluno é obrigatório', 'error');
        return;
    }

    if (!confirm('Tem certeza que deseja excluir este aluno?')) {
        return;
    }

    try {
        const response = await fetch(`/api/alunos/${id}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            showMessage('Aluno excluído com sucesso!', 'success');
            event.target.reset();
            loadDashboardData();
        } else {
            const error = await response.text();
            showMessage('Erro ao excluir aluno: ' + error, 'error');
        }
    } catch (error) {
        showMessage('Erro de conexão: ' + error.message, 'error');
    }
}

// ========== HANDLERS PARA DISCIPLINAS ==========

async function handleCreateDisciplina(event) {
    event.preventDefault();
    const formData = new FormData(event.target);
    
    const disciplina = {
        nome: formData.get('nome'),
        cargaHoraria: parseInt(formData.get('cargaHoraria')) || 40
    };

    try {
        const response = await fetch('/api/disciplinas', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(disciplina)
        });

        if (response.ok) {
            showMessage('Disciplina criada com sucesso!', 'success');
            event.target.reset();
            loadDashboardData();
        } else {
            const error = await response.text();
            showMessage('Erro ao criar disciplina: ' + error, 'error');
        }
    } catch (error) {
        showMessage('Erro de conexão: ' + error.message, 'error');
    }
}

async function handleUpdateDisciplina(event) {
    event.preventDefault();
    const formData = new FormData(event.target);
    const id = formData.get('id');
    
    if (!id) {
        showMessage('ID da disciplina é obrigatório', 'error');
        return;
    }

    const disciplina = {
        nome: formData.get('nome') || '',
        cargaHoraria: parseInt(formData.get('cargaHoraria')) || 40
    };

    try {
        const response = await fetch(`/api/disciplinas/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(disciplina)
        });

        if (response.ok) {
            showMessage('Disciplina atualizada com sucesso!', 'success');
            event.target.reset();
            loadDashboardData();
        } else {
            const error = await response.text();
            showMessage('Erro ao atualizar disciplina: ' + error, 'error');
        }
    } catch (error) {
        showMessage('Erro de conexão: ' + error.message, 'error');
    }
}

async function handleDeleteDisciplina(event) {
    event.preventDefault();
    const formData = new FormData(event.target);
    const id = formData.get('id');
    
    if (!id) {
        showMessage('ID da disciplina é obrigatório', 'error');
        return;
    }

    if (!confirm('Tem certeza que deseja excluir esta disciplina?')) {
        return;
    }

    try {
        const response = await fetch(`/api/disciplinas/${id}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            showMessage('Disciplina excluída com sucesso!', 'success');
            event.target.reset();
            loadDashboardData();
        } else {
            const error = await response.text();
            showMessage('Erro ao excluir disciplina: ' + error, 'error');
        }
    } catch (error) {
        showMessage('Erro de conexão: ' + error.message, 'error');
    }
}

// ========== FUNÇÕES AUXILIARES ==========

async function loadDashboardData() {
    try {
        // Recarregar a página para atualizar os dados
        window.location.reload();
    } catch (error) {
        console.error('Erro ao carregar dados:', error);
    }
}

function showMessage(message, type) {
    // Criar elemento de mensagem
    const messageDiv = document.createElement('div');
    messageDiv.className = `message message-${type}`;
    messageDiv.textContent = message;
    
    // Adicionar estilos
    messageDiv.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        padding: 1rem 1.5rem;
        border-radius: 8px;
        color: white;
        font-weight: 600;
        z-index: 1000;
        max-width: 300px;
        box-shadow: 0 4px 12px rgba(0,0,0,0.2);
        ${type === 'success' ? 'background: #4caf50;' : 'background: #f44336;'}
    `;
    
    document.body.appendChild(messageDiv);
    
    // Remover após 3 segundos
    setTimeout(() => {
        if (messageDiv.parentNode) {
            messageDiv.parentNode.removeChild(messageDiv);
        }
    }, 3000);
}

// ========== FUNÇÕES PARA GRÁFICOS ==========

function initializeCharts() {
    // Aguardar um pouco para garantir que os dados estão carregados
    setTimeout(() => {
        createSexoChart();
        createIdadeChart();
        createDisciplinasChart();
        createMediasChart();
    }, 1000);
}

function createSexoChart() {
    const ctx = document.getElementById('sexoChart');
    if (!ctx) return;

    // Dados do Thymeleaf (se disponíveis)
    const distribuicaoSexo = window.distribuicaoSexo || [
        { descricao: 'M', valor: 15 },
        { descricao: 'F', valor: 12 }
    ];

    new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: distribuicaoSexo.map(item => item.descricao === 'M' ? 'Masculino' : 'Feminino'),
            datasets: [{
                data: distribuicaoSexo.map(item => item.valor),
                backgroundColor: ['#003366', '#006699'],
                borderWidth: 2
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'bottom'
                }
            }
        }
    });
}

function createIdadeChart() {
    const ctx = document.getElementById('idadeChart');
    if (!ctx) return;

    // Dados do Thymeleaf (se disponíveis)
    const distribuicaoIdade = window.distribuicaoIdade || [
        { descricao: 'Menos de 20 anos', valor: 8 },
        { descricao: '20-25 anos', valor: 12 },
        { descricao: '26-30 anos', valor: 5 },
        { descricao: 'Mais de 30 anos', valor: 2 }
    ];

    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: distribuicaoIdade.map(item => item.descricao),
            datasets: [{
                label: 'Quantidade',
                data: distribuicaoIdade.map(item => item.valor),
                backgroundColor: '#006699',
                borderColor: '#003366',
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
}

function createDisciplinasChart() {
    const ctx = document.getElementById('disciplinasChart');
    if (!ctx) return;

    // Dados do Thymeleaf (se disponíveis)
    const topDisciplinas = window.topDisciplinas || [
        { descricao: 'Matemática', valor: 80 },
        { descricao: 'Física', valor: 60 },
        { descricao: 'Química', valor: 40 },
        { descricao: 'Biologia', valor: 30 }
    ];

    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: topDisciplinas.map(item => item.descricao),
            datasets: [{
                label: 'Carga Horária',
                data: topDisciplinas.map(item => item.valor),
                backgroundColor: '#fdb913',
                borderColor: '#e6a500',
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            indexAxis: 'y',
            scales: {
                x: {
                    beginAtZero: true
                }
            }
        }
    });
}

function createMediasChart() {
    const ctx = document.getElementById('mediasChart');
    if (!ctx) return;

    // Dados do Thymeleaf (se disponíveis)
    const topAlunos = window.topAlunos || [
        { nome: 'João Silva', media: 9.5 },
        { nome: 'Maria Santos', media: 9.2 },
        { nome: 'Pedro Costa', media: 8.8 },
        { nome: 'Ana Lima', media: 8.5 }
    ];

    new Chart(ctx, {
        type: 'line',
        data: {
            labels: topAlunos.map(item => item.nome.split(' ')[0]), // Apenas primeiro nome
            datasets: [{
                label: 'Média',
                data: topAlunos.map(item => item.media),
                borderColor: '#003366',
                backgroundColor: 'rgba(0, 51, 102, 0.1)',
                borderWidth: 2,
                fill: true,
                tension: 0.4
            }]
        },
        options: {
            responsive: true,
            scales: {
                y: {
                    beginAtZero: true,
                    max: 10
                }
            }
        }
    });
}

// ========== FUNÇÕES PARA CONSULTAS COMPLEXAS ==========

async function carregarConsulta(tipoConsulta) {
    try {
        showMessage('Carregando consulta...', 'info');
        
        const response = await fetch(`/api/consultas/${tipoConsulta}`);
        if (!response.ok) {
            throw new Error('Erro ao carregar consulta');
        }
        
        const dados = await response.json();
        exibirResultadoConsulta(dados, tipoConsulta);
        
    } catch (error) {
        showMessage('Erro ao carregar consulta: ' + error.message, 'error');
    }
}

function exibirResultadoConsulta(dados, tipoConsulta) {
    const resultadoDiv = document.getElementById('consulta-resultado');
    const tabelaDiv = document.getElementById('consulta-tabela');
    
    if (!dados || dados.length === 0) {
        tabelaDiv.innerHTML = '<p>Nenhum dado encontrado para esta consulta.</p>';
        resultadoDiv.style.display = 'block';
        return;
    }
    
    // Criar tabela dinamicamente
    const tabela = document.createElement('table');
    
    // Cabeçalho
    const thead = document.createElement('thead');
    const headerRow = document.createElement('tr');
    
    const colunas = Object.keys(dados[0]);
    colunas.forEach(coluna => {
        const th = document.createElement('th');
        th.textContent = coluna.replace(/_/g, ' ').toUpperCase();
        headerRow.appendChild(th);
    });
    
    thead.appendChild(headerRow);
    tabela.appendChild(thead);
    
    // Corpo
    const tbody = document.createElement('tbody');
    dados.forEach(linha => {
        const tr = document.createElement('tr');
        colunas.forEach(coluna => {
            const td = document.createElement('td');
            let valor = linha[coluna];
            
            // Formatação especial para números
            if (typeof valor === 'number') {
                if (coluna.toLowerCase().includes('taxa') || coluna.toLowerCase().includes('porcentagem')) {
                    valor = valor.toFixed(2) + '%';
                } else if (coluna.toLowerCase().includes('media') || coluna.toLowerCase().includes('valor')) {
                    valor = valor.toFixed(2);
                }
            }
            
            td.textContent = valor || '-';
            tr.appendChild(td);
        });
        tbody.appendChild(tr);
    });
    
    tabela.appendChild(tbody);
    tabelaDiv.innerHTML = '';
    tabelaDiv.appendChild(tabela);
    
    resultadoDiv.style.display = 'block';
    showMessage('Consulta carregada com sucesso!', 'success');
}

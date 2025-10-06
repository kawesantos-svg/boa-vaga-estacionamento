// src/main/resources/static/app.js

// IMPORTANTE: COLOQUE A URL PÚBLICA DO SEU SERVIÇO RENDER AQUI
const API_URL = 'https://boa-vaga-estacionamento.onrender.com/api';

// --- Seletores de Elementos ---
const modal = document.getElementById('checkout-modal');
const closeModalButton = document.querySelector('.close-button');
const vagasContainer = document.getElementById('vagas-container');
const checkinForm = document.getElementById('checkin-form');
const mensagemStatus = document.getElementById('mensagem-status');

// --- Funções do Modal ---
function showModal() {
    modal.style.display = 'block';
}

function hideModal() {
    modal.style.display = 'none';
    document.getElementById('modal-body').innerHTML = '';
    document.getElementById('modal-footer').innerHTML = '';
}

// --- Funções Auxiliares ---
function formatarData(dateString) {
    if (!dateString) return 'N/A';
    const data = new Date(dateString);
    return data.toLocaleString('pt-BR', {
        day: '2-digit', month: '2-digit', year: 'numeric',
        hour: '2-digit', minute: '2-digit'
    });
}

function exibirErro(mensagem, erro) {
    console.error(mensagem, erro); // Loga o erro detalhado no console do navegador
    mensagemStatus.textContent = `Erro: ${mensagem}. Verifique o console para mais detalhes.`;
    mensagemStatus.style.color = 'var(--danger-color)';
}

// --- Lógica da Aplicação (Comunicação com a API) ---

async function carregarVagas() {
    try {
        const response = await fetch(`${API_URL}/vagas`);
        if (!response.ok) {
            throw new Error(`Falha ao carregar vagas. Status: ${response.status}`);
        }
        const vagas = await response.json();
        vagasContainer.innerHTML = ''; // Limpa o container

        if (vagas.length === 0) {
            vagasContainer.innerHTML = '<p>Nenhuma vaga cadastrada. Adicione vagas na página de gerenciamento.</p>';
        }

        vagas.forEach(vaga => {
            const vagaDiv = document.createElement('div');
            vagaDiv.className = `vaga ${vaga.status}`;
            vagaDiv.innerHTML = `<strong>${vaga.numeroVaga}</strong><span>${vaga.status}</span>`;
            if (vaga.status === 'OCUPADA') {
                vagaDiv.style.cursor = 'pointer';
                vagaDiv.onclick = () => handleVagaOcupadaClick(vaga.numeroVaga);
            }
            vagasContainer.appendChild(vagaDiv);
        });
    } catch (error) {
        exibirErro('Não foi possível carregar as vagas.', error);
    }
}

async function handleVagaOcupadaClick(numeroVaga) {
    try {
        const response = await fetch(`${API_URL}/estadias/vaga/${numeroVaga}`);
        if (!response.ok) {
            const err = await response.json();
            throw new Error(err.message);
        }
        const estadia = await response.json();

        document.getElementById('modal-body').innerHTML = `
            <p><strong>Placa:</strong> ${estadia.carro.placa}</p>
            <p><strong>Modelo:</strong> ${estadia.carro.modelo}</p>
            <p><strong>Entrada:</strong> ${formatarData(estadia.horarioEntrada)}</p>
        `;
        document.getElementById('modal-footer').innerHTML = `<button id="pagar-btn"><i class="fas fa-dollar-sign"></i> Registrar Saída e Pagar</button>`;
        document.getElementById('pagar-btn').onclick = () => registrarSaida(estadia.id);
        showModal();
    } catch (error) {
        exibirErro('Não foi possível carregar os dados da estadia.', error);
    }
}

async function registrarSaida(estadiaId) {
    try {
        const response = await fetch(`${API_URL}/estadias/${estadiaId}/checkout`, { method: 'POST' });
        if (!response.ok) {
            const err = await response.json();
            throw new Error(err.message);
        }
        const pagamento = await response.json();
        document.getElementById('modal-body').innerHTML = `
            <h3>Pagamento Registrado com Sucesso!</h3>
            <p><strong>Placa:</strong> ${pagamento.placaCarro}</p>
            <p><strong>Entrada:</strong> ${formatarData(pagamento.horarioEntrada)}</p>
            <p><strong>Saída:</strong> ${formatarData(pagamento.horarioSaida)}</p>
            <h4 style="margin-top: 20px;">Valor Total: R$ ${pagamento.valor.toFixed(2)}</h4>
        `;
        document.getElementById('modal-footer').innerHTML = '';
        await carregarVagas();
    } catch (error) {
        exibirErro('Não foi possível registrar a saída.', error);
    }
}

async function registrarEntrada(event) {
    event.preventDefault();
    const placa = document.getElementById('placa').value;
    const modelo = document.getElementById('modelo').value;
    const numeroVaga = document.getElementById('numeroVaga').value;

    mensagemStatus.textContent = 'Registrando entrada...';
    mensagemStatus.style.color = 'var(--secondary-color)';

    try {
        const response = await fetch(`${API_URL}/estadias/checkin`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ placa, modelo, numeroVaga }),
        });

        if (response.ok) {
            mensagemStatus.textContent = 'Veículo estacionado com sucesso!';
            mensagemStatus.style.color = 'var(--success-color)';
            checkinForm.reset();
            await carregarVagas();
        } else {
            const error = await response.json();
            throw new Error(error.message || `Erro ${response.status}`);
        }
    } catch (error) {
        exibirErro(`Falha ao registrar entrada: ${error.message}`, error);
    }
}

// --- Event Listeners ---
if (modal) {
    closeModalButton.onclick = hideModal;
    window.onclick = function(event) {
        if (event.target == modal) {
            hideModal();
        }
    }
}

document.addEventListener('DOMContentLoaded', carregarVagas);
checkinForm.addEventListener('submit', registrarEntrada);
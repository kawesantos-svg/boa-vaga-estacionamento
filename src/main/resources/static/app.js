// Substitua pela sua URL do Render
const API_URL = 'https://sua-url-do-render.onrender.com/api';

const modal = document.getElementById('checkout-modal');
const closeModalButton = document.querySelector('.close-button');

// --- Funções do Modal ---
function showModal() {
    modal.style.display = 'block';
}

function hideModal() {
    modal.style.display = 'none';
    document.getElementById('modal-body').innerHTML = '';
    document.getElementById('modal-footer').innerHTML = '';
}

closeModalButton.onclick = hideModal;
window.onclick = function(event) {
    if (event.target == modal) {
        hideModal();
    }
}

// --- Lógica da Aplicação ---

/**
 * Formata data para o padrão brasileiro.
 * @param {string} dateString - Data em formato ISO (ex: 2025-10-06T18:30:00)
 * @returns {string} - Data formatada (ex: 06/10/2025 18:30)
 */
function formatarData(dateString) {
    if (!dateString) return 'N/A';
    const data = new Date(dateString);
    return data.toLocaleString('pt-BR', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    });
}


/**
 * Função principal para carregar e exibir as vagas.
 */
async function carregarVagas() {
    try {
        const response = await fetch(`${API_URL}/vagas`);
        const vagas = await response.json();
        const container = document.getElementById('vagas-container');
        container.innerHTML = ''; // Limpa o container

        vagas.forEach(vaga => {
            const vagaDiv = document.createElement('div');
            vagaDiv.className = `vaga ${vaga.status}`;
            vagaDiv.innerHTML = `<strong>${vaga.numeroVaga}</strong><span>${vaga.status}</span>`;

            // Adiciona evento de clique apenas se a vaga estiver OCUPADA
            if (vaga.status === 'OCUPADA') {
                vagaDiv.style.cursor = 'pointer';
                vagaDiv.onclick = () => handleVagaOcupadaClick(vaga.numeroVaga);
            }

            container.appendChild(vagaDiv);
        });
    } catch (error) {
        console.error('Erro ao carregar vagas:', error);
    }
}

/**
 * Lida com o clique em uma vaga ocupada, buscando a estadia e abrindo o modal.
 * @param {string} numeroVaga - O número da vaga clicada.
 */
async function handleVagaOcupadaClick(numeroVaga) {
    try {
        const response = await fetch(`${API_URL}/estadias/vaga/${numeroVaga}`);
        if (!response.ok) {
            const err = await response.json();
            throw new Error(err.message);
        }
        const estadia = await response.json();

        const modalBody = document.getElementById('modal-body');
        modalBody.innerHTML = `
            <p><strong>Placa:</strong> ${estadia.carro.placa}</p>
            <p><strong>Modelo:</strong> ${estadia.carro.modelo}</p>
            <p><strong>Entrada:</strong> ${formatarData(estadia.horarioEntrada)}</p>
        `;

        const modalFooter = document.getElementById('modal-footer');
        modalFooter.innerHTML = `<button id="pagar-btn"><i class="fas fa-dollar-sign"></i> Registrar Saída e Pagar</button>`;
        document.getElementById('pagar-btn').onclick = () => registrarSaida(estadia.id);

        showModal();
    } catch (error) {
        console.error('Erro ao buscar estadia:', error);
        alert(`Não foi possível carregar os dados da estadia: ${error.message}`);
    }
}

/**
 * Envia a requisição de checkout (saída) para a API.
 * @param {string} estadiaId - O UUID da estadia a ser finalizada.
 */
async function registrarSaida(estadiaId) {
    try {
        const response = await fetch(`${API_URL}/estadias/${estadiaId}/checkout`, {
            method: 'POST'
        });
        if (!response.ok) {
            const err = await response.json();
            throw new Error(err.message);
        }
        const pagamento = await response.json();

        const modalBody = document.getElementById('modal-body');
        const modalFooter = document.getElementById('modal-footer');

        modalBody.innerHTML = `
            <h3>Pagamento Registrado com Sucesso!</h3>
            <p><strong>Placa:</strong> ${pagamento.placaCarro}</p>
            <p><strong>Entrada:</strong> ${formatarData(pagamento.horarioEntrada)}</p>
            <p><strong>Saída:</strong> ${formatarData(pagamento.horarioSaida)}</p>
            <h4 style="margin-top: 20px;">Valor Total: R$ ${pagamento.valor.toFixed(2)}</h4>
        `;
        modalFooter.innerHTML = ''; // Limpa o botão de pagar

        await carregarVagas(); // Atualiza o status das vagas na página principal
    } catch (error) {
        console.error('Erro ao registrar saída:', error);
        alert(`Erro: ${error.message}`);
    }
}


/**
 * Lida com o envio do formulário de check-in.
 */
async function registrarEntrada(event) {
    event.preventDefault();
    const placa = document.getElementById('placa').value;
    const modelo = document.getElementById('modelo').value;
    const numeroVaga = document.getElementById('numeroVaga').value;
    const mensagemStatus = document.getElementById('mensagem-status');

    try {
        const response = await fetch(`${API_URL}/estadias/checkin`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ placa, modelo, numeroVaga }),
        });

        if (response.ok) {
            mensagemStatus.textContent = 'Veículo estacionado com sucesso!';
            mensagemStatus.style.color = 'var(--success-color)';
            document.getElementById('checkin-form').reset();
            await carregarVagas();
        } else {
            const error = await response.json();
            mensagemStatus.textContent = `Erro: ${error.message}`;
            mensagemStatus.style.color = 'var(--danger-color)';
        }
    } catch (error) {
        console.error('Erro no check-in:', error);
        mensagemStatus.textContent = 'Erro de conexão com o servidor.';
        mensagemStatus.style.color = 'var(--danger-color)';
    }
}

// --- Event Listeners ---
document.addEventListener('DOMContentLoaded', carregarVagas);
document.getElementById('checkin-form').addEventListener('submit', registrarEntrada);
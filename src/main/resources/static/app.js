// URL base da sua API
const API_URL = 'http://localhost:8080';

// Função para carregar e exibir as vagas
async function carregarVagas() {
    try {
        const response = await fetch(`${API_URL}/api/vagas`);
        const vagas = await response.json();

        const container = document.getElementById('vagas-container');
        container.innerHTML = ''; // Limpa o container antes de adicionar as novas vagas

        vagas.forEach(vaga => {
            const vagaDiv = document.createElement('div');
            vagaDiv.className = `vaga ${vaga.status}`; // Adiciona a classe de status para o CSS
            vagaDiv.innerHTML = `<strong>${vaga.numeroVaga}</strong><br>${vaga.status}`;
            container.appendChild(vagaDiv);
        });
    } catch (error) {
        console.error('Erro ao carregar vagas:', error);
    }
}

// Função para lidar com o envio do formulário de check-in
async function registrarEntrada(event) {
    event.preventDefault(); // Impede o recarregamento da página

    const placa = document.getElementById('placa').value;
    const modelo = document.getElementById('modelo').value;
    const numeroVaga = document.getElementById('numeroVaga').value;
    const mensagemStatus = document.getElementById('mensagem-status');

    try {
        const response = await fetch(`${API_URL}/api/estadias/checkin`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ placa, modelo, numeroVaga }),
        });

        if (response.ok) {
            mensagemStatus.textContent = 'Veículo estacionado com sucesso!';
            mensagemStatus.style.color = 'green';
            document.getElementById('checkin-form').reset(); // Limpa o formulário
            carregarVagas(); // Atualiza a lista de vagas
        } else {
            const errorText = await response.text();
            mensagemStatus.textContent = `Erro: ${errorText}`;
            mensagemStatus.style.color = 'red';
        }
    } catch (error) {
        console.error('Erro no check-in:', error);
        mensagemStatus.textContent = 'Erro de conexão com o servidor.';
        mensagemStatus.style.color = 'red';
    }
}

// Event Listeners
// Quando a página carregar, chama a função para carregar as vagas
document.addEventListener('DOMContentLoaded', carregarVagas);
// Quando o formulário for enviado, chama a função de registrar entrada
document.getElementById('checkin-form').addEventListener('submit', registrarEntrada);
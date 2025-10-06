// src/main/resources/static/vagas.js
// Lembre-se de colocar a sua URL do Render aqui
const API_URL = 'https://boa-vaga-estacionamento.onrender.com/api';

async function carregarVagas() {
    try {
        const response = await fetch(`${API_URL}/vagas`);
        const vagas = await response.json();
        const tbody = document.querySelector('#vagas-table tbody');
        tbody.innerHTML = '';

        vagas.forEach(vaga => {
            const tr = document.createElement('tr');
            const statusClass = vaga.status === 'OCUPADA' ? 'status-ocupada' : 'status-disponivel';
            tr.innerHTML = `
                <td>${vaga.numeroVaga}</td>
                <td><span class="status-tag ${statusClass}">${vaga.status}</span></td>
                <td>
                    <button class="danger" onclick="deletarVaga('${vaga.id}')" ${vaga.status === 'OCUPADA' ? 'disabled' : ''}>
                        <i class="fas fa-trash"></i> Excluir
                    </button>
                </td>
            `;
            tbody.appendChild(tr);
        });
    } catch (error) {
        console.error('Erro ao carregar vagas:', error);
    }
}

async function criarVaga(event) {
    event.preventDefault();
    const numeroVaga = document.getElementById('numeroVaga').value;

    try {
        const response = await fetch(`${API_URL}/vagas`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ numeroVaga })
        });

        if (response.ok) {
            alert('Vaga criada com sucesso!');
            document.getElementById('vaga-form').reset();
            await carregarVagas();
        } else {
            const error = await response.json();
            alert(`Erro ao criar vaga: ${error.message}`);
        }
    } catch (error) {
        console.error('Erro:', error);
    }
}

async function deletarVaga(id) {
    if (!confirm('Tem certeza que deseja excluir esta vaga?')) return;

    try {
        const response = await fetch(`${API_URL}/vagas/${id}`, { method: 'DELETE' });
        if (response.ok) {
            alert('Vaga excluída com sucesso!');
            await carregarVagas();
        } else {
            const error = await response.json();
            alert(`Erro ao excluir vaga: ${error.message}`);
        }
    } catch (error) {
        console.error('Erro:', error);
    }
}

document.addEventListener('DOMContentLoaded', carregarVagas);
document.getElementById('vaga-form').addEventListener('submit', criarVaga);
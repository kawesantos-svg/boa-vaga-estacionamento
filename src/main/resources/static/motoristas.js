// src/main/resources/static/motoristas.js
const API_URL = 'https://boa-vaga-estacionamento.onrender.com/api';

async function carregarMotoristas() {
    try {
        const response = await fetch(`${API_URL}/motoristas`);
        const motoristas = await response.json();
        const tbody = document.querySelector('#motoristas-table tbody');
        tbody.innerHTML = '';
        motoristas.forEach(m => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${m.nome}</td>
                <td>${m.cpf}</td>
                <td><button onclick="deletarMotorista('${m.id}')">Excluir</button></td>
            `;
            tbody.appendChild(tr);
        });
    } catch (error) {
        console.error('Erro ao carregar motoristas:', error);
    }
}

async function cadastrarMotorista(event) {
    event.preventDefault();
    const nome = document.getElementById('nome').value;
    const cpf = document.getElementById('cpf').value;

    try {
        const response = await fetch(`${API_URL}/motoristas`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({ nome, cpf })
        });
        if (response.ok) {
            alert('Motorista cadastrado com sucesso!');
            document.getElementById('motorista-form').reset();
            carregarMotoristas();
        } else {
            const error = await response.json();
            alert(`Erro: ${error.message}`);
        }
    } catch (error) {
        console.error('Erro ao cadastrar motorista:', error);
    }
}

async function deletarMotorista(id) {
    if (!confirm('Tem certeza que deseja excluir este motorista?')) return;
    try {
        const response = await fetch(`${API_URL}/motoristas/${id}`, { method: 'DELETE' });
        if (response.ok) {
            alert('Motorista excluído com sucesso!');
            carregarMotoristas();
        } else {
            const error = await response.json();
            alert(`Erro: ${error.message}`);
        }
    } catch (error) {
        console.error('Erro ao excluir motorista:', error);
    }
}

document.addEventListener('DOMContentLoaded', carregarMotoristas);
document.getElementById('motorista-form').addEventListener('submit', cadastrarMotorista);
// src/main/resources/static/carros.js
const API_URL = 'https://sua-url-aqui.onrender.com/api';;

async function carregarProprietarios() {
    try {
        const response = await fetch(`${API_URL}/motoristas`);
        const motoristas = await response.json();
        const select = document.getElementById('proprietario');
        motoristas.forEach(m => {
            const option = document.createElement('option');
            option.value = m.id;
            option.textContent = `${m.nome} (${m.cpf})`;
            select.appendChild(option);
        });
    } catch (error) {
        console.error('Erro ao carregar proprietários:', error);
    }
}

async function carregarCarros() {
    try {
        const response = await fetch(`${API_URL}/carros`);
        const carros = await response.json();
        const tbody = document.querySelector('#carros-table tbody');
        tbody.innerHTML = '';
        carros.forEach(c => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${c.placa}</td>
                <td>${c.modelo}</td>
                <td>${c.cor || ''}</td>
                <td>${c.proprietario ? c.proprietario.nome : 'N/A'}</td>
                <td><button onclick="deletarCarro('${c.placa}')">Excluir</button></td>
            `;
            tbody.appendChild(tr);
        });
    } catch (error) {
        console.error('Erro ao carregar carros:', error);
    }
}

async function cadastrarCarro(event) {
    event.preventDefault();
    const placa = document.getElementById('placa').value;
    const modelo = document.getElementById('modelo').value;
    const cor = document.getElementById('cor').value;
    const proprietarioId = document.getElementById('proprietario').value;

    try {
        const response = await fetch(`${API_URL}/carros`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({ placa, modelo, cor, proprietarioId })
        });
        if (response.ok) {
            alert('Carro cadastrado com sucesso!');
            document.getElementById('carro-form').reset();
            carregarCarros();
        } else {
            const error = await response.json();
            alert(`Erro: ${error.message}`);
        }
    } catch (error) {
        console.error('Erro ao cadastrar carro:', error);
    }
}

async function deletarCarro(placa) {
    if (!confirm('Tem certeza que deseja excluir este carro?')) return;
    try {
        const response = await fetch(`${API_URL}/carros/${placa}`, { method: 'DELETE' });
        if (response.ok) {
            alert('Carro excluído com sucesso!');
            carregarCarros();
        } else {
            const error = await response.json();
            alert(`Erro: ${error.message}`);
        }
    } catch (error) {
        console.error('Erro ao excluir carro:', error);
    }
}

document.addEventListener('DOMContentLoaded', () => {
    carregarProprietarios();
    carregarCarros();
});
document.getElementById('carro-form').addEventListener('submit', cadastrarCarro);
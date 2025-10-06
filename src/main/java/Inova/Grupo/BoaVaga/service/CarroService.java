// src/main/java/Inova/Grupo/BoaVaga/service/CarroService.java
package Inova.Grupo.BoaVaga.service;

import Inova.Grupo.BoaVaga.model.Carro;
import Inova.Grupo.BoaVaga.model.Motorista;
import Inova.Grupo.BoaVaga.repository.CarroRepository;
import Inova.Grupo.BoaVaga.repository.MotoristaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class CarroService {

    private final CarroRepository carroRepository;
    private final MotoristaRepository motoristaRepository; // <-- ADICIONE O REPOSITÓRIO

    // AJUSTE O CONSTRUTOR PARA RECEBER OS DOIS REPOSITÓRIOS
    public CarroService(CarroRepository carroRepository, MotoristaRepository motoristaRepository) {
        this.carroRepository = carroRepository;
        this.motoristaRepository = motoristaRepository;
    }

    // Adicione este método para listar os carros
    public List<Carro> listarTodos() {
        return carroRepository.findAll();
    }

    // Adicione este método para criar um carro
    public Carro criar(Carro carro, UUID proprietarioId) {
        Motorista proprietario = motoristaRepository.findById(proprietarioId)
                .orElseThrow(() -> new RuntimeException("Proprietário não encontrado."));
        carro.setProprietario(proprietario);
        return carroRepository.save(carro);
    }

    // Adicione este método para deletar um carro
    public void deletar(String placa) {
        if (!carroRepository.existsById(placa)) {
            throw new RuntimeException("Carro não encontrado.");
        }
        carroRepository.deleteById(placa);
    }

    /**
     * Busca um carro pela placa. Se não existir, cria um novo registro.
     * @param placa A placa do carro.
     * @param modelo O modelo do carro (usado se for necessário criar).
     * @return O carro encontrado ou o novo carro criado.
     */
    public Carro encontrarOuCriar(String placa, String modelo) {
        return carroRepository.findById(placa).orElseGet(() -> {
            Carro novoCarro = new Carro();
            novoCarro.setPlaca(placa);
            novoCarro.setModelo(modelo);
            // Em um cenário real, você poderia associar um motorista aqui.
            return carroRepository.save(novoCarro);
        });
    }
}
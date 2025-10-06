// src/main/java/Inova/Grupo/BoaVaga/service/MotoristaService.java
package Inova.Grupo.BoaVaga.service;

import Inova.Grupo.BoaVaga.model.Motorista;
import Inova.Grupo.BoaVaga.repository.MotoristaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MotoristaService {
    private final MotoristaRepository motoristaRepository;

    public MotoristaService(MotoristaRepository motoristaRepository) {
        this.motoristaRepository = motoristaRepository;
    }

    public List<Motorista> listarTodos() {
        return motoristaRepository.findAll();
    }

    public Optional<Motorista> buscarPorId(UUID id) {
        return motoristaRepository.findById(id);
    }

    public Motorista criar(Motorista motorista) {
        if (motoristaRepository.findByCpf(motorista.getCpf()).isPresent()) {
            throw new RuntimeException("Já existe um motorista com este CPF.");
        }
        return motoristaRepository.save(motorista);
    }

    public void deletar(UUID id) {
        if (!motoristaRepository.existsById(id)) {
            throw new RuntimeException("Motorista não encontrado.");
        }
        motoristaRepository.deleteById(id);
    }
}
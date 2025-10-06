// src/main/java/Inova/Grupo/BoaVaga/service/VagaService.java
package Inova.Grupo.BoaVaga.service;

import Inova.Grupo.BoaVaga.model.StatusVaga; // Adicione este import
import Inova.Grupo.BoaVaga.model.Vaga;
import Inova.Grupo.BoaVaga.repository.VagaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Adicione este import
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional // Boa prática adicionar a nível de classe
public class VagaService {

    private final VagaRepository vagaRepository;

    public VagaService(VagaRepository vagaRepository) {
        this.vagaRepository = vagaRepository;
    }

    @Transactional(readOnly = true)
    public List<Vaga> listarTodas() {
        return vagaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Vaga> buscarPorId(UUID id) {
        return vagaRepository.findById(id);
    }

    // NOVO MÉTODO PARA CRIAR VAGA
    public Vaga criarVaga(String numeroVaga) {
        if (vagaRepository.findByNumeroVaga(numeroVaga).isPresent()) {
            throw new RuntimeException("Uma vaga com este número já existe.");
        }
        Vaga novaVaga = new Vaga();
        novaVaga.setNumeroVaga(numeroVaga);
        novaVaga.setStatus(StatusVaga.DISPONIVEL); // Vagas são criadas como disponíveis
        return vagaRepository.save(novaVaga);
    }

    // NOVO MÉTODO PARA DELETAR VAGA
    public void deletarVaga(UUID id) {
        Vaga vaga = vagaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vaga não encontrada."));

        if (vaga.getStatus() == StatusVaga.OCUPADA) {
            throw new RuntimeException("Não é possível excluir uma vaga que está ocupada.");
        }
        vagaRepository.deleteById(id);
    }
}
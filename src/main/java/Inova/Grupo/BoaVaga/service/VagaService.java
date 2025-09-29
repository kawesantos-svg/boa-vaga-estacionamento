package Inova.Grupo.BoaVaga.service;

import Inova.Grupo.BoaVaga.model.Vaga;
import Inova.Grupo.BoaVaga.repository.VagaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VagaService {

    private final VagaRepository vagaRepository;

    public VagaService(VagaRepository vagaRepository) {
        this.vagaRepository = vagaRepository;
    }

    public List<Vaga> listarTodas() {
        return vagaRepository.findAll();
    }

    public Optional<Vaga> buscarPorId(UUID id) {
        return vagaRepository.findById(id);
    }
}
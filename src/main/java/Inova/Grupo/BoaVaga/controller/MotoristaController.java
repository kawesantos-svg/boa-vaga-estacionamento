// src/main/java/Inova/Grupo/BoaVaga/controller/MotoristaController.java
package Inova.Grupo.BoaVaga.controller;

import Inova.Grupo.BoaVaga.dto.MotoristaDTO;
import Inova.Grupo.BoaVaga.model.Motorista;
import Inova.Grupo.BoaVaga.service.MotoristaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/motoristas")
public class MotoristaController {
    private final MotoristaService motoristaService;

    public MotoristaController(MotoristaService motoristaService) {
        this.motoristaService = motoristaService;
    }

    @GetMapping
    public ResponseEntity<List<Motorista>> listarTodos() {
        return ResponseEntity.ok(motoristaService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<Motorista> criarMotorista(@Valid @RequestBody MotoristaDTO motoristaDTO) {
        Motorista novoMotorista = new Motorista();
        novoMotorista.setNome(motoristaDTO.getNome());
        novoMotorista.setCpf(motoristaDTO.getCpf());

        Motorista motoristaSalvo = motoristaService.criar(novoMotorista);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(motoristaSalvo.getId()).toUri();

        return ResponseEntity.created(location).body(motoristaSalvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMotorista(@PathVariable UUID id) {
        motoristaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
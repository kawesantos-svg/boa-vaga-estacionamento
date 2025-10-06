// src/main/java/Inova/Grupo/BoaVaga/controller/VagaController.java
package Inova.Grupo.BoaVaga.controller;

import Inova.Grupo.BoaVaga.dto.VagaDTO; // Adicione este import
import Inova.Grupo.BoaVaga.model.Vaga;
import Inova.Grupo.BoaVaga.service.VagaService;
import jakarta.validation.Valid; // Adicione este import
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI; // Adicione este import
import java.util.List;
import java.util.UUID; // Adicione este import
import org.springframework.web.servlet.support.ServletUriComponentsBuilder; // Adicione este import

@RestController
@RequestMapping("/api/vagas")
public class VagaController {

    private final VagaService vagaService;

    public VagaController(VagaService vagaService) {
        this.vagaService = vagaService;
    }

    @GetMapping
    public ResponseEntity<List<Vaga>> listarTodas() {
        List<Vaga> vagas = vagaService.listarTodas();
        return ResponseEntity.ok(vagas);
    }

    // NOVO ENDPOINT PARA CRIAR VAGA
    @PostMapping
    public ResponseEntity<Vaga> criarVaga(@Valid @RequestBody VagaDTO vagaDTO) {
        Vaga vagaSalva = vagaService.criarVaga(vagaDTO.getNumeroVaga());
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(vagaSalva.getId()).toUri();
        return ResponseEntity.created(location).body(vagaSalva);
    }

    // NOVO ENDPOINT PARA DELETAR VAGA
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarVaga(@PathVariable UUID id) {
        vagaService.deletarVaga(id);
        return ResponseEntity.noContent().build();
    }
}
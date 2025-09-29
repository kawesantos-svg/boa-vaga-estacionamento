package Inova.Grupo.BoaVaga.controller;

import Inova.Grupo.BoaVaga.model.Vaga;
import Inova.Grupo.BoaVaga.service.VagaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}